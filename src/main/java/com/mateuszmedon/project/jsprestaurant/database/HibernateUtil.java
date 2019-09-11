package com.mateuszmedon.project.jsprestaurant.database;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

        private static SessionFactory sessionFactory;

        static {
            // load only once when class is init
            // Created object which take config from the hibernate cfg xml
            StandardServiceRegistry standardServiceRegistry = // object load to properties only take XML file
                    new StandardServiceRegistryBuilder()
                            .configure("hibernate.cfg.xml").build();

            // metadata this is info about files. From data load before
            // created object metadata
            Metadata metadata = new MetadataSources(standardServiceRegistry)
                    .getMetadataBuilder().build();

            // created session with data include hibernate cfg xml file
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }

        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }
    }

