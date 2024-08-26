<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css" />
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="appcontainer">
		<div class="navcontainer">
			<div class="nav">
				<h1>Welcome to the showtime app, ${user.getFirstName()}!</h1>
			</div>
			<div class="nav2">
				<a class="btn btn-primary" href="/shows/new">Create your own
					show...</a> <a class="btn btn-primary" href="/logout">Logout</a>
			</div>
		</div>
		<div class="showcards">
			<div class="row row-cols-3 row-cols-md-3 g-4">
				<c:forEach var="show" items="${shows}">
					<div class="col">
						<div class="card border-dark mb-3" style="max-width: 18rem;">
							<div class="card-header">
								<a href="/shows/${show.getId()}">${show.getName()}</a>
							</div>
							<div class="card-body">
								<p>
									Venue:
									<c:out value="${show.getVenue()}"></c:out>
								</p>
								<p>
									Artist:
									<c:out value="${show.getAdminuser().getFirstName()}"></c:out>
								</p>
								<p>
									Ticket Price: $
									<c:out value="${show.getPrice()}"></c:out>
								</p>
								<p>
									Show date:
									<c:out value="${show.getShowdateformatted()}"></c:out>
								</p>
							</div>
							<div class="card-footer">
								<c:choose>
									<c:when test="${show.getAdminuser().getId() == user.getId()}">
										<p>
											<a class="btn btn-primary" href="/shows/edit/${show.getId()}">Edit</a><a
												class="btn btn-primary" href="/shows/delete/${show.getId()}">Delete</a><a
												class="btn btn-primary"
												href="/shows/guestlist/${show.getId()}">Guest List</a>
										</p>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${show.getFinalguestlist().contains(user)}">
												<p>Your ticket has been confirmed! see you at the show</p>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${show.getGuestlist().contains(user)}">
														<p>Your ticket is still being confirmed... check back
															later</p>
													</c:when>
													<c:otherwise>
														<p>
															<a class="btn btn-primary" href="/shows/${show.getId()}">Get
																a ticket</a>
														</p>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>