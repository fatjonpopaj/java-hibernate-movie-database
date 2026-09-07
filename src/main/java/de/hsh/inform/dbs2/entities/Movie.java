package de.hsh.inform.dbs2.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.hsh.dbs2.imdb.logic.dto.*;


@Entity
@Table(name = "UE08_MOVIE")
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String type;

    private int year;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Genre> genres = new HashSet<>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MovieCharacter> charactere = new ArrayList<>();

	public Movie(String title, String type, int year) {
        this.title = title;
        this.type = type;
        this.year = year;
    }

    public Movie() {

    }
    
    public Long getId() {
    	
        return id;
    }
    

    public String getTitle() {
    	
        return title;
    }

    public void setTitle(String title ){
    	
        this.title = title;
    }
    
    public String getType(){
    	
        return type;
    }
    
    public void setType(String type){
    	
        this.type = type;
    }
    
    public int getYear(){
    	
        return year;
    }
    
    public void setYear(int year){
    	
        this.year = year;
    }
    
	public Set<Genre> getGenres() {
		
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		
		this.genres = (genres);
	}
	
	public List<MovieCharacter> getCharactere() {
		
		return charactere;
	}
	
	public void setCharactere(List<MovieCharacter> charactere) {
		
		this.charactere = (charactere);
	}
    	
	public MovieDTO toDTO() {
		
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(this.id);
        movieDTO.setTitle(this.title);
        movieDTO.setYear(this.year);
        movieDTO.setType(String.valueOf(this.type));
       
        movieDTO.setGenres(this.genres.stream().map(Genre::getGenre).collect(Collectors.toSet()));

        movieDTO.setCharacters(this.charactere.stream().map(MovieCharacter::toDTO).collect(Collectors.toList()));        

        return movieDTO;
    }
}
