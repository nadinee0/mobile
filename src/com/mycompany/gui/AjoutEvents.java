/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Events;
import com.mycompany.services.ServiceEvents;

/**
 *
 * @author USER
 */
public class AjoutEvents extends Form {
    
    public AjoutEvents(Form previous)
{

        setTitle("Add a new event");
        setLayout(BoxLayout.y());
        
        TextField tfTitle = new TextField("","Event Title");
        TextField tfDesc = new TextField("","Event Description");
        TextField tfImage = new TextField("","Event Image");
        TextField tfLocation = new TextField("","Event Location");

        
        Button btnValider = new Button("Add event");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               try {
                        
                        Events p = new Events(tfTitle.getText(), tfDesc.getText(),tfImage.getText(), tfLocation.getText());
                        if( ServiceEvents.getInstance().ajoutEvent(p))
                        {
                           Dialog.show("Success","Event added successfully",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Quantity and price must be numbers", new Command("OK"));
                    }
  
            }
        });
        
        addAll(tfTitle, tfDesc, tfImage, tfLocation, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    


    


    
    

   
}
    
}
