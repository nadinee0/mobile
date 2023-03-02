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
import com.mycompany.entities.Category;
import com.mycompany.entities.SubCategory;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nadine
 */
public class ServiceSubcategory {
    //singleton
    public static ServiceSubcategory instance = null;
    public ArrayList<SubCategory> subcategories;
    public boolean resultOK;
    //initialition connection request
private ConnectionRequest req;
    

    public static ServiceSubcategory getInstance() {
        if(instance == null)
            instance = new ServiceSubcategory();
        
        return instance;
    }
    
    public ServiceSubcategory(){
        req = new ConnectionRequest();
    }
    
    //---------------- Ajout----------------
    public void ajoutSubCategory(SubCategory subcategory)
    {
        String url= Statics.BASE_URL+"/sub/category/subcategory/addsubcategoryJSON/new?nom_sc="+subcategory.getNom_sc();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data =="+str);                
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }  
    
    
     //---------------- Affichage----------------
  public ArrayList<SubCategory> parseCategories(String jsonText) {
        try {
            subcategories = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> subcategoriesListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) subcategoriesListJson.get("root");
            for (Map<String, Object> obj : list) {
                SubCategory subcatg = new SubCategory();
                float id = Float.parseFloat(obj.get("id").toString());
                subcatg.setId((int) id);
                if (obj.get("nom_sc") == null) {
                    subcatg.setNom_sc("null");
                } else {
                    subcatg.setNom_sc(obj.get("nom_sc").toString());
                }
              
                subcategories.add(subcatg);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return subcategories;
    }  
  
    
    public ArrayList<SubCategory> getAllSubCategories() {
        String url = Statics.BASE_URL + "/sub/category/subcategory/listeS";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                subcategories = parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return subcategories;
    }
    
    //-------------Delete------------- 
    public boolean deleteSubCategory(int id ) {
        String url = Statics.BASE_URL +"/sub/category/subcategory/deletesubcategoryJSON?id="+id;
        
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
    public boolean modifierArticle(SubCategory subcategory) {
        String url = Statics.BASE_URL +"/sub/category/subcategory/updatesubcategoryJSON?id="+subcategory.getId()+"&nom_sc="+subcategory.getNom_sc();
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
