JHOVE REST WS resources and application server
==============================================

Quick Start
-----------
REST wrappings for [JHOVE](https://jhove.openpreservation.org), provides:

- JAXB REST resources for JHOVE validation
- Dropwizard application server
- Basic Web GUI implementation

### Build from source
You'll need Java 8 or greater and Maven to build the project.

1. Clone this repo: `git clone git@github.com:openpreserve/jhove-rest.git`
2. Drop into project directory: `cd jhove-rest`
3. Compile and package via Maven `mvn clean package`

### Run the server
From the cloned project directory to start the jetty server:

```shell
java -jar jhove-rest-server/target/jhove-rest-server-0.0.1-SNAPSHOT.jar server jhove-rest.yml
```
### Explore the API

Go to <http://localhost:8080/api/swagger#/jhove> in your browser to see the swagger
documentation of the API and try it.

### Try via CURL

To validate a local file `./tmp/test.jpg` use the following CURL command:

```
curl -F "file=@tmp/test.jpg" -F "module=JPEG-hul" localhost:8080/api/jhove/validate -H  "Accept:application/xml"
```

If you prefer JSON results:

```
curl -F "file=@tmp/test.jpg" -F "module=JPEG-hul" localhost:8080/api/jhove/validate -H  "Accept:application/xml"
```

### Build with a different module selection/versions

The version of the JHOVE modules used to build the rest server are all held within [`pom.xml`](pom.xml). Change the versions here to those required and rebuild the project.
