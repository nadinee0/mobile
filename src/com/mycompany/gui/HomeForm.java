/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author USER
 */
public class HomeForm extends Form {
    
    public HomeForm() {
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnevents = new Button("Afficher Events");
        Button btnaddevent = new Button("Ajouter Events");

        
        btnevents.addActionListener(e-> new AfficherEvents(this).show());
        btnaddevent.addActionListener(e-> new AjoutEvents(this).show());
        addAll(btnevents,btnaddevent);
        
         
       
    }
    
}
