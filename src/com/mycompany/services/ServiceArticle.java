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
import com.codename1.io.charArrayReader;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Article;
import com.mycompany.utils.Statics;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Nadine
 */
public class ServiceArticle {
    
    //singleton
    public static ServiceArticle instance = null;
    public ArrayList<Article> articles;
    public boolean resultOK;
    //initialition connection request
private ConnectionRequest req;
    

    public static ServiceArticle getInstance() {
        if(instance == null)
            instance = new ServiceArticle();
        return instance;
    }
    
    public ServiceArticle(){
        req = new ConnectionRequest();
    }
    
    //---------------- Ajout----------------
    public void ajoutArticle(Article article)
    {
        String url= Statics.BASE_URL+"/article/article/addarticleJSON/new?titre="+article.getTitre()+"&description="+article.getDescription()+"&image="+"&prix="+article.getPrix()+"&type="+article.getType()+"&stock="+article.getStock();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data =="+str);                
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }  
    
    
     //---------------- Affichage----------------
   /* public ArrayList<Article>affichageArticle(){
        ArrayList<Article> result = new ArrayList<>();         
        String url = Statics.BASE_URL+"/article/article/listeA";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
            JSONParser jsonp = new JSONParser();
            
            try{
            Map<String,Object>mapArticles = jsonp.parseJSON(new charArrayReader(new String(req.getResponseData()).toCharArray()));
            List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapArticles.get("root");
            
            
            for(Map<String,Object> obj : listofMaps){
                Article ar = new Article();
                
                float id = Float.parseFloat(obj.get("id").toString());
                float prix = Float.parseFloat(obj.get("prix").toString());
                float stock = Float.parseFloat(obj.get("stock").toString());
                String titre = obj.get("titre").toString();
                String image = obj.get("image").toString();
                String description = obj.get("description").toString();
                String type = obj.get("type").toString();
          
                ar.setId((int)id);
                ar.setTitre(titre);
                ar.setDescription(description);
                ar.setImage(image);
                ar.setStock((int)stock);
                ar.setType(type);
                ar.setPrix((float)prix);
                
                result.add(ar);
            }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            
            }}            
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return result;
    } 
    */
    
  public ArrayList<Article> parseArticles(String jsonText) {
        try {
            articles = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> articlesListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) articlesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Article ar = new Article();
                float id = Float.parseFloat(obj.get("id").toString());
                ar.setId((int) id);
                ar.setPrix((Float.parseFloat(obj.get("prix").toString())));
                ar.setStock(((int) Float.parseFloat(obj.get("stock").toString())));
                if (obj.get("titre") == null) {
                    ar.setTitre("null");
                } else {
                    ar.setTitre(obj.get("titre").toString());
                }
                 ar.setType(obj.get("type").toString());
                 ar.setImage(obj.get("image").toString());
                 ar.setDescription(obj.get("description").toString());
                 ar.setType(obj.get("type").toString());
         
                articles.add(ar);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return articles;
    }  
  
    
    public ArrayList<Article> getAllArticles() {
        String url = Statics.BASE_URL + "/article/article/listeA";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                articles = parseArticles(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return articles;
    }
    
    //-------------Delete------------- 
    public boolean deleteArticle(int id ) {
        String url = Statics.BASE_URL +"/article/article/deletearticleJSON?id="+id;
        
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
    public boolean modifierArticle(Article article) {
        String url = Statics.BASE_URL +"/article/article/updatearticleJSON?id="+article.getId()+"&titre="+article.getTitre()+"&description="+article.getDescription()+"&image="+article.getImage()+"&prix="+article.getPrix()+"&type="+article.getType()+"&stock="+article.getStock();
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
