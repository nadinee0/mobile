/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Article;
import com.mycompany.services.ServiceArticle;

/**
 *
 * @author Nadine
 */
public class ModifierArticleForm extends Form {

    Form current;
    
public ModifierArticleForm(Resources res , Article ar ){
 super("Modifier Article",BoxLayout.y());   
 
    Toolbar  tb = new Toolbar(true);
    current = this;
    setToolbar(tb);
    
    getTitleArea().setUIID("container");
    setTitle("");
    getContentPane().setScrollVisible(false);
    
   //super.addSideMenu(res);
    
   TextField titre = new TextField(ar.getTitre(), "Titre", 20, TextField.ANY);
  // TextField image = new TextField(ar.getImage(), "Image", 20, TextField.ANY);
   TextField description = new TextField(ar.getDescription(), "Description", 20, TextField.ANY);
      TextField prix = new TextField(Float.toString(ar.getPrix()), "Prix", 20, TextField.ANY);
      TextField type = new TextField(ar.getType(), "Type", 20, TextField.ANY);
   TextField stock = new TextField(Integer.toString(ar.getStock()), "Stock", 20, TextField.ANY);


   ComboBox typeCombo = new  ComboBox();
   typeCombo.addItem("For Rent");
   typeCombo.addItem("For Sale");
   if(ar.getType()== "ForRent")
       typeCombo.setSelectedItem("For Rent");
   else
              typeCombo.setSelectedItem("For Sale");
   
 titre.setUIID("NewsTopLine");
 // image.setUIID("NewsTopLine");
 description.setUIID("NewsTopLine");
   prix.setUIID("NewsTopLine");
  type.setUIID("NewsTopLine");
  stock.setUIID("NewsTopLine");

 titre.setSingleLineTextArea(true);
  //image.setSingleLineTextArea(true);
 description.setSingleLineTextArea(true);
 type.setSingleLineTextArea(true);
 prix.setSingleLineTextArea(true);
  stock.setSingleLineTextArea(true);


Button btnModifier = new Button("Modifier");
btnModifier.setUIID("Button");

//Event onclick btnModifier
 btnModifier.addPointerPressedListener(l -> {
    ar.setTitre(titre.getText());
  //  ar.setImage(image.getText());
    ar.setDescription(description.getText());
    float price = Float.parseFloat(prix.getText());  
    ar.setPrix(price);
    int stockk =  Integer.parseInt(stock.getText());
    ar.setStock(stockk);
        

    
    if(typeCombo.getSelectedItem()=="For Rent")
        ar.setType("For Rent");
    else 
      ar.setType("For Sale");

 
 if(ServiceArticle.getInstance().modifierArticle(ar)){
     new ListArticleForm(res).show();
 }
  });
    Button btnAnnuler = new Button("Annuler");
    btnAnnuler.addActionListener(e -> {
        new ListArticleForm(res).show();
    
    });
    
    Label l1 = new Label("");
    Label l2 = new Label("");
    Label l3 = new Label("");
    Label l4 = new Label("");
    Label l5 = new Label("");

Container content = BoxLayout.encloseY(
l1,l2,
        
        new FloatingHint(titre),
        createLineSeparator(),
       //   new FloatingHint(image),
      //  createLineSeparator(),
        new FloatingHint(description),
        createLineSeparator(),
         new FloatingHint(prix),
        createLineSeparator(),
         typeCombo,
        createLineSeparator(),
        new FloatingHint(stock),
        createLineSeparator(),
       // l4,l5,
        btnModifier,
        btnAnnuler
       
);

add(content);
show();


}
  public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
}