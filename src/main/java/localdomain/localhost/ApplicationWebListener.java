/*
 * Copyright 2010-2013, the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package localdomain.localhost;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import localdomain.localhost.domain.Product;
import net.leludo.gtrchamp.dao.ChampionnatDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * In charge of the lifecycle of the application components.
 * <p/>
 * As we don't use any dependency injection framework in this sample (Java EE CDI, Spring Framework, Google Guice, ...),
 * we need to manage the instantiation and
 * <p/>
 * <ul>
 * <li>Initialise the JPA {@link EntityManagerFactory}.</li>
 * <li>Inject demo data</li>
 * </ul>
 *
 * @author <a href="mailto:cleclerc@cloudbees.com">Cyrille Le Clerc</a>
 */
//@WebListener
public class ApplicationWebListener implements ServletContextListener {
    private static EntityManagerFactory entityManagerFactory;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // CREATE JPA ENTITY MANAGER FACTORY
        entityManagerFactory = Persistence.createEntityManagerFactory("localdomain.localhost");
        servletContextEvent.getServletContext().setAttribute(EntityManagerFactory.class.getName(), entityManagerFactory);
        logger.debug("JPA EntityManagerFactory created");

        // DEMO INSERT FAKE DATA
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createQuery("select p from Product p");
        List resultList = query.getResultList();
        if (resultList.isEmpty()) {
            em.getTransaction().begin();
            em.persist(new Product(
                    "Long Island Iced Tea",
                    "http://bees-shop.s3-website-us-east-1.amazonaws.com/340757408_d3cbdba2f2.jpg",
                    "http://bees-shop.s3-website-us-east-1.amazonaws.com/340757408_d3cbdba2f2.txt"));
            em.persist(new Product(
                    "Sex on the beach",
                    "http://bees-shop.s3-website-us-east-1.amazonaws.com/5115940004_2825a4548e.jpg",
                    "http://bees-shop.s3-website-us-east-1.amazonaws.com/5115940004_2825a4548e.txt"));
            em.getTransaction().commit();
            logger.debug("Demo products inserted in the database");
        } else {
            logger.debug("Don't insert demo products in the database, {} products already found in the db", resultList.size());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            logger.debug("JPA EntityManagerFactory closed");
        }
    }
    
}
