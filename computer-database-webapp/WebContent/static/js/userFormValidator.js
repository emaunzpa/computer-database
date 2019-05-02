$( document ).ready(function() {
	document.getElementById('addButton').disabled = true;
});

jQuery.extend(jQuery.validator.messages, {
	required: $("#hiddenRequired").text()
});

document.getElementById('password').addEventListener('input', function (evt) {
    checkpasswords();
});

document.getElementById('passwordConfirm').addEventListener('input', function (evt) {
    checkpasswords();
});

function checkpasswords(){
	
	var password = document.getElementById("password").value;
	var passwordConfirm = document.getElementById("passwordConfirm").value;
	var regex = /^(?=.*[\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[\w!@#$%^&*]{8,}$/
	
	if (password.length < 8) {
		document.getElementById('passwordError').style.color = "red";
		document.getElementById('passwordError').innerHTML = $('#passwordLength').text();
		document.getElementById('addButton').disabled = true;
	}
	else if (!regex.test(password)) {
		document.getElementById('passwordError').style.color = "red";
		document.getElementById('passwordError').innerHTML = $('#passwordContains').text();
		document.getElementById('addButton').disabled = true;
	}
	else if (password != passwordConfirm) {
		document.getElementById('passwordError').style.color = "green";
		document.getElementById('passwordConfirmError').style.color = "red";
		document.getElementById('passwordError').innerHTML = "OK !";
		document.getElementById('passwordConfirmError').innerHTML = $('#passwordSame').text();
		document.getElementById('addButton').disabled = true;
	}
	else {
		console.log("ok");
		document.getElementById('passwordError').style.color = "green";
		document.getElementById('passwordConfirmError').style.color = "green";
		document.getElementById('passwordError').innerHTML = "OK !";
		document.getElementById('passwordConfirmError').innerHTML = "OK !";
		document.getElementById('addButton').disabled = false;
	}
	
}