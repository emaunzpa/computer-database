<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <c:import url="/views/navbar.jsp"/>

	<div id="hiddenDatesCoherence" hidden><spring:message code="lbl.datesCoherence"/></div>
	<div id="hiddenRequired" hidden><spring:message code="lbl.required"/></div>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="lbl.addComputer"/></h1>
                    <form:form id="computerForm" action="addComputer" method="POST" modelAttribute="computerDTO">
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name"><spring:message code="lbl.computerName"/></form:label>
                                <spring:message code="lbl.computerName" var="placeholder"/>
                                <form:input type="text" class="form-control" id="computerName" path="name" required="required" placeholder="${placeholder}"/>
                            	<form:errors path="name" class="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="introducedDate"><spring:message code="lbl.introduced"/></form:label>
                                <spring:message code="lbl.introduced" var="placeholder"/>
                                <form:input type="date" class="form-control" id="introduced" path="introducedDate" placeholder="${placeholder}"/>
                            	<form:errors path="introducedDate" class="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinuedDate"><spring:message code="lbl.discontinued"/></form:label>
								<spring:message code="lbl.discontinued" var="placeholder"/>
								<form:input type="date" class="form-control" id="discontinued" path="discontinuedDate" placeholder="${placeholder}"/>
                            	<form:errors path="discontinuedDate" class="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="manufacturerId"><spring:message code="lbl.company"/></form:label>
                                    <form:select class="form-control" id="companyId" path="manufacturerId">
                                    <form:option value="0">--</form:option>
                                    <c:set var="count" value="1"></c:set>
                                    <c:forEach items="${manufacturers}" var="manufacturer">
                                    	<form:option class="companyId-option" value="${count}">${manufacturer.name}</form:option>
                                    	 <c:set var="count" value="${count + 1}"></c:set>
                                    </c:forEach>
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="addButton" type="submit" value="<spring:message code="lbl.add"/>" class="btn btn-primary"/>
                            <spring:message code="lbl.or"/>
                            <a id="cancelButton" href="listComputers" class="btn btn-default"><spring:message code="lbl.deleteCancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="static/js/jquery.min.js"></script>
    <script src="static/js/jquery.validate.min.js"></script>
    <script src="static/js/computerFormValidator.js"></script>
    
</body>
</html>