<html>
	<head>
		<title>CDB error 404</title>
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<meta charset="utf-8">
			<!-- Bootstrap -->
			<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
			<link href="static/css/font-awesome.min.css" rel="stylesheet" media="screen">
			<link href="static/css/main.css" rel="stylesheet" media="screen">
			<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
	</head>
	
	<body>
		<div id="errorAlert" class="alert alert-danger" role="alert" style="text-align:center;">
			<spring:message code="lbl.404"/> ${errorMessage} <spring:message code="lbl.goBack"/>
			<a class="btn btn-default" id="backToDashboard" href="listComputers"><spring:message code="lbl.dashboard"/></a>
		</div>
	</body>
</html>