package com.mateuszmedon.project.jsprestaurant.servlets;




import com.mateuszmedon.project.jsprestaurant.model.Order;
import com.mateuszmedon.project.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order/add")
public class OrderAddServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/order-form.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String peopleCountString = req.getParameter("peopleCountParam");
        String tableCountString = req.getParameter("tableNumberParam");

        int peopleCount = Integer.parseInt(peopleCountString);
        int tableNumber = Integer.parseInt(tableCountString);

        Order order = new Order();
        order.setPeopleCount(peopleCount);
        order.setTableNumber(tableNumber);

        orderService.add(order);

        resp.sendRedirect("/order/list");


    }
}
