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
        req.addResponseListener( e -> {
            String str = new String(req.getResponseData());
            System.out.println("data =="+str);                
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }  
    
    
     //---------------- Affichage----------------
    public ArrayList<Article>affichageArticle(){
        ArrayList<Article> result = new ArrayList<>();         
        String url = Statics.BASE_URL+"/article/article/listeA";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
            JSONParser json ;
                json = new JSONParser();
            
            try{
            Map<String,Object>mapArticles = json.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));          
            List<Map<String,Object>> listofMaps = (List<Map<String,Object>>) mapArticles.get("root");
   
            
            for(Map<String,Object> obj : listofMaps){
                Article ar = new Article();
                
                float id = Float.parseFloat(obj.get("id").toString());
                  String titre = obj.get("titre").toString();
                    String description = obj.get("description").toString();
                float prix = Float.parseFloat(obj.get("prix").toString());
                
              
               // String image = obj.get("image").toString();
              float stock = Float.parseFloat(obj.get("stock").toString());
                String type = obj.get("type").toString();
          
                ar.setId((int)id);
                ar.setTitre(titre);
                ar.setDescription(description);
                 ar.setPrix((float)prix);
                    ar.setType(type);
              //  ar.setImage(image);
                ar.setStock((int)stock);
             
               
                
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
    
 
    //-------------Delete------------- 
    public boolean deleteArticle(int id ) {
        String url = Statics.BASE_URL +"/article/article/deletearticleJSON/"+id;
        
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
        String url = Statics.BASE_URL +"/article/article/updatearticleJSON/"+article.getId()+"?titre="+article.getTitre()+"&description="+article.getDescription()+"&prix="+article.getPrix()+"&type="+article.getType()+"&stock="+article.getStock();
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
