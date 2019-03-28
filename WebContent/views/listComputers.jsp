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
	            <h1 id="homeTitle">${computers.size()} Computers found (
	            <c:choose>
	            	<c:when test="${computers.size() > 10}">${pagination.startIndex + 1} to ${pagination.endIndex}</c:when>
	            	<c:otherwise>${computers.size()}</c:otherwise>
	            </c:choose>
	             printed )
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
	                            <input type="checkbox" id="selectall"><i class="fas fa-level-down-alt pull-right"></i>
	                       	</th>
	                        <th>Computer name 
	                        <c:choose>
	                        	<c:when test="${pagination.sorted == 'byName'}">
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byNameReverse"><i class="fas fa-sort"></i></a>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byName"><i class="fas fa-sort"></i></a>
	                        	</c:otherwise>
	                        </c:choose>
	                        </th>
	                        
	                        <th>Introduced date
	                        <c:choose>
	                        	<c:when test="${pagination.sorted == 'byIntroduced'}">
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byIntroducedReverse"><i class="fas fa-sort"></i></a>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byIntroduced"><i class="fas fa-sort"></i></a>
	                        	</c:otherwise>
	                        </c:choose>
	                        </th>
	                        <th>Discontinued date
	                        <c:choose>
	                        	<c:when test="${pagination.sorted == 'byDiscontinued'}">
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byDiscontinuedReverse"><i class="fas fa-sort"></i></a>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byDiscontinued"><i class="fas fa-sort"></i></a>
	                        	</c:otherwise>
	                        </c:choose>
	                        </th>
	                        <th>Company name
	                        <c:choose>
	                        	<c:when test="${pagination.sorted == 'byCompany'}">
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byCompanyReverse"><i class="fas fa-sort"></i></a>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<a class="pull-right" href="?startIndex=${pagination.startIndex}&endIndex=${pagination.endIndex}&search=${search}&sorted=byCompany"><i class="fas fa-sort"></i></a>
	                        	</c:otherwise>
	                        </c:choose>
	                        </th>
	                    </tr>
	                </thead>
	                <!-- Browse attribute computers -->
	                <tbody id="results">       	
	                	<c:set var="countComputer" value="1"></c:set>
	                	<c:forEach items="${computers}" var="computer">
	                    <tr class="computer-ligne" <c:if test="${countComputer < pagination.startIndex || countComputer > pagination.endIndex}">hidden</c:if> >
	                        <td class="editMode">
	                        	<input type="checkbox" name="cb" class="cb<c:if test="${countComputer >= pagination.startIndex && countComputer <= pagination.endIndex}"> cb-display</c:if>" value="${computer.id}"/>
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
	                	<a href="?startIndex=0&endIndex=10&search=${search}&sorted=${pagination.sorted}" id="startPaginationButton">Start</a>
	                	<a href="?startIndex=${pagination.previous50Index}&endIndex=${pagination.previous50Index + 10}&search=${search}&sorted=${pagination.sorted}" aria-label="Previous" id="previous50PaginationButton"><i class="fas fa-fast-backward"></i></a>
	                	<a href="?startIndex=${pagination.previous10Index}&endIndex=${pagination.previous10Index + 10}&search=${search}&sorted=${pagination.sorted}" aria-label="Previous" id="previous10PaginationButton"><i class="fas fa-step-backward"></i></a>
	                	<a href="?startIndex=${pagination.next10Index - 10}&endIndex=${pagination.next10Index}&search=${search}&sorted=${pagination.sorted}" aria-label="Next" id="next10PaginationButton"><i class="fas fa-step-forward"></i></a>
	                	<a href="?startIndex=${pagination.next50Index - 10}&endIndex=${pagination.next50Index}&search=${search}&sorted=${pagination.sorted}" aria-label="Next" id="next50PaginationButton"><i class="fas fa-fast-forward"></i></a>
	                	<a href="?startIndex=${pagination.toEndIndex - 10}&endIndex=${pagination.toEndIndex}&search=${search}&sorted=${pagination.sorted}" id="endPaginationButton">End</a>
	            	</li>
	       		</ul>
	       	</div>
	    </footer>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/dashboard.js"></script>
	
	</body>
</html>