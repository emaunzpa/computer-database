$("#userForm").validate({
	rules : {
		discontinuedDate : {
			greaterThan: "#introduced"
		}
	}
});

jQuery.extend(jQuery.validator.messages, {
	required: $("#hiddenRequired").text()
});