package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.dao.CountryDao;
import net.leludo.gtrchamp.dao.DaoFactory;

/**
 * Web service for all country requests.
 */
@Path("/country")
public class CountryWebService {

    @Context
    private ServletContext servletContext;

    /**
     * @return The countries list
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> countries() {
        CountryDao dao  = DaoFactory.countryDao();
        List<Country> country = dao.all();
        dao.close();
        return country;
    }

    /**
     * Create a new country.
     *
     * @param params
     *            The country request parameters needed to create the country
     * @return HTTP 200 if the country has been created, HTTP 406 (not
     *         acceptable) if one of the request parameters is wrong.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final CountryParams params) {
        Response response;

        CountryDao dao  = DaoFactory.countryDao();
        String name = params.getName();

        if (name == null || name.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Country name is missing !"))
                    .build();
        } else {
            Country country = new Country(name);
            dao.create(country);
            response = Response
                    .ok(new WsReturn(country.getId(), "Country " + country.getName() + " added !"))
                    .build();
        }

        dao.close();
        return response;
    }

    /**
     * Update an existing country.
     *
     * @param params
     *            The country request parameters needed to update the country
     * @return HTTP 200 if the country has been updated, HTTP 404 (not found) if
     *         the country to update doesn't exists, HTTP 406 (not acceptable)
     *         if one of the request parameters is wrong.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(final CountryParams params) {
        Response response;

        CountryDao dao  = DaoFactory.countryDao();

        String name = params.getName();

        if (name == null || name.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Country name is missing !"))
                    .build();
        } else {
            Country country = dao.find(params.getId().intValue());
            if (country != null) {
                country.setName(name);
                dao.update(country);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Country  " + country.getName() + " updated !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Driver #" + params.getId() + " not found !"))
                        .build();
            }
        }
        dao.close();
        return response;
    }

    /**
     * Delete an existing country.
     *
     * @param id
     *            The id of the country to delete
     * @return HTTP 200 if the country has been deleted, HTTP 404 (not found) if
     *         the country to delete doesn't exists
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final int id) {
        Response response;

        CountryDao dao  = DaoFactory.countryDao();

        Country country = dao.find(id);
        if (country != null) {
            dao.delete(country);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Country " + country.getName() + " deleted !")).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Country #" + id + " not found !"))
                    .build();
        }
        dao.close();
        return response;
    }

    /**
     * Say if a country have a track.
     *
     * @param id
     *            The id of the country to ask
     * @return {code:1,message:""} if the country have at least one track
     *         {code:0,message:""} if the country have no track
     */
    @GET
    @Path("/{id}/havetrack")
    @Produces(MediaType.APPLICATION_JSON)
    public Response haveTrack(@PathParam("id") final Integer id) {
        CountryDao dao  = DaoFactory.countryDao();
        boolean trackCount = dao.haveTack(id);
        return Response.ok().entity(new WsReturn(trackCount ? 1 : 0, "")).build();
    }
}
