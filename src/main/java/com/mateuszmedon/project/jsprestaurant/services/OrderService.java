package com.mateuszmedon.project.jsprestaurant.services;



import com.mateuszmedon.project.jsprestaurant.database.EntityDao;
import com.mateuszmedon.project.jsprestaurant.database.HibernateUtil;
import com.mateuszmedon.project.jsprestaurant.model.Order;
import com.mateuszmedon.project.jsprestaurant.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private EntityDao entityDao = new EntityDao();

    public void add(Order order) {
        entityDao.saveOrUpdate(order);
    }

    public void delete(Long orderId) {
        Order order = entityDao.getById(Order.class, orderId);
        entityDao.delete(order);
    }

    public List<Order> findAll() {
        return entityDao.printOrders(Order.class);
    }

    public void delivered(Long id) {

        Order delivered = entityDao.getById(Order.class, id); //download for edytion
        delivered.setTimeDelivered(LocalDateTime.now()); // set change

        entityDao.saveOrUpdate(delivered);
    }

    public void paid(Long id, Double amount) {
// download object from database
        Order order = entityDao.getById(Order.class, id);
        order.setPaid(amount);

        // if amount is to low sout....
        if (order.getPaid() < order.getToPay()) {
            System.out.println("You dont pay enough");
        } else if (order.getPaid() == order.getToPay()) {
            System.out.println("Where is a tip?");
        }

        // save object back to database
        entityDao.saveOrUpdate(order);

    }

    public void addProduct(Long orderId, Product product) {
        Order orderToAdd = entityDao.getById(Order.class, orderId);

        product.setOrder(orderToAdd);
        entityDao.saveOrUpdate(product); // check if order is in the database

        // lines of the bottom is optional
//        orderToAdd.getProducts().add(product);
//        entityDao.saveOrUpdate(orderToAdd); // save order to product
    }

    public Optional<Order> findById(Long idOrder) {
        Order order = entityDao.getById(Order.class, idOrder);

        // sent Optional (pack) with object witch can by null;
        return Optional.ofNullable(order);
    }

    public void removeProduct(Long idOrder, Long idProduct) {
//        Order order = entityDao.getById(Order.class, idOrder);

//        Product product = entityDao.getById(Product.class, idProduct);
//        if (product != null) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Order order = session.get(Order.class, idOrder);
            Product product = session.get(Product.class, idProduct);

            order.getProducts().remove(product); //delete relation (reference).
            product.setOrder(null);

            session.saveOrUpdate(order);
            session.delete(product);
            transaction.commit();
        }
    }
//        }else {
//            System.err.println("No product in order");
//        }
//    }

    public List<Order> printUndeliver() {
        List<Order> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            //  querry builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // create object which have a query O OBJECT Order
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);

            // Table which we make a query search;
            Root<Order> table = criteriaQuery.from(Order.class);

            // Make a query in table, use a criteria "criteria query"
            criteriaQuery.select(table)
                    .where(
                            builder.isNull(table.get("timeDelivered")));
            //TODO: specific queries of undelivered, not all. (look top - ready)

            // Make a query in database and print in list
            orderList.addAll(session.createQuery(criteriaQuery).list());
        }
        return orderList;
    }

    public List<Order> printUnpaid() {
        List<Order> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            //  querry builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // create object which have a query O OBJECT Order
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);

            // Table which we make a query search;
            Root<Order> table = criteriaQuery.from(Order.class);

            // Make a query in table, use a criteria "criteria query"
            criteriaQuery.select(table)
                    .where(
                            builder.isNull(table.get("paid")));

            // Make a query in database and print in list
            orderList.addAll(session.createQuery(criteriaQuery).list());
        }
        return orderList;
    }

    public Double sumPaid() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            //  querry builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // create object which have a query O OBJECT Order
            CriteriaQuery<Double> criteriaQuery = builder.createQuery(Double.class);

            // Table which we make a query search;
            Root<Order> table = criteriaQuery.from(Order.class);

            // Make a query in table, use a criteria "criteria query"
            LocalDate today = LocalDate.now();
            criteriaQuery
                    .select(
                            builder.sum(table.get("paid")))
                    .where(
                            builder.between(table.get("timeOrdered"),
                                    today.atStartOfDay(),
                                    today.plusDays(1).atStartOfDay()));

//                                    today.atTime(9,0),
//                                    today.atTime(22,0)));

            // Make a query in database and print in list
            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }

    public void paid(Long id) {
        Order order = entityDao.getById(Order.class, id);
        if (order != null) {
            order.setPaid(order.getToPay());
            entityDao.saveOrUpdate(order);
        } else {
            System.err.println("Dont have order in database");
        }
    }

    public List<Order> readyToCollect() {
        List<Order> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            //  querry builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // create object which have a query O OBJECT Order
            CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);

            // Table which we make a query search;
            Root<Order> table = criteriaQuery.from(Order.class);

            // Make a query in table, use a criteria "criteria query"
            LocalDate today = LocalDate.now();
            criteriaQuery.select(table)
                    .where(
                            builder.and(
                                    builder.between(
                                            table.get("timeOrdered"),
                                            today.atStartOfDay(),
                                            today.plusDays(1).atStartOfDay()

//                                    today.atTime(9,0),
//                                    today.atTime(22,0)));
                                    ),
                                    builder.isNull(
                                            table.get("paid"))));

            orderList.addAll(session.createQuery(criteriaQuery).list());
        }
        return orderList;
    }

}
