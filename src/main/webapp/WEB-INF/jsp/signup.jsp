<title>Insert title here</title>
</head>
<body>
<br>
<h1>Signup Form</h1>
	<form name="sign Up Page" action="adduser" method='POST'>
		<table>
			<tr>
				<td>First Name:</td>
				<td><input type="text" id="firstname" name="firstname" required></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type="text" id="lastname" name="lastname" required></td>
			</tr>
			
			<tr>
				<td>Email:</td>
				<td><input type="text" id="username" name="username" required></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" id="password" name="password" required></td>
			</tr>
			<tr>
				<td>User Type:</td>
				<td>
				  <input type="radio" id="buyer" name="usertype" value="buyer" required>
				  <label for="buyer">Buyer</label><br>
				  <input type="radio" id="seller" name="usertype" value="seller">
				  <label for="seller">Seller</label><br>
				  <input type="radio" id="both" name="usertype" value="both">
				  <label for="both">Both</label>
				</td>
			</tr>

			<tr>
				<td></td>
				<td><input type ="submit" value="Submit" /></td>
			</tr>
		</table>
	</form>
</body>
</html>