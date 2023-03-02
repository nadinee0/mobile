/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author Nadine
 */
public class Category {
  private int id;
  private String nom_cat, image;
  
    public Category() {
    }
    
    public Category(int id, String nom_cat, String image) {
        this.id = id;
        this.nom_cat = nom_cat;
        this.image = image;
    }

    public Category(String nom_cat, String image) {
        this.nom_cat = nom_cat;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", nom_cat=" + nom_cat + ", image=" + image + '}';
    }

    



  
}
