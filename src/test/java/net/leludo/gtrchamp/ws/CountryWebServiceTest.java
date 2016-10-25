package net.leludo.gtrchamp.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import net.leludo.gtrchamp.dao.CountryDao;

//@RunWith(MockitoJUnitRunner.class)
public class CountryWebServiceTest extends JerseyTest  {
    @Mock
    private CountryDao dao;
    @InjectMocks
    private CountryWebService countryWebService;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Override
    protected Application configure() {
//        /return new ResourceConfig(CountryWebService.class);
        return new ResourceConfig(HelloResource.class);

    }
    
    @Path("hello")
    public static class HelloResource {
        @GET
        public String getHello() {
            return "Hello World!";
        }
    }
    
    @Test
    public void test() {
        final String hello = target("hello").request().get(String.class);
        assertEquals("Hello World!", hello);
    }
    @Test
    public void testCountries() throws Exception {
        Response response = target("/country").request().get();
        assertNotNull(response);
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

}
