/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.mycompany.myapp.entities.Events;
import com.mycompany.utils.Statics;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import com.codename1.io.NetworkEvent;
import com.codename1.io.JSONParser;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import com.codename1.io.CharArrayReader;
import java.util.Date;
import java.io.IOException;

/**
 *
 * @author USER
 */
public class ServiceEvents {
    
     public ArrayList<Events> events;

    public static ServiceEvents instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    private ServiceEvents() {
        req = new ConnectionRequest();
    }
  public static ServiceEvents getInstance() {
        if (instance == null) {
            instance = new ServiceEvents();
        }
        return instance;
    }
  
  public boolean ajoutEvent(Events event){
   
      
  String url = Statics.BASE_URL + "/events/"+"addEventJSON"+"/new?"+"title="+event.getTitle()+"&"+"description="+event.getDescription()+"&"+"image="+event.getImage()+"&"+"location="+event.getLocation();
        req.setUrl(url);
        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
  };
      
 public ArrayList<Events> parseevents(String jsonText) {
    try {
        events = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> tasksListJson= j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
        for (Map<String, Object> obj : list) {
            Events event = new Events();
if (obj.get("id") != null) {
    event.setId((int) Float.parseFloat(obj.get("id").toString()));
}

            // Add null checks before using the values obtained from obj.get()
            if (obj.get("title") != null) {
                event.setTitle(obj.get("title").toString());
            }
            if (obj.get("description") != null) {
                event.setDescription(obj.get("description").toString());
            }
            if (obj.get("image") != null) {
               event.setImage(obj.get("image").toString());
            }
            if (obj.get("location") != null) {
                event.setLocation(obj.get("location").toString());
            }
            events.add(event);
        }

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return events;
}


    public ArrayList<Events> getAllEvents() {
        String url = Statics.BASE_URL +"/events/events"+"/listeA";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseevents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
         
    public boolean deleteEvents(int id ) {
        String url = Statics.BASE_URL +"/events/deleteEventJSON"+"/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOK;
    }
    
    
    
    public Events DetailEvent( int id , Events event) {
        
        String url = Statics.BASE_URL+"/events/"+"Events"+"/"+id;
        req.setUrl(url);
        
        String str  = new String(req.getResponseData());
        req.addResponseListener(((evt) -> {
        
            JSONParser jsonp = new JSONParser();
            try {
                
                Map<String,Object>obj = jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
                
               if (obj.get("id") != null) {
    event.setId((int) Float.parseFloat(obj.get("id").toString()));
}

            // Add null checks before using the values obtained from obj.get()
            if (obj.get("title") != null) {
                event.setTitle(obj.get("title").toString());
            }
            if (obj.get("description") != null) {
                event.setDescription(obj.get("description").toString());
            }
            if (obj.get("image") != null) {
               event.setImage(obj.get("image").toString());
            }
            if (obj.get("location") != null) {
                event.setLocation(obj.get("location").toString());
            }
                
            }catch(IOException ex) {
                System.out.println("error related to sql :( "+ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
            
            
        }));
        
              NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

              return event;
        
        
    }
    //Update 
    public boolean modifierEvent(Events event,int id ) {
  String url = Statics.BASE_URL + "/events/"+"updateEventJSON"+"/"+id+"?"+"title="+event.getTitle()+"&"+"description="+event.getDescription()+"&"+"image="+event.getImage()+"&"+"location="+event.getLocation();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOK;
        
    }
     
    
    
}
