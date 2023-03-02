/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;


//import com.mycompany.Entites.Article;
import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.services.ServiceArticle;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
//import java.util.Date;


/**
 *
 * @author Nadine
 */
public class AjoutArticleForm extends Form {
   
    
    Form AjoutArticle;
    
    
    public AjoutArticleForm(Resources res){
        
 
    Toolbar  tb = new Toolbar(true);
    AjoutArticle = this;
    setToolbar(tb);
    
    getTitleArea().setUIID("container");
    setTitle("Ajout Article");
    getContentPane().setScrollVisible(false);
     
   TextField titre = new TextField("","Saisir Titre");
   titre.setUIID("TextFieldBlack");
   addStringValue("Titre", titre);
   
   TextField Description = new TextField("","Saisir description");
   Description.setUIID("TextFieldBlack");
   addStringValue("Description", Description);
   
   TextField image = new TextField("","Saisir image");
   image.setUIID("TextFieldBlack");
   addStringValue("image", image);
   
   TextField type = new TextField("","Saisir type");
   type.setUIID("TextFieldBlack");
   addStringValue("type", type);
   
   TextField stock = new TextField("","Saisir stock");
   stock.setUIID("TextFieldBlack");
   addStringValue("stock", stock);
   
   TextField prix = new TextField("","Saisir prix");
   prix.setUIID("TextFieldBlack");
   addStringValue("prix", prix);
   
   
   Button btnAjouter = new Button("Ajouter");
   addStringValue("",btnAjouter);
   
   
   btnAjouter.addActionListener((e) -> {
       try{
           if(titre.getText() =="" || Description.getText() ==""){
               Dialog.show("Veuillez verifier les données","","Annuler","OK");
           }
           else{
               InfiniteProgress ip = new InfiniteProgress();
               
               final Dialog iDialog = ip.showInfiniteBlocking();
               
               Article ar = new Article( 
                       
                       String.valueOf(titre.getText()).toString(),
                       String.valueOf(image.getText()).toString(),
                       String.valueOf(Description.getText()).toString(),
                       String.valueOf(type.getText()).toString(),
                       Float.parseFloat(prix.getText()),
                       Integer.parseInt(stock.getText()) );
   
               System.out.println("data Article == "+ar);
               
               ServiceArticle.getInstance().ajoutArticle(ar);
               
               iDialog.dispose();
               refreshTheme();           
           }
           
       }catch(Exception ex){
           ex.printStackTrace();
       }
   });

 
    }   
             
private Component createLineSeparator(int color) {
     
       Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
}

    private void addStringValue(String titre, Component v) {
      add(BorderLayout.west(new Label(titre,"PaddedLabel"))
      .add(BorderLayout.CENTER,v));
      add(createLineSeparator(0xeeeeee));  
    }
    
    }
    
        
         
               
