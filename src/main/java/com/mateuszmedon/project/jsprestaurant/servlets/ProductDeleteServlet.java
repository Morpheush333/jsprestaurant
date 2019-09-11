package com.mateuszmedon.project.jsprestaurant.servlets;



import com.mateuszmedon.project.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/remove")
public class ProductDeleteServlet extends HttpServlet {

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderStringId = req.getParameter("orderId");
        Long orderId = Long.parseLong(orderStringId);

        String productStringId = req.getParameter("productId");
        Long productId = Long.parseLong(productStringId);

        orderService.removeProduct(orderId, productId);

        String referer = req.getHeader("referer");
        resp.sendRedirect(referer);
    }
}
