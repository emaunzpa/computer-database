<!DOCTYPE html>
<html>
	<head>
		<title>Computer Database</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
		<!-- Bootstrap -->
		<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="../css/font-awesome.min.css" rel="stylesheet" media="screen">
		<link href="../css/main.css" rel="stylesheet" media="screen">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
	</head>
	
	<body>
	    <c:import url="/views/navbar.jsp"/>
	
		<div id="deleteAlert" class="alert alert-warning" role="alert" style="text-align:center;" hidden>
			YOU ARE GONNA DELETE
			<span id="numberOfSelectedComputers"></span>
			COMPUTERS, ARE YOU SURE ?
			<a class="btn btn-default" id="" onclick="postDeleteForm();">Delete</a> or <a class="btn btn-default" onclick="hideDeleteAlert();">Cancel</a>
		</div>
		
	    <section id="main">
	        <div class="container">
	            <h1 id="homeTitle">${computers.size()} Computers found (${pagination.startIndex + 1} 
	            <c:if test="${computers.size() > 10}"> to ${pagination.endIndex}</c:if>
	             printed)
	            </h1>
	            <div id="actions" class="form-horizontal">
	                <div class="pull-left">
	                    <form id="searchForm" action="listComputers" method="GET" class="form-inline">
	                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
	                        <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
	                    </form>
	                </div>
	                <div class="pull-right">
	                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
	                    <a class="btn btn-default" id="editComputer" onclick="$.fn.toggleEditMode();">Edit</a>
						<a class="btn btn-primary" id="deleteComputer" onclick="displayDeleteAlert();"><i class="fas fa-trash-alt"></i></a> 
	                </div>
	            </div>
	        </div>
			
			<form id="deleteForm" action="listComputers" method="POST">
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
	                	<c:forEach items="${computers}" var="computer">
	                    <tr class="computer-ligne" <c:if test="${countComputer < pagination.startIndex || countComputer > pagination.endIndex}">hidden</c:if> >
	                        <td class="editMode">
	                        	<input type="checkbox" name="cb" class="cb" value="${computer.id}">
	                        </td>
	                        <td>
	                            <a href="editComputer?computerID=${computer.id}" id="computerName${countComputer}">${computer.name}</a>
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
	        </form>
	    </section>
	
	    <footer class="navbar-fixed-bottom">
	        <div class="container text-center">
	            <ul class="pagination">
	                <li>
	                	<a href="?startIndex=0&endIndex=10&search=${search}" id="startPaginationButton">Start</a>
	                	<!-- Previous 50 computers if possible -->
	                	<c:choose>
	                		<c:when test="${pagination.startIndex >= 50}">
	                			<a href="?startIndex=${pagination.startIndex - 50}&endIndex=${pagination.endIndex - 50}&search=${search}" aria-label="Previous" id="previous50PaginationButton"><span aria-hidden="true">&lt;&lt;</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=0&endIndex=10&search=${search}" aria-label="Previous" id="previous50PaginationButton"><span aria-hidden="true">&lt;&lt;</span></a>
	                		</c:otherwise>
	                	</c:choose>
	                	<!-- Previous 10 computers if possible -->
	                	<c:choose>
	                		<c:when test="${pagination.startIndex >= 10}">
	                			<a href="?startIndex=${pagination.startIndex - 10}&endIndex=${pagination.endIndex - 10}&search=${search}" aria-label="Previous" id="previous10PaginationButton"><span aria-hidden="true">&lt;</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=0&endIndex=10&search=${search}" aria-label="Previous" id="previous10PaginationButton"><span aria-hidden="true">&lt;</span></a>
	                		</c:otherwise>
	                	</c:choose>
	                	<!-- Next 10 computers if possible -->
						<c:choose>
	                		<c:when test="${pagination.endIndex + 10 <= computers.size()}">
	                			<a href="?startIndex=${pagination.startIndex + 10}&endIndex=${pagination.endIndex + 10}&search=${search}" aria-label="Next" id="next10PaginationButton"><span aria-hidden="true">&gt;</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<c:choose>
	                				<c:when test="${computers.size() > 10}">
	                					<a href="?startIndex=${computers.size() - 10}&endIndex=${computers.size()}&search=${search}" aria-label="Next" id="next10PaginationButton"><span aria-hidden="true">&gt;</span></a>
	                				</c:when>
	                				<c:otherwise>
	                					<a href="?startIndex=0&endIndex=10&search=${search}" aria-label="Next" id="next10PaginationButton"><span aria-hidden="true">&gt;</span></a>
	                				</c:otherwise>
	                			</c:choose>
	                		</c:otherwise>
	                	</c:choose>
	                	<!-- Next 50 computers if possible -->
	                	<c:choose>
	                		<c:when test="${pagination.endIndex + 50 <= computers.size()}">
	                			<a href="?startIndex=${pagination.startIndex + 50}&endIndex=${pagination.endIndex + 50}&search=${search}" aria-label="Next" id="next50PaginationButton"><span aria-hidden="true">&gt;&gt;</span></a>
	                		</c:when>
	                		<c:otherwise>
	                			<c:choose>
	                				<c:when test="${computers.size() > 10}">
	                					<a href="?startIndex=${computers.size() - 10}&endIndex=${computers.size()}&search=${search}" aria-label="Next" id="next50PaginationButton"><span aria-hidden="true">&gt;&gt;</span></a>
	                				</c:when>
	                				<c:otherwise>
	                					<a href="?startIndex=0&endIndex=10&search=${search}" aria-label="Next" id="next50PaginationButton"><span aria-hidden="true">&gt;&gt;</span></a>
	                				</c:otherwise>
	                			</c:choose>
	                		</c:otherwise>
	                	</c:choose>
	                	<c:choose>
	                		<c:when test="${computers.size() > 10}">
	                			<a href="?startIndex=${computers.size() - 10}&endIndex=${computers.size()}&search=${search}" id="endPaginationButton">End</a>
	                		</c:when>
	                		<c:otherwise>
	                			<a href="?startIndex=0&endIndex=10&search=${search}" id="endPaginationButton">End</a>
	                		</c:otherwise>
	                	</c:choose>
	            	</li>
	       		</ul>
	       	</div>
	    </footer>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/dashboard.js"></script>
	
	</body>
</html>