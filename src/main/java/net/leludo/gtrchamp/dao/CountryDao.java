package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Country;

@Singleton
public class CountryDao extends DefaultDao<Country, Integer> {

    public CountryDao() {
        super(Country.class);
    }

    /**
     * @return all the countries
     */
    public List<Country> all() {
        String queryString = "from Country";
        javax.persistence.Query query = this.em.createQuery(queryString);
        this.em.clear();
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
     * @param pilote
     *            The driver to crate
     */
    public void create(final Country country) {
        this.em.getTransaction().begin();
        this.em.persist(country);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Update an existing country.
     *
     * @param country
     *            The country to update
     */
    public void update(final Country country) {
        this.em.getTransaction().begin();
        this.em.merge(country);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Delete a Country.
     *
     * @param country
     *            The country to delete
     */
    public void delete(final Country country) {
        this.em.getTransaction().begin();
        this.em.remove(country);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Say if a country registered a track.
     *
     * @param countryId
     *            The id of the country to ask
     * @return true ou false
     */
    public boolean haveTack(final int countryId) {
        this.em.getTransaction().begin();
        Query query = this.em.createQuery("select count(*) from Track where country.id=:id");
        query.setParameter("id", countryId);
        Long trackCount = (Long) query.getSingleResult();
        this.em.getTransaction().commit();

        return trackCount > 0;
    }
}
