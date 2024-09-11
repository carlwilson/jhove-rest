FROM maven:3.9-eclipse-temurin-11-alpine AS dev-builder
RUN apk add --no-cache git

WORKDIR /jhove
RUN git clone -b feat/pdf-2-support https://github.com/openpreserve/jhove.git && cd jhove && mvn clean install

WORKDIR /build

# Copy the current dev source branch to a local build dir
COPY ./ /build
WORKDIR /build
RUN mvn clean package

# Now build a Java JRE for the Alpine application image
# https://github.com/docker-library/docs/blob/master/eclipse-temurin/README.md#creating-a-jre-using-jlink
FROM eclipse-temurin:11-jdk-alpine AS jre-builder

# Create a custom Java runtime
RUN "$JAVA_HOME/bin/jlink" \
         --add-modules java.base,java.logging,java.xml,java.management,java.sql,java.desktop,jdk.crypto.ec \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# Now the final application image
FROM alpine:3

ENV JHOVE_REST_VERSION=0.0.1-SNAPSHOT

# Set up dumb-init for process safety: https://github.com/Yelp/dumb-init
ADD --link https://github.com/Yelp/dumb-init/releases/download/v1.2.5/dumb-init_1.2.5_x86_64 /usr/local/bin/dumb-init 
RUN chmod +x /usr/local/bin/dumb-init

# Copy the JRE from the previous stage
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-builder /javaruntime $JAVA_HOME

# Since this is a running network service we'll create an unprivileged account
# which will be used to perform the rest of the work and run the actual service:
RUN addgroup -S jhove-rest && adduser --system -D --home /opt/jhove-rest -G jhove-rest jhove-rest
RUN mkdir --parents /opt/jhove-rest && chown -R jhove-rest:jhove-rest /opt/jhove-rest

# Copy the application from the previous stage
COPY --from=dev-builder /build/jhove-rest-server/target/jhove-rest-server-${JHOVE_REST_VERSION}.jar /opt/jhove-rest/jhove-rest.jar

# Copy the default configuration file
COPY jhove-rest.yml /var/opt/jhove-rest/config/
COPY jhove_docker.conf /var/opt/jhove-rest/jhove.conf

WORKDIR /var/opt/jhove-rest
RUN ls -alh /var/opt/jhove-rest/

# Expose the ports and volumes for use
VOLUME /var/opt/jhove-rest
EXPOSE 8080
EXPOSE 8081

ENTRYPOINT [ "dumb-init", "--" ]
CMD ["java", "-Djava.awt.headless=true", "-jar", "/opt/jhove-rest/jhove-rest.jar", "server", "/var/opt/jhove-rest/config/jhove-rest.yml"]
