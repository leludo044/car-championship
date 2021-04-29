package net.leludo.gtrchamp.ws;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.DriverDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Administration driver web service.
 *
 */
@RestController
@RequestMapping("/driver")
public class DriverWebService {

    /**
     * Return the list of the drivers.
     *
     * @return the driver list to JSON format
     */
    @ResponseBody
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ResponseBody
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Driver driver(@PathVariable("id") final int id) {
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
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> create(@RequestBody final DriverParams params) {

        ResponseEntity<WsReturn> response;

        String name = params.getName();
        String birthdate = params.getBirthdate();

        if (name == null || "".equals(name)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Driver firstname is missing !"), HttpStatus.NOT_ACCEPTABLE);
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
            response = new ResponseEntity<>(new WsReturn(driver.getId(), "Driver " + driver.getName() + " added !"), HttpStatus.OK);
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
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> update(@RequestBody final DriverParams params) {

        ResponseEntity<WsReturn> response;

        String name = params.getName();
        String birthdate = params.getBirthdate();

        if (name == null || "".equals(name)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Diver firstname is missing !"), HttpStatus.NOT_ACCEPTABLE);
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
                response = new ResponseEntity<>(new WsReturn(HttpStatus.OK.value(),
                        "Driver " + driver.getName() + " updated !"), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                                "Driver #" + params.getId() + " not found !"), HttpStatus.NOT_FOUND);
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
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> delete(@PathVariable("id") final int id) {

        ResponseEntity<WsReturn>  response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        DriverDao dao = daoFactory.driverDao();
        Driver pilodriverte = dao.find(id);
        if (pilodriverte != null) {
            dao.delete(pilodriverte);
            response = new ResponseEntity<>(new WsReturn(HttpStatus.OK.value(),
                    "Driver " + pilodriverte.getName() + " deleted !"), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                            "Driver #" + id + " not found !"), HttpStatus.NOT_FOUND);
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
    @GetMapping(path = "/{id}/ran", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> ran(@PathVariable("id") final Integer id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        DriverDao dao = daoFactory.driverDao();
        boolean ran = dao.ran(id);
        daoFactory.close();
        return new ResponseEntity<>(new WsReturn(ran ? 1 : 0, ""), HttpStatus.OK);
    }
}
