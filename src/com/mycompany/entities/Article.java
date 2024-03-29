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
public class Article {
    private int id,stock;
    private String titre,description,type;
    private float prix;

    public Article() {
    }

  

    public Article(String titre,String description,String type, float prix, int stock) {
        this.stock = stock;
        this.titre = titre;
        this.description = description;
        this.type = type;
        this.prix = prix;
    }
    
    public Article(int id, String titre,String description,String type, float prix, int stock) {
        this.id = id;
        this.stock = stock;
        this.titre = titre;
        this.description = description;

        this.type = type;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", stock=" + stock + ", titre=" + titre + ", description=" + description + ", type=" + type + ", prix=" + prix + '}';
    }
    
    
}
