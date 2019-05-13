<header class="navbar navbar-inverse navbar-expand-lg navbar-fixed-top">
	<div id="navMenu" class="container">
		<a class="navbar-brand navItem" href="listComputers?startIndex=0&endIndex=10&search=&sorted="><spring:message code="lbl.dashboard"></spring:message></a>
		<sec:authorize access="hasRole('ROLE_ADMIN')"><a class="navbar-brand navItem" href="addUser"><spring:message code="lbl.addUser"></spring:message></a></sec:authorize>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
    		<div class="navbar-brand navbar-right dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><i id="userIcon" class="fas fa-user"></i><spring:message code="lbl.hello"></spring:message>, ${user}</a>
		        <div id="dropdownMenu" class="dropdown-menu" style="display: none;">
		        <a id="logout" class="dropdown-item navItem" href="logout"><i id="logoutIcon" class="fas fa-sign-out-alt"></i><spring:message code="lbl.logout"></spring:message></a>
		        <a id="englishFlag" class="dropdown-item navItem" href="listComputers?startIndex=0&endIndex=10&search=&sorted=&lang=en"><img alt="englishLog" src="static/images/britainFlags.png"><spring:message code="lbl.english"></spring:message></a>
				<a id="frenchFlag" class="dropdown-item navItem" href="listComputers?startIndex=0&endIndex=10&search=&sorted=&lang=fr"><img alt="frenchLog" src="static/images/frenchFlags.png"><spring:message code="lbl.french"></spring:message></a>
		      </div>
			</div>
		</div>
	</div>
</header>