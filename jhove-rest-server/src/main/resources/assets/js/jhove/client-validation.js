/**
* Document ready function, loaded up on start
*/
$(document).ready(function () {
  /**
  * Event handler for the file selector
  */
  $('input:file').on('change', function () {
    // Disable the submit button
    $('button').prop('disabled', true)
    // Grab the label component
    var fileLabel = $(this).siblings('.custom-file-label')
    // Get filename without the fake path prefix
    var fileName = $(this).val().split('\\').pop()
    // Set the filename selection, a little tricksy
    fileLabel.addClass('selected').html(fileName)
    // Calculate and display the SHA1 of the file
    calcFileSha1(this.files[0])
  })

  /**
  * Event handler for submit button
  */
  $('button').click(function () {
    // Grab the data from the form object
    var formData = new FormData($('form')[0])
    // Call the validator, with result renderer as callback
    jhoveRest.validator.validate(formData, function () {
      renderResult()
    })
  })

  jhoveRest.modules.populate(function () {
    renderModules()
  })
})

/**
* Calculates the SHA-1 of selected file and displays the result
*/
function calcFileSha1 (file) {
  // New checksum calculator instance
  const rusha = new Rusha()
  // File reader to get data
  const reader = new FileReader()
  // Register reader onload event
  reader.onload = function (e) {
    // Calculate the checksum from the reader result
    var digest = rusha.digest(reader.result)
    // Set the label when finished
    $('#digest').val(digest)
    // Enable the submit button
    $('button').prop('disabled', false)
  }
  // Signal checkcum calculation and load reader
  $('#digest').val('Calcluating file checksum...')
  reader.readAsBinaryString(file)
}

function renderModules () {
  var transform = {
    '<>': 'option',
    value: '${moduleId.name}',
    text: '${moduleId.name}'
  }
  $('select').json2html(jhoveRest.modules.modules, transform)
}
/**
* Render the validation result to screen
*/
function renderResult () {
  $('#results').empty()
  $('#results').append($('<h2>').text('Validation Result'))
  var transforms = {
    status: {
      '<>': 'div',
      html: [
        {
          '<>': 'div',
          class: function (obj, index) { return 'alert ' + ((obj.valid > 0) ? 'alert-success' : 'alert-danger') },
          text: function (obj, index) { return jhoveResultString(obj.wellFormed, obj.valid) }
        }
      ]
    },
    messages: {
      '<>': 'div',
      class: function (obj, index) { return 'alert ' + (obj.prefix === 'Error' ? 'alert-danger' : 'alert-warning') },
      text: function (obj, index) { return (index + 1) + ': ' + obj.message }
    }
  }
  $('#results').json2html(jhoveRest.validator.result, transforms.status)
  $('#results').append($('<h3>').text('Messages'))
  $('#results').json2html(jhoveRest.validator.result.messages, transforms.messages)
}

function jhoveResultString (wellFormed, valid) {
  return (wellFormed < 1) ? 'Not well-formed' : (valid < 1) ? 'Well-formed, but not valid.' : 'Well-formed and valid'
}
