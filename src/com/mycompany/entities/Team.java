/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;


/**
 *
 * @author msi
 */
public class Team {
    private int id;
    private int winsTeam;
    private int lossesTeam;
    private float rateTeam;
    private String nomTeam;
    private String descriptionTeam;
    private String logoTeam;
    private String color;
    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWinsTeam() {
        return winsTeam;
    }

    public void setWinsTeam(int winsTeam) {
        this.winsTeam = winsTeam;
    }

    public int getLossesTeam() {
        return lossesTeam;
    }

    public void setLossesTeam(int lossesTeam) {
        this.lossesTeam = lossesTeam;
    }

    public float getRateTeam() {
        return rateTeam;
    }

    public void setRateTeam(float rateTeam) {
        this.rateTeam = rateTeam;
    }

    public String getNomTeam() {
        return nomTeam;
    }

    public void setNomTeam(String nomTeam) {
        this.nomTeam = nomTeam;
    }

    public String getDescriptionTeam() {
        return descriptionTeam;
    }

    public void setDescriptionTeam(String descriptionTeam) {
        this.descriptionTeam = descriptionTeam;
    }

    public String getLogoTeam() {
        return logoTeam;
    }

    public void setLogoTeam(String logoTeam) {
        this.logoTeam = logoTeam;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

   

    public Team() {
    }

    public Team(int id, int winsTeam, int lossesTeam, float rateTeam, String nomTeam, String descriptionTeam, String logoTeam, String color) {
        this.id = id;
        this.winsTeam = winsTeam;
        this.lossesTeam = lossesTeam;
        this.rateTeam = rateTeam;
        this.nomTeam = nomTeam;
        this.descriptionTeam = descriptionTeam;
        this.logoTeam = logoTeam;
        this.color = color;
        
    }

    public Team(int winsTeam, int lossesTeam, float rateTeam, String nomTeam, String descriptionTeam, String logoTeam, String color) {
        this.winsTeam = winsTeam;
        this.lossesTeam = lossesTeam;
        this.rateTeam = rateTeam;
        this.nomTeam = nomTeam;
        this.descriptionTeam = descriptionTeam;
        this.logoTeam = logoTeam;
        this.color = color;
        
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", winsTeam=" + winsTeam + ", lossesTeam=" + lossesTeam + ", rateTeam=" + rateTeam + ", nomTeam=" + nomTeam + ", descriptionTeam=" + descriptionTeam + ", logoTeam=" + logoTeam + ", color=" + color + '}';
    }

    
    
    
    
    
}
