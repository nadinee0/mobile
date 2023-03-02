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
import com.mycompany.entities.Article;
import com.mycompany.entities.Category;
import com.mycompany.utils.Statics;
import java.io.IOException;
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
        String url= Statics.BASE_URL+"/category/category/addcategoryJSON/new?nom_cat="+category.getNom_cat()+"&image="+category.getImage();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data =="+str);                
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }  
    
    
     //---------------- Affichage----------------    
  public ArrayList<Category> parseCategories(String jsonText) {
        try {
            categories = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> categoriesListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) categoriesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Category catg = new Category();
                float id = Float.parseFloat(obj.get("id").toString());
                catg.setId((int) id);
                if (obj.get("nom_cat") == null) {
                    catg.setNom_cat("null");
                } else {
                    catg.setNom_cat(obj.get("nom_cat").toString());
                }
                 catg.setImage(obj.get("image").toString());
           
                categories.add(catg);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return categories;
    }  
  
    
    public ArrayList<Category> getAllCategories() {
        String url = Statics.BASE_URL + "/category/category/listeC";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                categories = parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
    //-------------Delete------------- 
    public boolean deleteCategory(int id ) {
        String url = Statics.BASE_URL +"/category/category/deletecategoryJSON?id="+id;
        
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
    public boolean modifierArticle(Category category) {
        String url = Statics.BASE_URL +"/category/category/updatecategoryJSON?id="+category.getId()+"&nom_cat="+category.getNom_cat()+"&image="+category.getImage();
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


