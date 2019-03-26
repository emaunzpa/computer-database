<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <c:import url="/views/navbar.jsp"/>
    
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">ID : ${computer.id}</div>
                    <h1>Edit Computer</h1>

                    <form id="computerForm" action="editComputer" method="POST">
                        <input type="hidden" value="${computer.id}" id="id" name="computerId"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" value="${computer.name}" name="computerName" required="required">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" value="${computer.introducedDate}" name="introducedDate">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" value="${computer.discontinuedDate}" name="discontinuedDate">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
                                    <c:set var="count" value="1"></c:set>
                                    <c:forEach items="${manufacturers}" var="manufacturer">
                                    	<c:choose>
                                    		<c:when test="${manufacturer.id == computer.manufacturerId}">
                                    			<option class="companyId-option" value="${count}" selected="selected">${manufacturer.name}</option>
                                    		</c:when>
                                    		<c:otherwise>
                                    			<option class="companyId-option" value="${count}">${manufacturer.name}></option>
                                    		</c:otherwise>
                                    	</c:choose>
                                    	 <c:set var="count" value="${count + 1}"></c:set>
                                    </c:forEach>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="editButton" type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a id="cancelButton" href="listComputers" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    
    <script src="../js/jquery.min.js"></script>
    <script src="../js/jquery.validate.min.js"></script>
   	<script src="../js/computerFormValidator.js"></script>
    
</body>
</html>