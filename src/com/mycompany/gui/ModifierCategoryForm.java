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
import com.mycompany.entities.Category;
import com.mycompany.services.ServiceCategory;

/**
 *
 * @author Nadine
 */
public class ModifierCategoryForm extends Form{
   Form current;
    
public ModifierCategoryForm(Resources res , Category catg ){
 super("Modifier Categorie",BoxLayout.y());   
 
    Toolbar  tb = new Toolbar(true);
    current = this;
    setToolbar(tb);
    
    getTitleArea().setUIID("container");
    setTitle("");
    getContentPane().setScrollVisible(false);
    
   //super.addSideMenu(res);
    
   TextField nom = new TextField(catg.getNom(), "Nom", 20, TextField.ANY);
  TextField $ = new TextField(catg.getNom(), "", 20, TextField.ANY);
   
 nom.setUIID("NewsTopLine");
 // image.setUIID("NewsTopLine");
 nom.setSingleLineTextArea(true);
  //image.setSingleLineTextArea(true);


Button btnModifier = new Button("Modifier");
btnModifier.setUIID("Button");

//Event onclick btnModifier
 btnModifier.addPointerPressedListener(l -> {
    catg.setNom(nom.getText());
   // catg.setImg(image.getText());
   

 if(ServiceCategory.getInstance().modifierCategory(catg)){
     new ListCategoryForm(res).show();
 }
  });
    Button btnAnnuler = new Button("Annuler");
    btnAnnuler.addActionListener(e -> {
        new ListCategoryForm(res).show();
    
    });
    
    Label l1 = new Label(" ");
    Label l2 = new Label(" ");
    Label l3 = new Label("");
  

Container content = BoxLayout.encloseY(
 l1,
        
        new FloatingHint(nom),
     createLineSeparator(),
         new FloatingHint($),
      //  createLineSeparator(),
  l2,l3,
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
