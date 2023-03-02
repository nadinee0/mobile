/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Category;
import com.mycompany.entities.SubCategory;
import com.mycompany.services.ServiceCategory;
import com.mycompany.services.ServiceSubcategory;

/**
 *
 * @author Nadine
 */
public class AjoutSubcategoryForm extends Form {
   
    
    Form AjoutCategory;
    
    
    public AjoutSubcategoryForm(Resources res){
        
 
    Toolbar  tb = new Toolbar(true);
    AjoutCategory = this;
    setToolbar(tb);
    
    getTitleArea().setUIID("container");
    setTitle("Ajouter Sous-Categorie");
    getContentPane().setScrollVisible(false);
     
   TextField nom = new TextField("","Saisir nom");
   nom.setUIID("TextFieldBlack");
   addStringValue("Nom du Sou-Categorie", nom);
   
  /* TextField image = new TextField("","Saisir description");
   image.setUIID("TextFieldBlack");
   addStringValue("Image", image);
   */
   
   Button btnAjouter = new Button("Ajouter");
   addStringValue("",btnAjouter);
   
   
   btnAjouter.addActionListener((e) -> {
       try{
           if(nom.getText() =="" /*|| image.getText() ==""*/){
               Dialog.show("Veuillez verifier les données","","Annuler","OK");
           }
           else{
               InfiniteProgress ip = new InfiniteProgress();
               
               final Dialog iDialog = ip.showInfiniteBlocking();
               
               SubCategory subcatg = new SubCategory( 
                       
                       String.valueOf(nom.getText()).toString()
                       
               );
                       
               System.out.println("data SubCategory == "+subcatg);
               
               ServiceSubcategory.getInstance().ajoutSubCategory(subcatg);
               
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
