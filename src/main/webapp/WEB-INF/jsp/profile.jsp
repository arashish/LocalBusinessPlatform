<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
				<td>Email:</td>
				<td><input type="text" id="id" name="id" value="${user.id}" readonly> </td>
			</tr>
			<tr>
				<td></td>
				<td><input type="text" id="username" name="username" value="${user.username}" hidden> </td>
			</tr>
			<tr>
				<td>First Name:</td>
				<td><input type="text" id="firstname" name="firstname" value="${user.firstname}"> </td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" id="lastname" name="lastname" value="${user.lastname}"> </td>
			</tr>
			
			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password" value="${user.password}"> </td>
			</tr>
			
			<tr>
				<td>User Type:</td>
				<td>
					<c:choose>
					    <c:when test="${'buyer' eq user.usertype}">
							<input type="radio" id="buyer" name="usertype" value="buyer" checked> <label for="buyer">Buyer</label><br>
							<input type="radio" id="seller" name="usertype" value="seller"> <label for="seller">Seller</label><br>
							<input type="radio" id="both" name="usertype" value="both"> <label for="both">Both</label>
					    </c:when>
					    <c:when test="${'seller' eq user.usertype}">
					        <input type="radio" id="buyer" name="usertype" value="buyer"> <label for="buyer">Buyer</label><br>
							<input type="radio" id="seller" name="usertype" value="seller" checked> <label for="seller">Seller</label><br>
							<input type="radio" id="both" name="usertype" value="both"> <label for="both">Both</label>
					    </c:when>
						 <c:when test="${'both' eq user.usertype}">
					        <input type="radio" id="buyer" name="usertype" value="buyer"> <label for="buyer">Buyer</label><br>
							<input type="radio" id="seller" name="usertype" value="seller"> <label for="seller">Seller</label><br>
							<input type="radio" id="both" name="usertype" value="both" checked> <label for="both">Both</label>
					    </c:when>
						
					</c:choose>
				</td>
			</tr>
			<tr>	
				<td>Active:</td>
				<td>
					<c:choose>
					    <c:when test="${true eq user.active}">
							<input type="radio" id="on" name="active" value=true checked> <label for="on">On</label><br>
							<input type="radio" id="off" name="active" value=false> <label for="off">Off</label><br>
					    </c:when>
					    <c:when test="${'false' eq user.active}">
					        <input type="radio" id="on" name="active" value=true> <label for="on">On</label><br>
							<input type="radio" id="off" name="active" value=false checked> <label for="off">Off</label><br>
					    </c:when>
					</c:choose>
				</td>
			</tr>
			
			<tr>
				<td>Registration Date:</td>
				<td><input type="text" id="registrationdate" name="registrationdate" value="${user.registrationdate} readonly"> </td>
			</tr>
			
			<tr>
				<td></td>
				<td><button name="Save changes" type="submit">Save Changes</button>
			</tr>
		</table>
	</form>
</body>
</html>