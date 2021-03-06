package net.leludo.gtrchamp.ws;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.DriverDao;

/**
 * Administration driver web service.
 *
 */
@Path("/driver")
public class DriverWebService {

    /**
     * Return the list of the drivers.
     *
     * @return the driver list to JSON format
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Driver> drivers() {
        DriverDao dao = DataManager.getInstance().getManager().driverDao();
        return dao.findAll();
    }

    /**
     * Return the driver from his id.
     *
     * @param id
     *            the id of the driver to search
     *
     * @return the driver corresponding to the id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver driver(@PathParam("id") final int id) {
        DriverDao dao = DataManager.getInstance().getManager().driverDao();
        return dao.find(id);
    }

    /**
     * Create a new driver.
     *
     * @param params
     *            The driver request parameters needed to create the driver
     * @return HTTP 200 if the driver has been created, HTTP 406 (not
     *         acceptable) if one of the request parameters is wrong.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final DriverParams params) {

        Response response;

        String name = params.getName();
        String birthdate = params.getBirthdate();

        if (name == null || "".equals(name)) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Driver firstname is missing !"))
                    .build();
        } else {
            LocalDate date = null;
            if (birthdate != null && !"".equals(birthdate)) {
                date = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            Driver driver = new Driver(name, date);
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            DriverDao dao = daoFactory.driverDao();
            dao.create(driver);
            daoFactory.close();
            response = Response
                    .ok(new WsReturn(driver.getId(), "Driver " + driver.getName() + " added !"))
                    .build();
        }

        return response;
    }

    /**
     * Update an existing driver.
     *
     * @param params
     *            The driver request parameters needed to update the driver
     * @return HTTP 200 if the driver has been updated, HTTP 404 (not found) if
     *         the driver to update doesn't exists, HTTP 406 (not acceptable) if
     *         one of the request parameters is wrong.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(final DriverParams params) {

        Response response;

        String name = params.getName();
        String birthdate = params.getBirthdate();

        if (name == null || "".equals(name)) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Diver firstname is missing !"))
                    .build();
        } else {
            LocalDate date = null;
            if (birthdate != null && !"".equals(birthdate)) {
                date = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            DriverDao dao = daoFactory.driverDao();
            Driver driver = dao.find(params.getId());
            if (driver != null) {
                driver.setName(name);
                driver.setBirthdate(date);
                dao.update(driver);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Driver " + driver.getName() + " updated !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Driver #" + params.getId() + " not found !"))
                        .build();
            }
            daoFactory.close();
        }
        return response;
    }

    /**
     * Delete an existing driver.
     *
     * @param id
     *            The id of the driver to delete
     * @return HTTP 200 if the driver has been deleted, HTTP 404 (not found) if
     *         the driver to delete doesn't exists
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final int id) {

        Response response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        DriverDao dao = daoFactory.driverDao();
        Driver pilodriverte = dao.find(id);
        if (pilodriverte != null) {
            dao.delete(pilodriverte);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Driver " + pilodriverte.getName() + " deleted !")).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Driver #" + id + " not found !"))
                    .build();
        }
        daoFactory.close();
        return response;
    }

    /**
     * Say if a driver ran a race.
     *
     * @param id
     *            The id of the driver to ask
     * @return {code:1,message:""} if the driver ran at least one race
     *         {code:0,message:""} if the driver didn't run a race
     */
    @GET
    @Path("/{id}/ran")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ran(@PathParam("id") final Integer id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        DriverDao dao = daoFactory.driverDao();
        boolean ran = dao.ran(id);
        daoFactory.close();
        return Response.ok().entity(new WsReturn(ran ? 1 : 0, "")).build();
    }
}
