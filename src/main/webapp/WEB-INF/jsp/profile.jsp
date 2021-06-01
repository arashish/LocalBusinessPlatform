<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form name="Profile Page" action="updateuser" method='POST'>
		<table>
			<tr>
				<td></td>
				<td><input type="text" id="id" name="id" value="${user.id}" hidden>
			</tr>
			<tr>
				<td></td>
				<td><input type="text" id="username" name="username" value="${user.username}" hidden>
			</tr>
			<tr>
				<td>First Name:</td>
				<td><input type="text" id="firstname" name="firstname" value="${user.firstname}">
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" id="lastname" name="lastname" value="${user.lastname}">
			</tr>
			<tr>
				<td>Mobile Number:</td>
				<td><input type="text" id="phone" name="phone" value="${user.phone}">
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password" value="${user.password}">
			</tr>
			<tr>
				<td></td>
				<td><button name="Save changes" type="submit">Save Changes</button>
			</tr>
		</table>
	</form>
</body>
</html>