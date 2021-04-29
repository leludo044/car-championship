package net.leludo.gtrchamp.ws;

import java.util.List;
import java.util.Properties;

import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.dao.CountryDao;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Web service for all country requests.
 */
@RestController
@RequestMapping("/country")
public class CountryWebService {

    /**
     * Return the countries list.
     *
     * @return The countries list
     */
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> countries() {
        CountryDao dao = DataManager.getInstance().getManager().countryDao();
        return dao.all();
    }

    /**
     * Create a new country.
     *
     * @param params
     *            The country request parameters needed to create the country
     * @return HTTP 200 if the country has been created, HTTP 406 (not
     *         acceptable) if one of the request parameters is wrong.
     */

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Properties create(@RequestBody final CountryParams params) {
        Properties response = new Properties();

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        CountryDao dao = daoFactory.countryDao();

        String name = params.getName();

        if (name == null || "".equals(name)) {
            response.put("code", HttpStatus.NOT_ACCEPTABLE.value());
            response.put("message", "Country name is missing !");
        } else {
            Country country = new Country(name);
            dao.create(country);
            response.put("code", country.getId());
            response.put("message", "Country " + country.getName() + " added !");
        }

        daoFactory.close();
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

    @ResponseBody
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Properties update(@RequestBody final CountryParams params) {
        Properties response = new Properties();

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        CountryDao dao = daoFactory.countryDao();

        String name = params.getName();

        if (name == null || "".equals(name)) {
            response.put("code", HttpStatus.NOT_ACCEPTABLE.value());
            response.put("message", "Country name is missing !");

        } else {

            Country country = dao.find(params.getId());
            if (country != null) {
                country.setName(name);
                dao.update(country);
                response.put("code", HttpStatus.OK.value());
                response.put("message", "Country  " + country.getName() + " updated !");
            } else {
                response.put("code", HttpStatus.NOT_FOUND.value());
                response.put("message", "Driver #" + params.getId() + " not found !");
            }

            daoFactory.close();
        }
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
    @ResponseBody
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Properties delete(@PathVariable("id") final int id) {
        Properties response = new Properties();

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        CountryDao dao = daoFactory.countryDao();

        Country country = dao.find(id);
        if (country != null) {
            dao.delete(country);

            response.put("code", HttpStatus.OK.value());
            response.put("message", "Country " + country.getName() + " deleted !");
        } else {
            response.put("code", HttpStatus.NOT_FOUND.value());
            response.put("message", "Country #" + id + " not found !");
        }

        daoFactory.close();
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
    @ResponseBody
    @GetMapping(path = "/{id}/havetrack", produces = MediaType.APPLICATION_JSON_VALUE)
    public Properties haveTrack(@PathVariable("id") final Integer id) {
        Properties response = new Properties();
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        CountryDao dao = daoFactory.countryDao();
        boolean trackCount = dao.haveTack(id);
        daoFactory.close();
        response.put("code", trackCount ? 1 : 0);
        response.put("message", "");
        return response;
    }
}
