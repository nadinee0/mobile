/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import java.io.IOException;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
//import com.codename1.io.charArrayReader;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Article;
import com.mycompany.entities.Category;
import com.mycompany.utils.Statics;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Nadine
 */
public class ServiceCategory {
    //singleton
    public static ServiceCategory instance = null;
    public ArrayList<Category> categories;
    public boolean resultOK;
    //initialition connection request
private ConnectionRequest req;
    

    public static ServiceCategory getInstance() {
        if(instance == null)
            instance = new ServiceCategory();
        
        return instance;
    }
    
    public ServiceCategory(){
        req = new ConnectionRequest();
    }
    
    //---------------- Ajout----------------
    public void ajoutCategory(Category category)
    {
        String url= Statics.BASE_URL+"/category/category/addcategoryJSON/new?nom="+category.getNom()/*+"&img="+category.getImg()*/;
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data =="+str);                
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }  
    
    
     //---------------- Affichage----------------     
      public ArrayList<Category>affichageCategory(){
        ArrayList<Category> result = new ArrayList<>();         
        String url = Statics.BASE_URL+"/category/category/listeC";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
            JSONParser json ;
                json = new JSONParser();
            
            try{
            Map<String,Object>mapCategory = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));          
            List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapCategory.get("root");
   
            
            for(Map<String,Object> obj : listofMaps){
                Category catg = new Category();
                
                float id = Float.parseFloat(obj.get("id").toString());
               
                String nom = obj.get("nom").toString();
             //  String img = obj.get("img").toString();
               
          
                catg.setId((int)id);
                catg.setNom(nom);
               
            //  catg.setImg(img);
               
                
                result.add(catg);
            }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            
            }}            
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return result;
    } 
    
    
    //-------------Delete------------- 
    public boolean deleteCategory(int id ) {
        String url = Statics.BASE_URL +"/category/category/deletecategoryJSON/"+id;
        
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
   
    //------------Update------------- 
    public boolean modifierCategory(Category category) {
        String url = Statics.BASE_URL +"/category/category/updatecategoryJSON/"+category.getId()+"?nom="+category.getNom()/*+"&img="+category.getImg()*/;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200 ;  // Code response Http 200 ok
               req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
        
    } 
    
}


