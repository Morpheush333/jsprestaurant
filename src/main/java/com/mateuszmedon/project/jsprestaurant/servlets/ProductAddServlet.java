package com.mateuszmedon.project.jsprestaurant.servlets;




import com.mateuszmedon.project.jsprestaurant.model.Product;
import com.mateuszmedon.project.jsprestaurant.services.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/add")
public class ProductAddServlet extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");

        req.setAttribute("orderIdAttribute", orderIdParam);

        req.getRequestDispatcher("/product-form.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String orderIdString = req.getParameter("orderId");
        String amountString = req.getParameter("amountParam");
        String valueString = req.getParameter("valueParam");

        String description = req.getParameter("descriptionParam");
        Long orderId= Long.parseLong(orderIdString);
        Integer amount = Integer.parseInt(amountString);
        Double value = Double.parseDouble(valueString);

        Product product = new Product();
        product.setAmount(amount);
        product.setValue(value);
        product.setDescription(description);

        orderService.addProduct(orderId, product);

        resp.sendRedirect("/product/list?orderId=" + orderIdString);

    }
}
