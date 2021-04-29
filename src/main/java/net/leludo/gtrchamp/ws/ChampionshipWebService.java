package net.leludo.gtrchamp.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import net.leludo.gtrchamp.Championship;
import net.leludo.gtrchamp.Competitor;
import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.Race;
import net.leludo.gtrchamp.Track;
import net.leludo.gtrchamp.dao.ChampionshipDao;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.TrackDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * Championship web service.
 */
@RestController
@RequestMapping("/championship")
public class ChampionshipWebService {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ChampionshipWebService.class);

    /**
     * Constructor.
     */
    public ChampionshipWebService() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Instanciate " + this.getClass());
        }
    }

    /**
     * Return the championships list.
     *
     * @return The championships list to JSON format
     */
    @ResponseBody
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String championships() {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        List<Championship> chps = championshipDao.all();
        daoFactory.close();
        if (chps == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Championship chp : chps) {
                    g.writeStartObject();
                    g.writeNumberField("id", chp.getId());
                    g.writeStringField("name", chp.getName());
                    g.writeStringField("type", chp.getType());
                    g.writeNumberField("mode", chp.getMode());
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Return a championship.
     *
     * @param id
     *            Id of the Championship to return
     * @return A championship representation to JSON format
     */
    @ResponseBody
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String championship(@PathVariable("id") final int id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship chp = championshipDao.find(id);
        daoFactory.close();
        if (chp == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartObject();
                // g.writeObjectFieldStart("championnat");
                g.writeNumberField("id", chp.getId());
                g.writeStringField("name", chp.getName());
                g.writeStringField("type", chp.getType());
                g.writeNumberField("mode", chp.getMode());
                // g.writeEndObject(); // for field 'name'
                // 9 g.writeStringField("gender", Gender.MALE);
                // 10 g.writeBooleanField("verified", false);
                // 11 g.writeFieldName("userImage"); // no 'writeBinaryField'
                // (yet?)
                // 12 byte[] binaryData = ...;
                // 13 g.writeBinary(binaryData);
                g.writeEndObject();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Create a new championship.
     *
     * @param params
     *            The championship request parameters needed to create the
     *            championship
     * @return HTTP 200 if the championship has been created, HTTP 406 (not
     *         acceptable) if one of the request parameters is wrong.
     */
    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> create(@RequestBody final ChampionshipParams params) {
        ResponseEntity<WsReturn> response;

        String name = params.getName();
        String type = params.getType();
        int mode = params.getMode();

        if (name == null || "".equals(name)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Championship name is missing !"), HttpStatus.NOT_ACCEPTABLE);
        } else if (type == null || "".equals(type)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Championship type is missing !"), HttpStatus.NOT_ACCEPTABLE);
        } else if (mode != 1 && mode != 2) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Championship mode is wrong !"), HttpStatus.NOT_ACCEPTABLE);
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ChampionshipDao championshipDao = daoFactory.championshipDao();
            Championship championship = new Championship(name, type, mode);
            championshipDao.save(championship);
            response = new ResponseEntity<>(new WsReturn(championship.getId(),
                    "Championship " + championship.getName() + " created !"),
                    HttpStatus.OK);
            daoFactory.close();
        }

        return response;
    }

    /**
     * Update an existing championship.
     *
     * @param params
     *            The championship request parameters needed to update the
     *            championship
     * @return HTTP 200 if the championship has been updated, HTTP 404 (not
     *         found) if the championship to update doesn't exists, HTTP 406
     *         (not acceptable) if one of the request parameters is wrong.
     */
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> update(@RequestBody final ChampionshipParams params) {
        ResponseEntity<WsReturn> response;

        String name = params.getName();
        String type = params.getType();
        int mode = params.getMode();

        if (name == null || "".equals(name)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Championship name is missing !"), HttpStatus.NOT_ACCEPTABLE);
        } else if (type == null || "".equals(type)) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Championship type is missing !"), HttpStatus.NOT_ACCEPTABLE);
        } else if (mode != 1 && mode != 2) {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_ACCEPTABLE.value(),
                            "Championship mode is wrong !"), HttpStatus.NOT_ACCEPTABLE);
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ChampionshipDao championshipDao = daoFactory.championshipDao();
            Championship championship = championshipDao.find(params.getId());
            if (championship != null) {
                championship.setName(name);
                championship.setType(type);
                championship.setMode(mode);
                championshipDao.update(championship);
                response = new ResponseEntity<>(new WsReturn(HttpStatus.OK.value(),
                        "Championship " + championship.getName() + " updated !"), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                                "Driver #" + params.getId() + " not found !"), HttpStatus.OK);
            }
            daoFactory.close();
        }
        return response;
    }

    /**
     * Delete an existing championship.
     *
     * @param id
     *            The id of the championship to delete
     * @return HTTP 200 if the championship has been deleted, HTTP 404 (not
     *         found) if the championship to delete doesn't exists
     */

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> delete(@PathVariable("id") final int id) {
        ResponseEntity<WsReturn> response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();

        Championship championship = championshipDao.find(id);
        if (championship != null) {
            championshipDao.delete(championship);
            response = new ResponseEntity<>(new WsReturn(HttpStatus.OK.value(),
                    "Championship " + championship.getName() + " deleted !"), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                            "Championship #" + id + " not found !"), HttpStatus.NOT_FOUND);
        }
        daoFactory.close();
        return response;
    }

    /**
     * Say if a championship is started (at least one race has been ran).
     *
     * @param id
     *            The id of the championship to ask
     * @return {code:1,message:""} if the championship is started
     *         {code:0,message:""} if the championship is not started
     */
    @GetMapping(path = "/{id}/isstarted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> isStarted(@PathVariable("id") final Integer id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        boolean isStarted = championshipDao.isStarted(id);
        daoFactory.close();
        return new ResponseEntity<>(new WsReturn(isStarted ? 1 : 0, ""), HttpStatus.OK);
    }

    /**
     * Return the scheduled races for a championship.
     *
     * @param championshipId
     *            The id of the championship to ask
     * @return Return the scheduled races for a championship in JSON format
     */
    @ResponseBody
    @GetMapping(path = "/{championshipId}/race/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public String races(@PathVariable("championshipId") final int championshipId) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship chp = championshipDao.find(championshipId);
        daoFactory.close();
        if (chp == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Race gp : chp.getPlannedRaces()) {
                    g.writeStartObject();
                    g.writeNumberField("id", gp.getId());
                    g.writeStringField("name", gp.getTrack().getName());
                    g.writeNumberField("length", gp.getTrack().getLength());
                    g.writeStringField("date", gp.getDateFr());
                    g.writeStringField("country", gp.getTrack().getCountry().getName());
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Return the standings of a championship.
     *
     * @param championshipId
     *            The id of the championship to ask
     * @return The standings of a championship in JSON format
     */
    @ResponseBody
    @GetMapping(path = "/{championshipId}/standings", produces = MediaType.APPLICATION_JSON_VALUE)
    public String standings(@PathVariable("championshipId") final int championshipId) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        List<Object[]> standings = championshipDao.standings(championshipId);
        daoFactory.close();
        if (standings == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                int rang = 1;
                for (Object[] pilote : standings) {
                    g.writeStartObject();
                    g.writeNumberField("rang", rang++);
                    g.writeStringField("name", ((Driver) pilote[0]).getName());
                    g.writeNumberField("points", (Long) pilote[1]);
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Return the scheduled races for a championship.
     *
     * @param id
     *            The id of the championship to ask
     * @return The scheduled races for a championship in JSON format
     */
    @ResponseBody
    @GetMapping(path = "/{id}/tracks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable("id") final Integer id) {
        List<Race> races;
        ResponseEntity<?> response;
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            races = championship.getPlannedRaces();
            response = new ResponseEntity<>(races, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                            "Championship #" + id + " not found !"), HttpStatus.NOT_FOUND);
        }
        daoFactory.close();

        return response;
    }

    /**
     * Schedule a race for a championship.
     *
     * @param id
     *            The id of the championship for which to schedule the race
     * @param trackId
     *            The id of the track to schedule
     * @param params
     *            The race request parameters needed to schedule it for the
     *            championship
     * @return HTTP 200 if the race has been scheduled, HTTP 404 (not found) if
     *         the championship or the track to schedule doesn't exists
     */
    @PutMapping(path = "/{id}/{trackId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> update(@PathVariable("id") final Integer id,
            @PathVariable("trackId") final Integer trackId, @RequestBody final RaceParams params) {
        ResponseEntity<WsReturn> response;
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        TrackDao trackDao = daoFactory.trackDao();
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            Track track = trackDao.find(trackId);
            if (track != null) {
                LocalDate date = null;
                if (params.getDate() != null && !"".equals(params.getDate())) {
                    date = LocalDate.parse(params.getDate(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
                championship.planRace(track, date);
                championshipDao.update(championship);
                response = new ResponseEntity<>(new WsReturn(championship.lastRace().getId(), "Track " + track.getName()
                                + " added to championship " + championship.getName() + "."), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                                "Track #" + trackId + " not found !"), HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                            "Championship #" + id + " not found !"), HttpStatus.NOT_FOUND);
        }
        daoFactory.close();
        return response;
    }

    /**
     * Cancel a race for a championship.
     *
     * @param id
     *            The id of the championship for which to cancel the race
     * @param trackId
     *            The id of the track to cancel
     * @return HTTP 200 if the race has been canceled, HTTP 404 (not found) if
     *         the championship or the track to cancel doesn't exists
     */
    @DeleteMapping(path = "/{id}/{trackId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WsReturn> remove(@PathVariable("id") final Integer id,
            @PathVariable("trackId") final Integer trackId) {
        ResponseEntity<WsReturn> response;
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            if (trackId != null) {
                Race race = championship.cancelRace(trackId);
                championshipDao.update(championship);
                response = new ResponseEntity<>(
                        new WsReturn(HttpStatus.OK.value(), "Track " + race.getTrack().getName()
                                + " deleted from championship " + championship.getName() + "."), HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                                "Undefined track to delete !"), HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>(new WsReturn(HttpStatus.NOT_FOUND.value(),
                            "Championship #" + id + " not found !"), HttpStatus.NOT_FOUND);
        }
        daoFactory.close();

        return response;
    }

    /**
     * Return the results of a race.
     *
     * @param raceId
     *            The id of the race to ask
     * @return The results of a race in JSON format
     */
    @GetMapping(path = "/race/{raceId}/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public String results(@PathVariable("raceId") final int raceId) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        List<Object[]> results = championshipDao.results(raceId);
        daoFactory.close();
        if (results == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Object[] concurrent : results) {
                    g.writeStartObject();
                    g.writeNumberField("piloteId",
                            ((Competitor) concurrent[0]).getDriver().getId());
                    g.writeStringField("name", ((Competitor) concurrent[0]).getDriver().getName());
                    g.writeNumberField("startingPosition",
                            ((Competitor) concurrent[0]).getStartingPosition());
                    g.writeNumberField("arrivalPosition",
                            ((Competitor) concurrent[0]).getArrivalPosition());
                    g.writeNumberField("raceNumber", ((Competitor) concurrent[0]).getRaceNumber());
                    g.writeBooleanField("pole",
                            ((Competitor) concurrent[0]).getStartingPosition() == 1);
                    g.writeNumberField("points", (Integer) concurrent[1]);
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

}
