package de.hsh.dbs2.imdb.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hsh.dbs2.imdb.other.ConnectionManager;
import de.hsh.inform.dbs2.entities.Genre;
import jakarta.persistence.EntityManager;

public class GenreManager {

	/**
	 * Ermittelt eine vollstaendige Liste aller in der Datenbank abgelegten Genres
	 * Die Genres werden alphabetisch sortiert zurueckgeliefert.
	 * @return Alle Genre-Namen als String-Liste
	 * @throws Exception
	 */
	public List<String> getGenres() throws Exception {

		List<String> genres = new ArrayList<>();;
		
		boolean ok = false;
		
        EntityManager em = ConnectionManager.getConnection().createEntityManager();
        em.getTransaction().begin();
        
        try {
        
        List<Genre> results = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        
        genres = results.stream().map(Genre::getGenre).collect(Collectors.toList());
        
        genres.sort(String::compareTo);
        
        em.getTransaction().commit();
        
        ok = true;
        
        } finally {
        	
        	if (!ok) {
        		
                em.getTransaction().rollback();
        	}
        	
            em.close();	
        }
        		
		return genres;
	}

}
