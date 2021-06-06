<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Local Business Platform</h1>
<br>
<h2>Please Login</h2>
	<form name="Login Page" action="login" method='POST'>
		<table>
			<tr>
				<td>Email:</td>
				<td><input type="text" id="username" name="username" required>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password" required>
			</tr>
			<tr>
				<td></td>
				<td><button type="submit">Log In</button></td>
			</tr>
			<tr>
				<td></td>
				<td><small style="color:red">${SPRING_SECURITY_LAST_EXCEPTION.message}</small></td>
			</tr>
			<tr>
				<td></td>
				<td>Don't have an account!</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="/signup">Create an account</a></td>
			</tr>
		</table>
	</form>
	
</body>
</html>