jQuery('#navbarDropdown').on('click', function (e) {
	  $('.dropdown-toggle').next().toggle();
});

jQuery(document).on('click', function (e) {
	if ($('#dropdownMenu').is(":visible")) {
		$('.dropdown-toggle').next().toggle();
	}
});