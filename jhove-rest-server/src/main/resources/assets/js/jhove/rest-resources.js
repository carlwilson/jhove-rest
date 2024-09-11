/**
 * JavaScript wrapper JHOVE REST resources
 */
var jhoveRest = {
  validator: {
    result: null,
    status: null,
    validate: function (formData, callback, contentType = 'json') {
      $.ajax({
        url: '/api/jhove/validate',
        type: 'POST',
        data: formData,
        dataType: contentType,
        contentType: false,
        processData: false,
        success: function (data, textStatus, jqXHR) {
          console.log(jqXHR)
          jhoveRest.validator.result = data
          callback()
        },
        // HTTP Error handler
        error: function (jqXHR, textStatus, errorThrown) {
          // Log full error to console
          console.log('Validation Error: ' + textStatus + errorThrown)
          console.log(jqXHR)
        }
      })
    }
  },
  app: {
    details: null,
    getDetails: function (callback, contentType = 'json') {
      $.ajax({
        url: '/api/jhove/',
        dataType: contentType,
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
          console.log(jqXHR)
          jhoveRest.app.details = data
          callback()
        },
        // HTTP Error handler
        error: function (jqXHR, textStatus, errorThrown) {
          // Log full error to console
          console.log('Validation Error: ' + textStatus + errorThrown)
          console.log(jqXHR)
        }
      })
    }
  },
  env: {
    details: null,
    getDetails: function (callback, contentType = 'json') {
      $.ajax({
        url: '/api/info/',
        dataType: contentType,
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
          console.log(jqXHR)
          jhoveRest.env.details = data
          callback()
        },
        // HTTP Error handler
        error       : 'jhoveRest.errHandler'
      })
    }
  },
  modules: {
    modules: null,
    populate: function (callback, contentType = 'json') {
      $.ajax({
        url: '/api/jhove/modules',
        dataType: contentType,
        type: 'GET',
        success: function (data, textStatus, jqXHR) {
          jhoveRest.modules.modules = data.sort((a, b) => {
            var aName = a.moduleId.name.toUpperCase()
            var bName = b.moduleId.name.toUpperCase()
            if (aName < bName) {
              return -1
            }
            if (aName > bName) {
              return 1
            }
            return 0
          })
          console.log(jhoveRest.modules.modules)
          callback()
        },
        // HTTP Error handler
        error: function (jqXHR, textStatus, errorThrown) {
          // Log full error to console
          console.log('JHOVE Error: ' + textStatus + errorThrown)
          console.log(jqXHR)
        }
      })
    }
  },
  errHandler: function (jqXHR, textStatus, errorThrown) {
    // Log full error to console
    console.log('JHOVE Error: ' + textStatus + errorThrown)
    console.log(jqXHR)
  }
}
