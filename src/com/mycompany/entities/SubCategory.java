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
public class SubCategory {
    private int id;
    private String nom_sc;

    public SubCategory() {
    }

    public SubCategory(String nom_sc) {
        this.nom_sc = nom_sc;
    }

    public SubCategory(int id, String nom_sc) {
        this.id = id;
        this.nom_sc = nom_sc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_sc() {
        return nom_sc;
    }

    public void setNom_sc(String nom_sc) {
        this.nom_sc = nom_sc;
    }

    @Override
    public String toString() {
        return "SubCategory{" + "id=" + id + ", nom_sc=" + nom_sc + '}';
    }
    
    
}
