package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.Track;
import net.leludo.gtrchamp.dao.CountryDao;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.TrackDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Track web service.
 *
 * <ul>
 * <li>GET /track/</li>
 * <li>GET /track/:id</li>
 * <li>POST /track/</li>
 * <li>PUT /track/:id</li>
 * <li>DELETE /track/:id</li>
 * </ul>
 *
 */
@RestController
@RequestMapping("/track")
public class TrackWebService {

    /**
     * @return the track list in JSON format
     */
    @ResponseBody
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Track> tracks() {
        TrackDao dao = DataManager.getInstance().getManager().trackDao();
        return dao.findAll();
    }

    /**
     * Return the track from his id.
     *
     * @param id
     *            the id of the track to search
     *
     * @return the track corresponding to the id
     */
    @ResponseBody
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Track track(@PathVariable("id") final int id) {
        TrackDao dao = DataManager.getInstance().getManager().trackDao();
        return dao.find(id);
    }

    /**
     * Create a new track.
     *
     * @param params
     *            The track request parameters needed to create the track
     * @return HTTP 200 if the track has been created, HTTP 406 (not acceptable)
     *         if one of the request parameters is wrong.
     */
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> create(@RequestBody final TrackParams params) {
        String name = params.getName();
        String countryId = params.getCountryId();

        ResponseEntity<WsReturn> response;
        String message = validate(params);

        if (StringUtils.isNotEmpty(message)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(), message), HttpStatus.NOT_ACCEPTABLE);
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            TrackDao dao = daoFactory.trackDao();
            CountryDao countryDao = daoFactory.countryDao();
            Country country = countryDao.find(Integer.valueOf(countryId));
            Track track = new Track(name, params.getLength(), country);
            dao.create(track);
            response = new ResponseEntity<>(new WsReturn(track.getId(), "Track " + track.getName() + " created !"), HttpStatus.OK);
            daoFactory.close();
        }

        return response;

    }

    /**
     * Validate the informations of a track.
     *
     * @param params
     *            The track request parameters received
     * @return An error message of a blank string if all is right
     */
    private String validate(final TrackParams params) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        CountryDao countryDao = daoFactory.countryDao();

        String name = params.getName();
        String countryId = params.getCountryId();
        String message = "";

        if (name == null || "".equals(name)) {
            message = "Track name is missing !";
        } else if (params.getLength() == null) {
            message = "Track length is missing !";
        } else if (countryId == null || "".equals(countryId)) {
            message = "Country is missing !";
        } else if (countryDao.find(Integer.valueOf(countryId)) == null) {
            message = "Country #" + countryId + " not found !";
        } else {
            try {
                Float.valueOf(params.getLength());
            } catch (final NumberFormatException nfe) {
                message = "Track length must be a numeric value !";
            }
        }

        daoFactory.close();

        return message;
    }

    /**
     * Update an existing track.
     *
     * @param params
     *            The track request parameters needed to update the track
     * @return HTTP 200 if the track has been updated, HTTP 404 (not found) if
     *         the track to update doesn't exists, HTTP 406 (not acceptable) if
     *         one of the request parameters is wrong.
     */
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> update(@RequestBody final TrackParams params) {

        String name = params.getName();
        String countryId = params.getCountryId();

        ResponseEntity<WsReturn> response;
        String message = validate(params);

        if (StringUtils.isNotEmpty(message)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(), message), HttpStatus.NOT_ACCEPTABLE);
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            TrackDao dao = daoFactory.trackDao();
            CountryDao countryDao = daoFactory.countryDao();
            Country country = countryDao.find(Integer.valueOf(countryId));
            Track track = dao.find(params.getId());
            if (track != null) {
                track.setName(name);
                track.setLength(params.getLength());
                track.setCountry(country);
                dao.update(track);
                response = new ResponseEntity<>(new WsReturn(HttpStatus.OK.value(),
                        "TRack " + track.getName() + " updated !"), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                                "Track #" + params.getId() + " not found !"), HttpStatus.NOT_FOUND);
            }
            daoFactory.close();
        }
        return response;
    }

    /**
     * Delete an existing track.
     *
     * @param id
     *            The id of the track to delete
     * @return HTTP 200 if the track has been deleted, HTTP 404 (not found) if
     *         the track to delete doesn't exists
     */
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> delete(@PathVariable("id") final int id) {
        ResponseEntity<WsReturn> response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        TrackDao dao = daoFactory.trackDao();

        Track track = dao.find(id);
        if (track != null) {
            dao.delete(track);
            response = new ResponseEntity<>(new WsReturn(HttpStatus.OK.value(),
                    "Track " + track.getName() + " deleted !"), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(
                    new WsReturn(HttpStatus.NOT_FOUND.value(), "Track #" + id + " not found !"), HttpStatus.NOT_FOUND);
        }
        daoFactory.close();
        return response;
    }

    /**
     * Say if the track has been run.
     *
     * @param id
     *            The id of the track to ask
     * @return {code:1,message:""} if the track has been run as least once
     *         {code:0,message:""} if the track has not been run
     */
    @GetMapping(path = "/{id}/wasrun", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> wasRun(@PathVariable("id") final Integer id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        TrackDao dao = daoFactory.trackDao();
        boolean wasRun = dao.wasRun(id);
        daoFactory.close();
        return new ResponseEntity<>(new WsReturn(wasRun ? 1 : 0, ""), HttpStatus.OK);
    }
}
