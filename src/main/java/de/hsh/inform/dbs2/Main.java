package de.hsh.inform.dbs2;

import de.hsh.inform.dbs2.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import de.hsh.inform.dbs2.entities.*;
import java.util.List;

public class Main {
	
    private static EntityManagerFactory emf;

    public static void main(String [] args) {
        System.out.println("hello world");
        emf = Persistence.createEntityManagerFactory("movie");
        createGenre();
        createPerson();
        printMovies();
        printGenre();
        printPerson();
        printMovieCharacter();
    }

    public static void createMovie() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Movie movie = new Movie("Star Wars", "C", 1977);
        em.persist(movie);
        em.getTransaction().commit();
        em.close();
    }

    public static void printMovies() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Movie> results = em.createQuery("SELECT m FROM Movie m WHERE m.year = :year", Movie.class).setParameter("year", 1977).getResultList();
        for (Movie movie : results) {
            System.out.println("" + movie.getId() + ":" + movie.getTitle());
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void createGenre() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Genre genre = new Genre("Action");
        em.persist(genre);
        em.getTransaction().commit();
        em.close();
    }

    public static void printGenre() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Genre> results = em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        for (Genre genre : results) {
            System.out.println("" + genre.getId() + ":" + genre.getGenre());
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void createPerson() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Person person = new Person("Vader", 'M');
        em.persist(person);
        em.getTransaction().commit();
        em.close();
    }

    public static void printPerson() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Person> results = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        

        
        for (Person person : results) {


            System.out.println("" + person.getId() + ":" + person.getName()+ " " + person.getSex());
        }
        em.getTransaction().commit();
        em.close();
    }

    public static void createMovieCharacter() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        MovieCharacter movieCharacter = new MovieCharacter("Darth Vader", "Vadi", 5);
        em.persist(movieCharacter);
        em.getTransaction().commit();
        em.close();
    }

    public static void printMovieCharacter() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<MovieCharacter> results = em.createQuery("SELECT mc FROM MovieCharacter mc", MovieCharacter.class).getResultList();
        for (MovieCharacter movieCharacter : results) {
            System.out.println("" + movieCharacter.getId() + ":" + movieCharacter.getCharacter() + " " + movieCharacter.getAlias() + " " + movieCharacter.getPosition());
        }
        em.getTransaction().commit();
        em.close();
    }



}
