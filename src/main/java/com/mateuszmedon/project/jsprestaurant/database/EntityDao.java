package com.mateuszmedon.project.jsprestaurant.database;




import com.mateuszmedon.project.jsprestaurant.model.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntityDao {
    public void saveOrUpdate(BaseEntity entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            // the logic there
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } // try with resources - close session (session.close())
            // automatic after close `}` try
        // probably of the import of the interface autoClosable
    }

    public <T extends BaseEntity> T getById(Class<T> classT, Long id){
        SessionFactory factory = HibernateUtil.getSessionFactory();

        try (Session session = factory.openSession()) {
            T result = session.get(classT, id);
            return result;
        }
    }

    public <T extends BaseEntity> List<T> printOrders(Class<T> classT) {
        List<T> orderList = new ArrayList<>();

        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            //  querry builder
            CriteriaBuilder builder = session.getCriteriaBuilder();

            // create object which have a query to OBJECT Order
            CriteriaQuery<T> criteriaQuery = builder.createQuery(classT);

            // Table which we make a query search;
            Root<T> table = criteriaQuery.from(classT);

            // Make a query in table, use a criteria "criteria query"
            criteriaQuery.select(table);

            // Make a query in database and print in list
            orderList.addAll(session.createQuery(criteriaQuery).list());
        }
        return orderList;
    }

    public void delete(BaseEntity entity) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } // try with resources - close session (session.close())
        // auto close after end of braket in try
    }
}
