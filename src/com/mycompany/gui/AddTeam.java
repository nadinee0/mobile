/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.BaseForm;
import com.mycompany.entities.Team;
import com.mycompany.services.ServiceTeam;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class AddTeam extends BaseForm {

    String imageCode;
    String filePath = "";

    public AddTeam(Resources res, Form previous) {
        super("Ajouter Team", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Team");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(sl,
                BorderLayout.south(
                        GridLayout.encloseIn(2,
                                facebook, twitter
                        )
                )
        ));

// ...
        TextComponent nomTeam = new TextComponent().label("Nom_Team");
        add(nomTeam);
        TextComponent descriptionTeam = new TextComponent().label("Description_Team");
        add(descriptionTeam);

        TextComponent winsTeam = new TextComponent().label("Wins_Team");
        add(winsTeam);

        TextComponent lossesTeam = new TextComponent().label("Losses_Team");
        add(lossesTeam);

        TextComponent rateTeam = new TextComponent().label("Rate_Team");
        add(rateTeam);

        TextComponent color = new TextComponent().label("Color");
        add(color);

        TextComponent logoTeam = new TextComponent().label("Logo_Team");
        add(logoTeam);

      
        Button ajouterBtn = new Button("Ajouter");
        ajouterBtn.addActionListener((evt) -> {
            if (nomTeam.getText().equals("") || descriptionTeam.getText().equals("")) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                ServiceTeam sp = new ServiceTeam();
                Team fi = new Team();
                fi.setNomTeam(nomTeam.getText());
                fi.setDescriptionTeam(descriptionTeam.getText());
                fi.setWinsTeam((int) Float.parseFloat(winsTeam.getText()));
                fi.setLossesTeam((int) Float.parseFloat(lossesTeam.getText()));
                fi.setRateTeam(Float.parseFloat(rateTeam.getText()));
                fi.setColor(color.getText());
                fi.setLogoTeam(logoTeam.getText());
                
                

                boolean isAdded = sp.addTeam(fi); // Call the addTeam method from ServiceTeam to add the team to the database
                if (isAdded) {
                    Dialog.show("Success", "Team added successfully", new Command("OK"));
                    new AllTeam(res).show();
                } else {
                    Dialog.show("Error", "Failed to add team", new Command("OK"));
                }
            }
        });

        add(ajouterBtn);

    }
    

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
