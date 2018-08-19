<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2018/5/13
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Spring MVC表单处理</title>
</head>
<body>
<%@ include file="/WEB-INF/common.jsp" %>
<h2>user 信息 </h2>
<table>
    <tr>
        <td>名称：${model}</td>
        <td>${model.userName}</td>
    </tr>
    <tr>
        <td>地址：</td>
        <td>${model.address}</td>
    </tr>
    <tr>
        <td>编号：</td>
        <td>${model.id}</td>
    </tr>
</table>
</body>
</html>
