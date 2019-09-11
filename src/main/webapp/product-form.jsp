<%@ page import="com.mateuszmedon.project.jsprestaurant.model.Product" %><%--
  Created by IntelliJ IDEA.
  User: amen
  Date: 8/18/19
  Time: 11:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product form</title>
</head>
<body>
<jsp:include page="navigator.jsp"/>
<h1>Product Form</h1>

<%
    String operation = "add";

    String descriptionValue = "";
    String amountValue = "";
    String valueValue = "";
    String editedProductIdValue = "";

    Object possibleProduct = request.getAttribute("productAttribute");
    if (possibleProduct != null && possibleProduct instanceof Product) {
        operation = "edit";

        Product product = (Product) possibleProduct;

        descriptionValue = product.getDescription();
        amountValue = String.valueOf(product.getAmount());
        valueValue = String.valueOf(product.getValue());

        editedProductIdValue = String.valueOf(product.getId());
    }
%>

<form style="width: 50%;margin:auto;" action="/product/<%= operation %>" method="post">
    <input type="hidden" name="orderId" value="<%= request.getAttribute("orderIdAttribute") %>">
    <input type="hidden" name="editedProductId" value="<%= editedProductIdValue %>">

    <label style="display:inline-block;min-width: 49%;width: 49%;" for="descriptionId">Order description:</label>
    <input style="width: 50%" id="descriptionId" name="descriptionParam" value="<%=descriptionValue%>" type="text">

    <br/>

    <label style="display:inline-block;min-width: 49%;width: 49%;" for="amountId">Amount:</label>
    <input style="width: 50%" id="amountId" name="amountParam" value="<%=amountValue%>" type="number" step="1">

    <br/>

    <label style="display:inline-block;min-width: 49%;width: 49%;" for="valueId">Value:</label>
    <input style="width: 50%" id="valueId" name="valueParam" value="<%=valueValue%>" type="number" step="0.01" min="0.0">

    <br/>

    <input style="width: 100%" type="submit">

</form>

</body>
</html>