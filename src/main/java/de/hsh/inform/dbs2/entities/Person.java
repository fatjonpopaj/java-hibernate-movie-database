package de.hsh.inform.dbs2.entities;

import jakarta.persistence.*;

@Entity()
@Table(name = "UE08_PERSON")
public class Person {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private char sex;

    public Person(String name, char sex) {
        this.name = name;
        this.sex = sex;
    }

    public Person() {

    }
    
    public Long getId() {
    	
        return id;
    }

    public String getName() {
    	
        return name;
    }
    
    public void setName(String name) {
    	
    	this.name = name;
    }
    
    public char getSex() {
    	
    	return sex;
    }
    
    public void setSex(char sex) {
    	
    	this.sex = sex;
    }
}
