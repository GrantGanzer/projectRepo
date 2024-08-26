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
	<h1 class="welcome">Welcome to the showtime app</h1>
	<h3 class="welcome">This app is tool for artist to take back their
		ticket sales...</h3>
	<div class="manifesto">
		<p>Too long has the ticketing and live show industry placed
			profits and personal gain high above, beautiful experience, artistry,
			and community.</p>
		<p>All while making it as difficult as possible to operate on
			"their turf"</p>
		<p>NOT cool, NOT chill, and NOT around here</p>
		<p>...we dont play like that...</p>
		<p>Feel free (and yes its free) to use this tool to track your own
			ticket sales and connect with your fans</p>
	</div>
	<div class="welcomecontainer">
		<div class="logincontainer">
			<form:form action="/register" method="post" modelAttribute="newUser"
				class="forms">
				<p>Create an account?</p>
				<div class="form-group">
					<form:label class="input-group-text" path="firstName">First name:</form:label>
					<form:input class="form-control" type="text" path="firstName" />
					<form:errors class="errors" path="firstName" />
				</div>
				<div class="form-group">
					<form:label class="input-group-text" path="lastName">Last name:</form:label>
					<form:input class="form-control" type="text" path="lastName" />
					<form:errors class="errors" path="lastName" />
				</div>
				<div class="form-group">
					<form:label class="input-group-text" path="email">Email:</form:label>
					<form:input class="form-control" type="text" path="email" />
					<form:errors class="errors" path="email" />
				</div>
				<div class="form-group">
					<form:label class="input-group-text" path="password">Password:</form:label>
					<form:input class="form-control" type="password" path="password" />
					<form:errors class="errors" path="password" />
				</div>
				<div class="form-group">
					<form:label class="input-group-text" path="confirm">Confirm Pw:</form:label>
					<form:input class="form-control" type="password" path="confirm" />
					<form:errors class="errors" path="confirm" />
				</div>
				<button class=" btn btn-primary" type="submit">Submit</button>
			</form:form>
			<form:form action="/login" method="post" modelAttribute="newLogin"
				class="forms">
				<p>Already have an account?</p>
				<div class="form-group">
					<form:label class="input-group-text" path="email">Email:</form:label>
					<form:input class="form-control" type="text" path="email" />
					<form:errors class="errors" path="email" />
				</div>
				<div class="form-group">
					<form:label class="input-group-text" path="password">Password:</form:label>
					<form:input class="form-control" type="password" path="password" />
					<form:errors class="errors" path="password" />
				</div>
				<button class=" btn btn-primary" type="submit">Submit</button>
			</form:form>
		</div>
	</div>
</body>
</html>