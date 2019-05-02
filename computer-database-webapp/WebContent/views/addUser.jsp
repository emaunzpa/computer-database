<!DOCTYPE html>
<html>
	<head>
		<title>Computer Database</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
		<!-- Bootstrap -->
		<link href="static/css/bootstrap.min.css" rel="stylesheet"
			media="screen">
		<link href="static/css/font-awesome.min.css" rel="stylesheet"
			media="screen">
		<link href="static/css/main.css" rel="stylesheet" media="screen">
		<link rel="stylesheet"
			href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
			integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf"
			crossorigin="anonymous">
	</head>

	<body>
	
		<c:import url="/views/navbar.jsp" />

		<div id="hiddenRequired" hidden>
			<spring:message code="lbl.required" />
		</div>
	
		<section id="main">
				<div class="container">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2 box">
							<h1>
								<spring:message code="lbl.addUser" />
							</h1>
							<form id="userForm" action="addUser" method="POST">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<fieldset>
									<div class="form-group">
										<label>
											<spring:message code="lbl.userName" />
										</label>
										<spring:message code="lbl.userName" var="placeholder" />
										<input type="text" id="userName" class="form-control"
											name="userName" required="required" />
									</div>
									<div class="form-group">
										<label>
											<spring:message code="lbl.password" />
										</label>
										<spring:message code="lbl.password" var="placeholder" />
										<input type="password" class="form-control" id="password" required="required"
											name="password" />
									</div>
									<div class="form-group">
										<label>
											<spring:message code="lbl.role" />
										</label>
										<select class="form-control" name="role" required="required" >
											<option value="ROLE_USER"><spring:message code="lbl.user" /></option>
											<option value="ROLE_ADMIN"><spring:message code="lbl.admin" /></option>
										</select>
									</div>
								</fieldset>
								<div class="actions pull-right">
									<input id="addButton" type="submit"
										value="<spring:message code="lbl.add"/>" class="btn btn-primary" />
									<spring:message code="lbl.or" />
									<a id="cancelButton" href="listComputers" class="btn btn-default"><spring:message
											code="lbl.deleteCancel" /></a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</section>
	
		<script src="static/js/jquery.min.js"></script>
		<script src="static/js/jquery.validate.min.js"></script>
		<script src="static/js/userFormValidator.js"></script>
	
	</body>

</html>