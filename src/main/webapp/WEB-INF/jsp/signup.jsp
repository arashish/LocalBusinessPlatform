<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<br>
<h1>Signup Form</h1>
	<form name="sign Up Page" action="adduser" method='POST'>
		<table>
			<tr>
				<td>First Name:</td>
				<td><input type="text" id="firstname" name="firstname">
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" id="lastname" name="lastname">
			</tr>
			<tr>
				<td>Mobile Number:</td>
				<td><input type="text" id="phone" name="phone">
			</tr>
			<tr>
				<td>Username:</td>
				<td><input type="text" id="username" name="username">
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password">
			</tr>
			<tr>
				<td></td>
				<td><button type="submit">Sign Up</button>
			</tr>
		</table>
	</form>
</body>
</html>