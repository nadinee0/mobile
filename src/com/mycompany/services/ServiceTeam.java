/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;

import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Team;

import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author msi
 */
public class ServiceTeam {

    public ArrayList<Team> teams;
    
    public static ServiceTeam instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceTeam() {
         req = new ConnectionRequest();
    }

    public static ServiceTeam getInstance() {
        if (instance == null) {
            instance = new ServiceTeam();
        }
        return instance;
    }
    


    public ArrayList<Team> parseTeams(String jsonText){
                try {

                    System.out.println(jsonText);
            teams=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Team a = new Team();
                                
               a.setId((int)Float.parseFloat(obj.get("id").toString()));
                a.setNomTeam(obj.get("Nom_Team").toString());
                a.setDescriptionTeam(obj.get("Description_Team").toString());
                a.setWinsTeam((int)Float.parseFloat(obj.get("Wins_Team").toString()));
                a.setLossesTeam((int)Float.parseFloat(obj.get("Losses_Team").toString()));  
                a.setRateTeam(Float.parseFloat(obj.get("Rate_Team").toString()));
                a.setColor(obj.get("Color").toString());
                a.setLogoTeam(obj.get("Logo_Team").toString());
                
                teams.add(a);


            }
        } catch (IOException ex) {
            
        }
        return teams;
    }
    public ArrayList<Team> getAllTeams(){
        String url = Statics.BASE_URL+"/team/tout/s";
        req.setUrl(url);
        req.setPost(false); 
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                teams = parseTeams(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return teams;
    }
   

public boolean addTeam(Team u) {
   String url = Statics.BASE_URL+"/team/addTeamJSON/new?Nom_Team=" + u.getNomTeam() 
        + "&Description_Team=" + u.getDescriptionTeam() 
        + "&Wins_Team=" + u.getWinsTeam() 
        + "&Losses_Team=" + u.getLossesTeam() 
        + "&Rate_Team=" + u.getRateTeam()
        + "&Color=" + u.getColor() 
        + "&Logo_Team=" + (u.getLogoTeam() != null ? u.getLogoTeam() : "") 
        ;

    req.setUrl(url);
    req.setPost(false);
    
    System.out.println(url);
    
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
            req.removeResponseListener(this);
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
}


        public boolean editTeams(Team u) {
           
      String url = "http://127.0.0.1:8000/team/updateTeamJSON/"+u.getId()  + "?Nom_Team=" + u.getNomTeam() 
        + "&Description_Team=" + u.getDescriptionTeam() 
        + "&Wins_Team=" + u.getWinsTeam() 
        + "&Losses_Team=" + u.getLossesTeam() 
        + "&Rate_Team=" + u.getRateTeam()
        + "&Color=" + u.getColor() 
        + "&Logo_Team=" + (u.getLogoTeam() != null ? u.getLogoTeam() : "") 
       ;
               req.setUrl(url);
               req.setPost(false); 
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteTeams(int id) {
        String url = "http://127.0.0.1:8000/team/deleteTeamJSON/" + id;
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}

