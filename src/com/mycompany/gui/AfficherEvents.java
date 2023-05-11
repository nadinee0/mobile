/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.myapp.entities.Events;
import com.mycompany.services.ServiceEvents;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class AfficherEvents extends Form{
    
    public  AfficherEvents(Form previous) {
       
       /*setTitle("List Produits");
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceEvents.getInstance().getAllEvents().toString());
        add(sp);*/
        
        
 ArrayList<Events> event = ServiceEvents.getInstance().getAllEvents();
        for (Events p : event) {
            addElement(p);
     


        
                    Label lSupprimer = new Label(" ");
        lSupprimer.setUIID("NewsTopLine");
        Style supprmierStyle = new Style(lSupprimer.getUnselectedStyle());
        supprmierStyle.setFgColor(0xf21f1f);
        
        FontImage suprrimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprmierStyle);
        lSupprimer.setIcon(suprrimerImage);
        lSupprimer.setTextPosition(RIGHT);
        
        //click delete icon
        lSupprimer.addPointerPressedListener(l -> {
            
            Dialog dig = new Dialog("Suppression");
            
            if(dig.show("Suppression","Vous voulez supprimer ce event ?","Annuler","Oui")) {
                dig.dispose();
            }
            else {
                dig.dispose();
                 }
                //n3ayto l suuprimer men service Reclamation
                if(ServiceEvents.getInstance().deleteEvents(p.getId())) {
                        new AfficherEvents(previous);
                }

        });
                         
       
       //Event onclick btnModifer
         
        //Update icon 
        Label lModifier = new Label(" ");
        lModifier.setUIID("NewEvent");
        Style modifierStyle = new Style(lModifier.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        lModifier.setIcon(mFontImage);
        lModifier.setTextPosition(LEFT);
       lModifier.addPointerPressedListener(e-> new ModifierEvent(this,p).show());
      Button details =new Button("view");
      details.addActionListener(e-> new DetailsEvent(this,p.getId(),p).show());
      add( details);
        add(lModifier);
        add(lSupprimer);         
       
       //Event onclick btnModifer
        
        }

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

 public void addElement(Events event) {
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        // Create an image viewer to display the event image
     // Create an image view to display the product image
    ImageViewer imageViewer = new ImageViewer();
    Image im = null;
    try {
        im = Image.createImage("/" + event.getImage());
        im = im.scaled(1000,1000); // set a fixed size for the image
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    imageViewer.setImage(im);
    container.add(imageViewer);
        
        // Create a label to display the event title
        Label titleLabel = new Label(event.getTitle());
        titleLabel.getStyle().setMargin(5, 0, 0, 0);
        container.add(titleLabel);
        
        // Create a label to display the event description
        Label descriptionLabel = new Label(event.getDescription());
        descriptionLabel.getStyle().setMargin(5, 0, 0, 0);
        container.add(descriptionLabel);
        
        // Create a label to display the event location
        Label locationLabel = new Label(event.getLocation());
        locationLabel.getStyle().setMargin(5, 0, 0, 0);
        container.add(locationLabel);
        
        add(container);
    }
    






    
    
}
