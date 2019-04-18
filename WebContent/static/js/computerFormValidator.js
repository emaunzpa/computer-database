// Javascript to validate introduced and discontinued dates with Jquery

jQuery.validator.addMethod(
		"greaterThan", 
		function(value, element, params) {

			if (/Invalid/.test(new Date(value))){
				return true;
			}
			if (/Invalid/.test(new Date($(params).val())) && !/Invalid/.test(new Date(value))){
				return false;
			}
			if (!/Invalid/.test(new Date(value)) && !/Invalid/.test(new Date($(params).val()))){
				return new Date(value) > new Date($(params).val());
			}			
		},
		$("#hiddenDatesCoherence").text()
);

$("#computerForm").validate({
	rules : {
		discontinuedDate : {
			greaterThan: "#introduced"
		}
	}
});

jQuery.extend(jQuery.validator.messages, {
	required: $("#hiddenRequired").text()
});
		
	 
	 