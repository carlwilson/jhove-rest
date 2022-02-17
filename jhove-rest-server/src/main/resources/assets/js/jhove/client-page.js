/**
* Document ready function, loaded up on start
*/
$(document).ready(function () {
  jhoveRest.modules.populate(function () {
    modMenu()
  })
})

/**
* Render the JHOVE application details
*/
function modMenu () {
  $('#mod-drop').empty()
  var transform = {
    '<>': 'a',
    class: 'dropdown-item',
    href: '/modules/${moduleId.name}',
    text: '${moduleId.name}'
  }
  $('#mod-drop').json2html(jhoveRest.modules.modules, transform)
}
