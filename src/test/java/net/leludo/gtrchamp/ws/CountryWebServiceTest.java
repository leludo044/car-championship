package net.leludo.gtrchamp.ws;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.leludo.connector.BdConnector;
import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.dao.DaoFactory;

public class CountryWebServiceTest extends JerseyTest {

    DaoFactory daoFactory;

    // @Mock
    // private CountryDao dao;

    // @InjectMocks
    private CountryWebService countryWebService;

//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//    }    
    
    @Override
    protected Application configure() {

        init();
        /// daoFactory = Mockito.spy(DaoFactory.class);
        // Mockito.when(daoFactory.countryDao()).thenReturn(dao);

        return new ResourceConfig(CountryWebService.class);
        // return new ResourceConfig(HelloResource.class);

    }

    @Test
    public void testCountries() throws Exception {
        String response = target("/country").request().get(String.class);
        assertNotNull(response);

        ObjectMapper mapper = new ObjectMapper();
        List<Country> countries = mapper.readValue(response, List.class);
        System.out.println(countries);
        assertTrue(countries.size() > 0);
    }

    @Test
    public void testCreate() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testUpdate() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testDelete() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    @Test
    public void testHaveTrack() throws Exception {
        throw new RuntimeException("not yet implemented");
    }

    private void init() {
        EntityManagerFactory emf;
        String uri = System.getenv("CLEARDB_DATABASE_URL");
        if (uri != null) {
            try {
                URI dbUri;
                dbUri = new URI(uri);

                BdConnector connector = new BdConnector(dbUri);

                Properties properties = new Properties();
                properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
                properties.put("hibernate.connection.url", connector.getUrl());
                properties.put("hibernate.connection.username", connector.getUsername());
                properties.put("hibernate.connection.password", connector.getPassword());
                properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
                properties.put("hibernate.show_sql", "false");

                emf = Persistence.createEntityManagerFactory("gtrchamp", properties);

                DaoFactory.getInstance().setEntityManagerfactory(emf);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
