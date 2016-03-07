package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;

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
}
