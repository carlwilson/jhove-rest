/**
* Document ready function, loaded up on start
*/
$(document).ready(function () {
  jhoveRest.app.getDetails(function () {
    renderAppDetails()
  })
  jhoveRest.env.getDetails(function () {
    renderEnvDetails()
  })
})

/**
* Render the JHOVE application details
*/
function renderAppDetails () {
  $('#jhove-details').empty()
  var transforms = {
    details: {
      '<>': 'div',
      html: [
        {
          '<>': 'h1',
          text: 'JHOVE REST Services'
        },
        {
          '<>': 'p',
          text: function (obj, index) { var date = new Date(obj.buildDate); return 'v' + obj.version + ', built on ' + date.toUTCString() }
        },
        {
          '<>': 'p',
          class: 'rights',
          text: '${rights}'
        }
      ]
    }
  }
  console.log('Rendering app details')
  $('#jhove-details').json2html(jhoveRest.app.details, transforms.details)
}

/**
* Render the Server environment details
*/
function renderEnvDetails () {
  $('#env-details').empty()
  var transforms = {
    details: {
      '<>': 'div',
      html: [
        {
          '<>': 'p',
          text: '${os.name} v${os.version}, Java v${java.version}${java.architecture} ${java.vendor}'
        }
      ]
    }
  }
  console.log('Rendering env details')
  $('#env-details').json2html(jhoveRest.env.details, transforms.details)
}
