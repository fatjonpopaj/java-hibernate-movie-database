package de.hsh.inform.dbs2.entities;

import jakarta.persistence.*;
import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;

@Entity()
@Table(name = "UE08_MOVIE_CHARACTER")
public class MovieCharacter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String character;

    private String alias;
    
    private int position;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;

    public MovieCharacter(String character, String alias, int position) {
    	
        this.character = character;
        this.alias = alias;
        this.position = position;
    }

    public MovieCharacter() {

    }

    public Long getId() {
    	
        return id;
    }
    
    public String getCharacter() {
    	
        return character;
    }
    
    public void setCharacter(String character) {
    	
    	this.character = character;
    }
    
    public String getAlias() {
    	
    	return alias;
    }
    
   public void setAlias(String alias) {
    	
	   this.alias = alias;
    }
    
    public int getPosition() {
    	
    	return position;
    }
    
    public void setPosition(int position) {
    	
    	this.position = position;
    }
    
    public Person getPerson() {
    	
		return person;
	}

	public void setPerson(Person person) {
		
		this.person = person;
	}
    
	public CharacterDTO toDTO() {
		
        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setCharacter(this.character);
        characterDTO.setAlias(this.alias);
        characterDTO.setPlayer(person.getName());

        return characterDTO;
    }
}
