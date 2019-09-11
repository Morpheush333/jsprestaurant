package com.mateuszmedon.project.jsprestaurant.servlets;



import com.mateuszmedon.project.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/deliver")
public class OrderDeliveredServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdString = req.getParameter("orderId");
        Long orderId = Long.parseLong(orderIdString);

        orderService.delivered(orderId); //make as a delivered

        //webSide which we are in
        String referer = req.getHeader("referer");

        resp.sendRedirect(referer); //go back to the previous web
    }
}
