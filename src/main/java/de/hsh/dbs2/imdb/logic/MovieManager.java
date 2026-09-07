package de.hsh.dbs2.imdb.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.hsh.dbs2.imdb.logic.dto.*;
import de.hsh.dbs2.imdb.other.ConnectionManager;
import jakarta.persistence.EntityManager;
import de.hsh.inform.dbs2.entities.*;


public class MovieManager {

	/**
	 * Ermittelt alle Filme, deren Filmtitel den Suchstring enthaelt.
	 * Wenn der String leer ist, sollen alle Filme zurueckgegeben werden.
	 * Der Suchstring soll ohne Ruecksicht auf Gross/Kleinschreibung verarbeitet werden.
	 * @param search Suchstring. 
	 * @return Liste aller passenden Filme als MovieDTO
	 * @throws Exception
	 */
	public List<MovieDTO> getMovieList(String search) throws Exception {
	
		EntityManager em = ConnectionManager.getConnection().createEntityManager();
		em.getTransaction().begin();
		
		boolean ok = false;
		
		List<Movie> results = new ArrayList<>();
			
		try {
			
			results = em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title", Movie.class)
				.setParameter("title", "%" + search + "%").getResultList();
		
			em.getTransaction().commit();
			
			ok = true;
        
        } finally {
        	
        	if (!ok) {
        		
                em.getTransaction().rollback();
        	}
        	
            em.close();	
        }	
		
		return results.stream().map(Movie::toDTO).collect(Collectors.toList());
    }

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movie Film-Objekt mit Genres und Charakteren.
	 * @throws Exception
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
		
		EntityManager em = ConnectionManager.getConnection().createEntityManager();
		em.getTransaction().begin();
		
		boolean ok = false;
			
		try {
		
			List<Movie> movies = em.createQuery("SELECT m FROM Movie m WHERE m.id = :id" , Movie.class)
					.setParameter("id", movieDTO.getId()).getResultList(); 
		
			Movie movie;
			
			if (movies.size() == 0) {
			
				movie = new Movie();	
			
				em.persist(movie);
			
			
			} else {
				
				movie = movies.get(0);
			}
			
			movie.setTitle(movieDTO.getTitle());
			movie.setType(movieDTO.getType());
			movie.setYear(movieDTO.getYear());			
		
			Set<Genre> genres = new HashSet<>();
			
			for (String genre : movieDTO.getGenres()) {
			
				genres.add(em.createQuery("SELECT g FROM Genre g WHERE g.genre = :genre", Genre.class)
						.setParameter("genre", genre).getSingleResult());
			}
		
			movie.setGenres(genres);

		
			List<MovieCharacter> movieChars = new ArrayList<>();
		
			for (int i = 0; i < movieDTO.getCharacters().size(); i++) {

				CharacterDTO charDTO =  movieDTO.getCharacters().get(i);
				MovieCharacter movieCharacter = new MovieCharacter();
			
				movieCharacter.setCharacter(charDTO.getCharacter());
				movieCharacter.setAlias(charDTO.getAlias());
				movieCharacter.setPosition(i);
			
				Person person = em.createQuery("SELECT p FROM Person p WHERE p.name = :name", Person.class)
					.setParameter("name", charDTO.getPlayer()).getSingleResult();
			
				movieCharacter.setPerson(person);
				
				movieChars.add(movieCharacter);
			}
		
			movie.setCharactere(movieChars);


			em.getTransaction().commit();
			
			ok = true;
			
		} finally {
    	
    		if (!ok) {
    		
    			em.getTransaction().rollback();
    		}
    	
        	em.close();	
    	}	
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movie
	 * @throws Exception
	 */
	public void deleteMovie(long movieId) throws Exception {
		
		EntityManager em = ConnectionManager.getConnection().createEntityManager();
		em.getTransaction().begin();
		
		boolean ok = false;
		
		try {
		
			Movie movie = em.find(Movie.class, movieId);
			em.remove(movie);
		
			em.getTransaction().commit();
			ok = true;
			
		} finally {
    	
			if (!ok) {
		
				em.getTransaction().rollback();
			}
	
    		em.close();		
		}	
	}
	/**
	 * Liefert die Daten eines einzelnen Movies zurück
	 * @param movieId
	 * @return
	 * @throws Exception
	 */
	public MovieDTO getMovie(long movieId) throws Exception {
		
		EntityManager em = ConnectionManager.getConnection().createEntityManager();
		em.getTransaction().begin();
		
		boolean ok = false;
		
		Movie movie = null;
		
		try {
		
			movie = em.find(Movie.class, movieId);
					
			em.getTransaction().commit();
		
			ok = true;
		
		} finally {
	    	
			if (!ok) {
		
				em.getTransaction().rollback();
			}
	
			em.close();	
			
		}	
		
		if (movie == null) {
			
			return null;
		}
				
		return movie.toDTO();
	}
}
