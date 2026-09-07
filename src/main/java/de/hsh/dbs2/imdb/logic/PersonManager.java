package de.hsh.dbs2.imdb.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hsh.dbs2.imdb.other.ConnectionManager;
import de.hsh.inform.dbs2.entities.*;
import jakarta.persistence.EntityManager;


public class PersonManager {

	/**
	 * Liefert eine Liste aller Personen, deren Name den Suchstring enthaelt.
	 * @param text Suchstring
	 * @return Liste mit passenden Personennamen, die in der Datenbank eingetragen sind.
	 * @throws Exception
	 */
	public List<String> getPersonList(String text) throws Exception {
			
		List<String> names = new ArrayList<>();;
		
		boolean ok = false;
		
        EntityManager em = ConnectionManager.getConnection().createEntityManager();
        em.getTransaction().begin();
        
        try {
        
        List<Person> results = em.createQuery("SELECT p FROM Person p WHERE p.name LIKE :text", Person.class).
        		setParameter("text", "%" + text + "%").getResultList();
        
        names = results.stream().map(Person::getName).collect(Collectors.toList());;
        
        
        em.getTransaction().commit();
        
        ok = true;
        
        } finally {
        	
        	if (!ok) {
        		
                em.getTransaction().rollback();
        	}
        	
            em.close();	
        }
        		
		return names;
	}
}
