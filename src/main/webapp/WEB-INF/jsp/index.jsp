<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script data-main="${pageContext.request.contextPath}/js/app.js" src="${pageContext.request.contextPath}/js/require.js"></script>
<script type="text/javascript">
    require(["scripts/index"]);
</script>
</head>
<body>
	hello world
	<div>lasttime:${lasttime}</div>
	<div>nowtime:${nowtime}</div>
</body>
</html>