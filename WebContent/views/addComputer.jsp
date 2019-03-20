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
                    <h1>Add Computer</h1>
                    <form id="addComputerForm" action="addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" required="required" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introducedDate" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinuedDate" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <option value="0">--</option>
                                    <c:set var="count" value="1"></c:set>
                                    <c:forEach items="${manufacturers}" var="manufacturer">
                                    	<option class="companyId-option" value="${count}">${manufacturer.name}</option>
                                    	 <c:set var="count" value="${count + 1}"></c:set>
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="addButton" type="submit" value="Add" class="btn btn-primary">
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
    
    <!-- Javascript to validate introduced and discontinued dates with Jquery-->
    <script type="text/javascript">
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
    		'Must be greater than Introduced Date or both null'
    	);
    	     	    
    	$("#addComputerForm").validate({
    		rules : {
    			discontinuedDate : {
    				greaterThan: "#introduced"
    			}
    		}
    	});
    </script>
    
</body>
</html>