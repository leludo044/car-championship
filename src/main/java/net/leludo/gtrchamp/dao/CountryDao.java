package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Country;

@Singleton
public class CountryDao extends DefaultDao<Country, Integer> {

    protected CountryDao() {
        super(Country.class);
    }

    /**
     * @return all the countries
     */
    public List<Country> all() {
        String queryString = "from Country";
        javax.persistence.Query query = this.getSession().createQuery(queryString);
        this.getSession().clear();
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
        this.getSession().getTransaction().begin();
        this.getSession().persist(country);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Update an existing country.
     *
     * @param country
     *            The country to update
     */
    public void update(final Country country) {
        this.getSession().getTransaction().begin();
        this.getSession().merge(country);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Delete a Country.
     *
     * @param country
     *            The country to delete
     */
    public void delete(final Country country) {
        this.getSession().getTransaction().begin();
        this.getSession().remove(country);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Say if a country registered a track.
     *
     * @param countryId
     *            The id of the country to ask
     * @return true ou false
     */
    public boolean haveTack(final int countryId) {
        this.getSession().getTransaction().begin();
        Query query = this.getSession().createQuery("select count(*) from Track where country.id=:id");
        query.setParameter("id", countryId);
        Long trackCount = (Long) query.getSingleResult();
        this.getSession().getTransaction().commit();

        return trackCount > 0;
    }
}
