package net.leludo.connector;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Observable;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

	EntityManager entityManager;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*
		 * 
		 * CLEARDB_DATABASE_URL: mysql://b4620b09acec2f:ab5c04c3@eu-cdbr-west-01.cleardb.co
m/heroku_7dedb7d29129dd2?reconnect=true
		 */
		try {
			URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
			//URI dbUri = new URI("mysql://b4620b09acec2f:ab5c04c3@eu-cdbr-west-01.cleardb.com/heroku_7dedb7d29129dd2?reconnect=true");
		
			System.out.println(dbUri.toString());
			System.out.println(dbUri.getUserInfo());
			String username = dbUri.getUserInfo().split(":")[0] ;
			String password = dbUri.getUserInfo().split(":")[1] ;
			
			BdConnector connector = new BdConnector(dbUri);
			
            Properties properties = new Properties();
            properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            properties.put("hibernate.connection.url", connector.getUrl());
            properties.put("hibernate.connection.username", connector.getUsername());
            properties.put("hibernate.connection.password", connector.getPassword());
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
            properties.put("hibernate.show-sql", "false");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "localdomain.localhost", properties);
            entityManager = emf.createEntityManager();
            
            arg0.getServletContext().setAttribute(EntityManagerFactory.class.getName(), emf);

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
