package com.mateuszmedon.project.jsprestaurant.servlets;




import com.mateuszmedon.project.jsprestaurant.model.Order;
import com.mateuszmedon.project.jsprestaurant.model.Product;
import com.mateuszmedon.project.jsprestaurant.services.OrderService;
import com.mateuszmedon.project.jsprestaurant.services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/product/edit")
public class ProductEditServlet extends HttpServlet {
        private ProductService productService = new ProductService();
        private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");
        String productIdParam = req.getParameter("productId");

        Long productId = Long.parseLong(productIdParam);
        Optional<Product> productOptional = productService.findById(productId);

        if(!productOptional.isPresent()){
            resp.sendRedirect("/product/list?orderId=" + orderIdParam);
            return;
        }

        req.setAttribute("orderIdAttribute", orderIdParam);
        req.setAttribute("productAttribute", productOptional.get());

        req.getRequestDispatcher("/product-form.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdString = req.getParameter("orderId");
        String amountString = req.getParameter("amountParam");
        String valueString = req.getParameter("valueParam");
        String editedProductIdString = req.getParameter("editedProductId");

        String description = req.getParameter("descriptionParam");
        Long orderId= Long.parseLong(orderIdString);
        Long editedProductId = Long.parseLong(editedProductIdString);
        Integer amount = Integer.parseInt(amountString);
        Double value = Double.parseDouble(valueString);

        Product product = new Product();
        product.setId(editedProductId);
        product.setAmount(amount);
        product.setValue(value);
        product.setDescription(description);

        Optional<Order> orderOptional = orderService.findById(orderId);
        if (!orderOptional.isPresent()){
            resp.sendRedirect("/order/list");
            return;
        }
        product.setOrder(orderOptional.get());


//        you dont as well really write this code on top optional
//        you can use a OrderService Class to use method addProduct
        productService.update(product);

        resp.sendRedirect("/product/list?orderId=" + orderIdString);




    }
}
