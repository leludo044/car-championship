package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.leludo.gtrchamp.Country;
import org.springframework.stereotype.Component;

/**
 * DAO for country access.
 */
public class CountryDao extends DefaultDao<Country, Integer> {

    /**
     * Constructor.
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    protected CountryDao(final EntityManager entityManager) {
        super(Country.class);
        super.entityManager(entityManager);
    }

    /**
     * Return all the countries.
     *
     * @return all the countries
     */
    public List<Country> all() {
        EntityManager session = this.getSession();
        String queryString = "from Country";
        javax.persistence.Query query = session.createQuery(queryString);
        return query.getResultList();
    }

    /**
     * Return a country.
     *
     * @param id
     *            The id of the country to find
     * @return the country
     */
    public Country get(final Integer id) {
        return find(id);
    }

    /**
     * Create a new driver.
     *
     * @param country
     *            The country to create
     */
    public void create(final Country country) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        session.persist(country);
        session.getTransaction().commit();
    }

    /**
     * Update an existing country.
     *
     * @param country
     *            The country to update
     */
    public void update(final Country country) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        session.merge(country);
        session.getTransaction().commit();

    }

    /**
     * Delete a Country.
     *
     * @param country
     *            The country to delete
     */
    public void delete(final Country country) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        session.remove(country);
        session.getTransaction().commit();

    }

    /**
     * Say if a country registered a track.
     *
     * @param countryId
     *            The id of the country to ask
     * @return true ou false
     */
    public boolean haveTack(final int countryId) {
        EntityManager session = this.getSession();
        Query query = session.createQuery("select count(*) from Track where country.id=:id");
        query.setParameter("id", countryId);
        Long trackCount = (Long) query.getSingleResult();

        return trackCount > 0;
    }
}
