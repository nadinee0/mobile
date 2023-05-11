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
package com.mycompany.myapp;

import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.mycompany.gui.AddTeam;
import com.mycompany.gui.AfficherEvents;
import com.mycompany.gui.AjoutArticleForm;
import com.mycompany.gui.AjoutCategoryForm;
import com.mycompany.gui.AjoutEvents;
import com.mycompany.gui.AllTeam;
import com.mycompany.gui.StatistiquePieForm;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {

    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(" Nadine Elleuch", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu(" Article", FontImage.MATERIAL_DASHBOARD, e -> new AjoutArticleForm(res).show());
        getToolbar().addMaterialCommandToSideMenu(" Categorie", FontImage.MATERIAL_TRENDING_UP, e -> new AjoutCategoryForm(res).show());
        getToolbar().addMaterialCommandToSideMenu(" Statistique Article", FontImage.MATERIAL_TRENDING_DOWN, e -> new StatistiquePieForm(res).show());
        getToolbar().addMaterialCommandToSideMenu(" Events", FontImage.MATERIAL_DASHBOARD, e -> new AfficherEvents(this).show());
        getToolbar().addMaterialCommandToSideMenu(" Add Events", FontImage.MATERIAL_DASHBOARD, e -> new AjoutEvents(this).show());
        getToolbar().addMaterialCommandToSideMenu(" Teams", FontImage.MATERIAL_DASHBOARD, e -> new AllTeam(res).show());
        //getToolbar().addMaterialCommandToSideMenu(" Add teams", FontImage.MATERIAL_DASHBOARD, e -> new AddTeam(this).show());


        /*  Button btnevents = new Button("Afficher Events");
        Button btnaddevent = new Button("Ajouter Events");

        
        btnevents.addActionListener(e-> new AfficherEvents(this).show());
        btnaddevent.addActionListener(e-> new AjoutEvents(this).show());
        addAll(btnevents,btnaddevent);*/
    }

    protected abstract void showOtherForm(Resources res);
}
