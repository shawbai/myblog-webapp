<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
	<form method="post" action="test/testRequestParam">
			<input type="text" name="age" /> 
			<input type="submit" value="SubMit"/>
    </form>
    <form method="post" action="/test/testPojo">
        <input type="text" name="name" /> 
        <input type="text" name="address" /> 
            <input type="text" name="age" /> 
            <input type="submit" value="SubMit"/>
    </form>
</div>
</body>
</html>