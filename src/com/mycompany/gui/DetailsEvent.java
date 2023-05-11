/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Events;
import com.mycompany.services.ServiceEvents;
import java.io.IOException;

/**
 *
 * @author USER
 */
public class DetailsEvent extends Form{
    
    
    
    public  DetailsEvent(Form previous,int id ,Events p) {
      
Events event = ServiceEvents.getInstance().DetailEvent(id,p);
Container eventContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        eventContainer.getStyle().setMargin(10, 10, 10, 10);

        // Create an image view to display the product image
        ImageViewer imageViewer = new ImageViewer();
        Image im = null;
        try {
            im = Image.createImage("/" + event.getImage());
            im = im.scaled(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayWidth());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        imageViewer.setImage(im);
        eventContainer.add(imageViewer);

        // Create a label to display the product name
        Label titleLabel = new Label(event.getTitle());
        titleLabel.getStyle().setMargin(5, 0, 0, 0);
        eventContainer.add(titleLabel);

     
        // Create a label to display the product description
        Label descriptionLabel = new Label("Description: " + event.getDescription());
        descriptionLabel.getStyle().setMargin(5, 0, 0, 0);
        eventContainer.add(descriptionLabel);

       
        
        
        
       // Create a label to display the product quanitites

        Label locationLabel = new Label("location: " + event.getLocation());
        locationLabel.getStyle().setMargin(5, 0, 0, 0);
        eventContainer.add(locationLabel);
        

        // Create a "Buy" button
        Button buyButton = new Button("Buy");
        buyButton.addActionListener(e -> {
            Dialog.show("Buy", "You have bought the ticket of this event: " + event.getTitle(), "OK", null);
        });
        eventContainer.add(buyButton);

        // Add the container for the current product to the main product container
        add(eventContainer);

      


             getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());


    
}

    
    
}
