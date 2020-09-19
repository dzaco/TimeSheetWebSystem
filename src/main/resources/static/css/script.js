$(document).ready(function(){

// wyszukiwanie w tabelach
  $("#filterInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#filterTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });


});