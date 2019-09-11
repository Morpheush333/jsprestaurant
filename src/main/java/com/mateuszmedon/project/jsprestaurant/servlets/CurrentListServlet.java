package com.mateuszmedon.project.jsprestaurant.servlets;



import com.mateuszmedon.project.jsprestaurant.model.Order;
import com.mateuszmedon.project.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/order/current")
public class CurrentListServlet extends HttpServlet {
    //    TODO uzupelnij servlet.

    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList = orderService.printUnpaid();

        req.setAttribute("orderListAttribute", orderList);

        req.getRequestDispatcher("/order-list.jsp").forward(req,resp);
    }
}
