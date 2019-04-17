// Javascript to validate introduced and discontinued dates with Jquery

jQuery.validator.addMethod(
		"greaterThan", 
		function(value, element, params) {

			console.log(new Date($(params).val()));
			console.log(new Date(value));
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
		'Must be coherent with introduced date'
);

$("#computerForm").validate({
	rules : {
		discontinuedDate : {
			greaterThan: "#introduced"
		}
	}
});