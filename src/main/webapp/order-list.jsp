<%@ page import="com.mateuszmedon.project.jsprestaurant.model.Order" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17/08/2019
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order List</title>
</head>
<body>
<jsp:include page="navigator.jsp"/>
<h1>Order List </h1>
<table width="100%" border="1" bgcolor="#ffe4c4">
    <tr>
        <th>Id</th>
        <%--<th>Ilość produktów:</th>--%>
        <th>Time:</th>
        <th>Time Delivered:</th>
        <th>Customers:</th>
        <th>Table Number:</th>
        <th>To Pay:</th>
        <th>Pay:</th>
        <th>Action:</th>
    </tr>
    <%
        List<Order> orderList = (List<Order>) request.getAttribute("orderListAttribute");
        for (Order order : orderList) {
            out.print("<tr>");
            out.print("<td>" + order.getId() + "</td>");
//            out.print("<td>"+order.getProducts().size()+"</td>");
            out.print("<td>" + order.getFormattedTimeOrdered() + "</td>");
            out.print("<td>" + order.getFormattedTimeDelivered() + "</td>");
            out.print("<td>" + order.getPeopleCount() + "</td>");
            out.print("<td>" + order.getTableNumber() + "</td>");
            out.print("<td>" + String.format("%.2f", order.calculateToPay()) + "</td>");
            out.print("<td>" + (order.getPaid() != null) + "</td>");
            out.print("<td>" + getLinkForProductList(order.getId()) +
                    getLinkForDeliveredOrder(order) +
                    getLinkForPay(order) +
                    getLinkToRemoveOrder(order) +
                    "</td>");
            out.print("</tr>");
        }
    %>
</table>
</body>
</html>
<%!
    private String getLinkToRemoveOrder(Order order) {
        if (order.getProductsCount() == 0 || (order.getPaid() != null)) {
            return "<a href=\"/order/remove?orderId=" + order.getId() + "\">Delete</a>";
        } else {
            return "";
        }
    }

    private String getLinkForProductList(Long id) {
        return "<a href=\"/product/list?orderId=" + id + "\">Products</a>";
    }

    private String getLinkForDeliveredOrder(Order order) {
        if (order.getTimeDelivered() == null) {
            return "<a href=\"/order/deliver?orderId=" + order.getId() + "\">Delivered</a>";
        } else {
            return "";
        }
    }

    private String getLinkForPay(Order order) {
        if ((order.getTimeDelivered() != null
                && order.getPaid() == null
                && order.getToPay() != null)
                && order.getToPay()>0) { // TODO make to payment when add a new order. Also Look to change naumber 0.01 in column toPay.
            //TODO check on calculate method which is in class.
            return "<a href=\"/product/pay?orderId=" + order.getId() + "\">Pay</a>";
        } else {
            return "";
        }
    }

%>