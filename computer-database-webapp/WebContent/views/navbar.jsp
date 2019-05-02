<header class="navbar navbar-inverse navbar-fixed-top">
	<div id="navMenu" class="container">
		<a class="navbar-brand navItem" href="listComputers?startIndex=0&endIndex=10&search=&sorted="><spring:message code="lbl.dashboard"></spring:message></a>
		<sec:authorize access="hasRole('ROLE_ADMIN')"><a class="navbar-brand navItem" href="addUser"><spring:message code="lbl.addUser"></spring:message></a></sec:authorize>
		<a class="navbar-brand navbar-right navItem" href="logout"><i id="logoutIcon" class="fas fa-sign-out-alt"></i><spring:message code="lbl.logout"></spring:message></a>
		<a class="navbar-brand navbar-right navItem" href="listComputers?startIndex=0&endIndex=10&search=&sorted=&lang=fr"><img alt="frenchLog" src="static/images/frenchFlags.png"></a>
		<a class="navbar-brand navbar-right navItem" href="listComputers?startIndex=0&endIndex=10&search=&sorted=&lang=en"><img alt="englishLog" src="static/images/britainFlags.png"></a>
	</div>
</header>