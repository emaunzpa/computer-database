<!DOCTYPE html>
<html>
	<head>
		<title>Computer Database</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
		<!-- Bootstrap -->
		<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
		<link href="../css/main.css" rel="stylesheet" media="screen">
	</head>
	
	<body>
	    <c:import url="/views/navbar.jsp"/>
	
	    <section id="main">
	        <div class="container">
	            <h1 id="homeTitle">${computers.size()} Computers found (${pagination.startIndex} to ${pagination.endIndex} printed)</h1>
	            <div id="actions" class="form-horizontal">
	                <div class="pull-left">
	                    <form id="searchForm" action="#" method="GET" class="form-inline">
	
	                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
	                        <input type="submit" id="searchsubmit" value="Filter by name"
	                        class="btn btn-primary" />
	                    </form>
	                </div>
	                <div class="pull-right">
	                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
	                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
	                </div>
	            </div>
	        </div>
	
	        <form id="deleteForm" action="#" method="POST">
	            <input type="hidden" name="selection" value="">
	        </form>
	
	        <div class="container" style="margin-top: 10px;">
	            <table id="table" class="table table-striped table-bordered">
	                <thead>
	                    <tr>
	                        <th class="editMode" style="width: 60px; height: 22px;">
	                            <input type="checkbox" id="selectall" /> 
	                            <span style="vertical-align: top;">
	                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();"><i class="fa fa-trash-o fa-lg"></i></a></span></th>
	                        <th>Computer name</th>
	                        <th>Introduced date</th>
	                        <!-- Table header for Discontinued Date -->
	                        <th>Discontinued date</th>
	                        <!-- Table header for Company -->
	                        <th>Company name</th>
	                    </tr>
	                </thead>
	                <!-- Browse attribute computers -->
	                <tbody id="results">
	                	<c:set var="countComputer" value="1"></c:set>
	                	<c:forEach items="${printedComputers}" var="computer">
	                    <tr class="computer-ligne">
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="" onclick="" id="computerName${countComputer}">${computer.name}</a>
	                        </td>
	                        <td>${computer.introducedDate}</td>
	                        <td>${computer.discontinuedDate}</td>
	                        <td>${computer.manufacturerName}</td>
	                    </tr>
	                    <c:set var="countComputer" value="${countComputer + 1}"></c:set>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	    </section>
	
	    <footer class="navbar-fixed-bottom">
	        <div class="container text-center">
	            <ul class="pagination">
	                <li>
	                	<a href="?startIndex=0&endIndex=10" id="startPaginationButton">Start</a>
	                	<!-- Previous 50 computers if possible -->
	                	<c:choose>
	                		<c:when test="${pagination.startIndex >= 50}">
	                			<a href="?startIndex=${pagination.startIndex - 50}&endIndex=${pagination.endIndex - 50}" aria-label="Previous" id="previous50PaginationButton"><span aria-hidden="true">- 50</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=0&endIndex=10" aria-label="Previous" id="previous50PaginationButton"><span aria-hidden="true">- 50</span></a>
	                		</c:otherwise>
	                	</c:choose>
	                	<!-- Previous 10 computers if possible -->
	                	<c:choose>
	                		<c:when test="${pagination.startIndex >= 10}">
	                			<a href="?startIndex=${pagination.startIndex - 10}&endIndex=${pagination.endIndex - 10}" aria-label="Previous" id="previous10PaginationButton"><span aria-hidden="true">&laquo;</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=0&endIndex=10" aria-label="Previous" id="previous10PaginationButton"><span aria-hidden="true">&laquo;</span></a>
	                		</c:otherwise>
	                	</c:choose>
	                	<!-- Next 10 computers if possible -->
						<c:choose>
	                		<c:when test="${pagination.endIndex + 10 <= computers.size()}">
	                			<a href="?startIndex=${pagination.startIndex + 10}&endIndex=${pagination.endIndex + 10}" aria-label="Next" id="next10PaginationButton"><span aria-hidden="true">&raquo;</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=${computers.size() - 10}&endIndex=${computers.size()}" aria-label="Next" id="next10PaginationButton"><span aria-hidden="true">&raquo;</span></a>
	                		</c:otherwise>
	                	</c:choose>
	                	<!-- Next 50 computers if possible -->
	                	<c:choose>
	                		<c:when test="${pagination.endIndex + 50 <= computers.size()}">
	                			<a href="?startIndex=${pagination.startIndex + 50}&endIndex=${pagination.endIndex + 50}" aria-label="Next" id="next50PaginationButton"><span aria-hidden="true">+ 50</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=${computers.size() - 10}&endIndex=${computers.size()}" aria-label="Next" id="next50PaginationButton"><span aria-hidden="true">+ 50</span></a>
	                		</c:otherwise>
	                	</c:choose>
	                	<a href="?startIndex=${computers.size() - 10}&endIndex=${computers.size()}" id="endPaginationButton">End</a>
	            	</li>
	       		</ul>
	    </footer>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/dashboard.js"></script>
	
	</body>
</html>