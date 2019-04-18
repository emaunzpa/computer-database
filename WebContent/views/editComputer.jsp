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
                    <div class="label label-default pull-right">ID : ${computer.id}</div>
                    <h1><spring:message code="lbl.editComputer"/></h1>

                    <form:form id="computerForm" action="editComputer" method="POST" modelAttribute="computerDTO">
                        <form:input type="hidden" value="${computer.id}" id="id" path="id"/>
                        <fieldset>
                            <div class="form-group">
                                <form:label path="name"><spring:message code="lbl.computerName"/></form:label>
                                <form:input type="text" class="form-control" id="computerName" value="${computer.name}" path="name" required="required"/>
                            	<form:errors path="name" class="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="introducedDate"><spring:message code="lbl.introduced"/></form:label>
                                <form:input type="date" class="form-control" id="introduced" value="${computer.introducedDate}" path="introducedDate"/>
                            	<form:errors path="introducedDate" class="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="discontinuedDate"><spring:message code="lbl.discontinued"/></form:label>
                                <form:input type="date" class="form-control" id="discontinued" value="${computer.discontinuedDate}" path="discontinuedDate"/>
                            	<form:errors path="discontinuedDate" class="error"></form:errors>
                            </div>
                            <div class="form-group">
                                <form:label path="manufacturerId"><spring:message code="lbl.company"/></form:label>
                                <form:select class="form-control" id="companyId" path="manufacturerId">
                                    <form:option value="0">--</form:option>
                                    <c:set var="count" value="1"></c:set>
                                    <c:forEach items="${manufacturers}" var="manufacturer">
                                    	<c:choose>
                                    		<c:when test="${manufacturer.id == computer.manufacturerId}">
                                    			<form:option class="companyId-option" value="${count}" selected="selected">${manufacturer.name}</form:option>
                                    		</c:when>
                                    		<c:otherwise>
                                    			<form:option class="companyId-option" value="${count}">${manufacturer.name}</form:option>
                                    		</c:otherwise>
                                    	</c:choose>
                                    	 <c:set var="count" value="${count + 1}"></c:set>
                                    </c:forEach>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="editButton" type="submit" value="<spring:message code="lbl.edit"/>" class="btn btn-primary">
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