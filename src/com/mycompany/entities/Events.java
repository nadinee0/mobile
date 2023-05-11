/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author USER
 */
public class Events {
     private int id;
    private String title,description,image,location;

    public Events() {
    }

    
    
    
    
    public Events(int id, String title, String description, String image, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.location = location;
    }

    public Events(String title, String description, String image, String location) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
    
}
