/**
* JavaScript wrapper for JHOVE Application details
*/
/**
* Document ready function, loaded up on start
*/
$(document).ready(function () {
  jhoveRest.modules.populate(function () {
    renderModules()
  })
})

/**
* Render the validation result to screen
*/
function renderModules () {
  var transform = {
    '<>': 'li',
    class: 'list-group-item',
    html: [
      {
        '<>': 'div',
        class: 'card',
        html: [
          {
            '<>': 'div',
            class: 'card-body',
            html: [
              {
                '<>': 'h5',
                class: 'card-title',
                text: '${moduleId.name} v${moduleId.release}'
              },
              {
                '<>': 'p',
                class: 'card-text',
                text: function (obj, index) { var date = new Date(obj.moduleId.date); return 'Built: ' + date.toLocaleDateString('en-GB') }
              },
              {
                '<>': 'p',
                class: 'card-text',
                text: function (obj, index) { return 'Mime Types: ' + obj.formatDetails.mimeTypes.join(',') }
              }
            ]
          }
        ]
      }
    ]
  }

  $('ul').json2html(jhoveRest.modules.modules, transform)
}
