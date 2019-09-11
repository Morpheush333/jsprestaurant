<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18/08/2019
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order form</title>
</head>
<body>
<jsp:include page="navigator.jsp"/>
<h1>Order Form</h1>

<form style="width: 50%;margin:auto;" action="/order/add" method="post">
    <%--<div width="50%" style="margin:auto">--%>
    <label style="display:inline-block;min-width: 49%;width: 49%;" for="peopleNumberId">People count:</label>
    <input style="width: 50%" id="peopleNumberId" name="peopleCountParam" type="number" step="1" min="1">

    <br/>

    <label style="display:inline-block;min-width: 49%;width: 49%;" for="tableNumberId">Table number:</label>
    <input style="width: 50%" id="tableNumberId" name="tableNumberParam" type="number" step="1">

    <br/>

    <input style="width: 100%" type="submit">

    <%--</div>--%>
</form>

</body>
</html>
