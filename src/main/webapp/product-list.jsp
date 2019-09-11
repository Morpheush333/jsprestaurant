<%@ page import="com.mateuszmedon.project.jsprestaurant.model.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mateuszmedon.project.jsprestaurant.model.Product" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17/08/2019
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<jsp:include page="navigator.jsp"/>
<h1>Product List</h1>
<%
    Long orderId = Long.parseLong(request.getParameter("orderId")); // We have it from URL
%>
<a href="/product/add?orderId=<%= orderId %>">Add Product</a>

<table width="100%" border="1">
    <tr>
        <th>Id</th>
        <%--<th>Ilość produktów:</th>--%>
        <th>Description:</th>
        <th>Value:</th>
        <th>Amount:</th>
        <th>Order:</th>
        <th>Actions:</th>
    </tr>
    <%


        Set<Product> productList = (Set<Product>) request.getAttribute("productSetAttribute");
        for (Product product : productList ) {
            out.print("<tr>");
            out.print("<td>" + product.getId() + "</td>");
//            out.print("<td>"+order.getProducts().size()+"</td>");
            out.print("<td>" + product.getDescription() + "</td>");
            out.print("<td>" + product.getValue() + "</td>");
            out.print("<td>" + product.getAmount() + "</td>");
            out.print("<td>" +
                    getLinkToProductDelete(orderId, product.getId()) +
                    getLinkToEditDelete(orderId, product.getId()) +
                    "</td>");
            out.print("</tr>");
        }
    %>
</table>
</body>
</html>
<%!
    private String getLinkToEditDelete(Long orderId, Long id) {
        return "<a href=\"/product/edit?orderId=" + orderId + "&productId=" + id + "\">Edit  </a>";
    }

    private String getLinkToProductDelete(Long orderId, Long id) {
        return "<a href=\"/product/remove?orderId=" + orderId + "&productId=" + id + "\">Delete_Product  </a>";
    }
%>