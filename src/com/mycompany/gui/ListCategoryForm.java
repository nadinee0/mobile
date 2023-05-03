/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import com.mycompany.entities.Article;
import com.mycompany.services.ServiceArticle;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import com.codename1.ui.validation.LengthConstraint;
import com.mycompany.entities.Category;
import com.mycompany.myapp.SideMenuBaseForm;
import com.mycompany.services.ServiceCategory;
import java.util.ArrayList;

/**
 *
 * @author Nadine
 */
public class ListCategoryForm extends SideMenuBaseForm {
Form current;
    ListCategoryForm(Resources res) {
  
    
     super("Categorie",BoxLayout.y());   
         setupSideMenu(res);

    Toolbar  tb = new Toolbar(true);
    current = this;
    setToolbar(tb);
    
    getTitleArea().setUIID("container");
    setTitle("");
    getContentPane().setScrollVisible(false);
    
    tb.addSearchCommand(e->{
        
    });
    Tabs swipe = new Tabs();
    
    Label s1 = new Label();
    Label s2 = new Label();

    addTab(swipe, s1, res.getImage("gradient-overlay.png"), "", "", res); 
    
    //
        
         swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Categorie", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton add = RadioButton.createToggle("Ajouter", barGroup);
        add.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
    new ListCategoryForm(res).show();
               
               refreshTheme(); 
                  updateArrowPosition(mesListes, arrow);
        });
        
        add.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
    
         new AjoutCategoryForm(res).show();
           
            refreshTheme();
              updateArrowPosition(add, arrow);
        });
 
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, /*liste,*/ add),
                FlowLayout.encloseBottom(arrow)
        ));

        add.setSelected(true);
        arrow.setVisible(false);
        
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(add, arrow);
            
        });
         mesListes.setSelected(true);
        arrow.setVisible(false);
         addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
     
        });
        bindButtonSelection(mesListes, arrow);
       // bindButtonSelection(liste, arrow);
        bindButtonSelection(add, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        ArrayList<Category>list=  ServiceCategory.getInstance().affichageCategory();  
        for(Category catg : list){
            
             String urlImage = "gradient-overlay.png";
             Image placeHolder = Image.createImage(120,90);
 
             EncodedImage enc = EncodedImage.createFromImage(placeHolder,false);
             URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
             
         addButton(urlim,catg,res);   
         ScaleImageLabel image = new ScaleImageLabel(urlim);
      
         //Container containerImg = new Container();
         
         image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
    
        }
    
        
        

}
    
    
     private void addTab(Tabs swipe,Label spacer, Image image, String string, String text, Resources res) {
int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

if(image.getHeight()< size){
    image = image.scaledHeight(size);
}

if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2){
    image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
}

ScaleImageLabel imageScale = new ScaleImageLabel(image);
imageScale.setUIID("container");
imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

Label overLay = new Label("", "ImageOverlay");

    Container page1 =
            LayeredLayout.encloseIn(imageScale,
                    overLay,
                    BorderLayout.south(
                            BoxLayout.encloseY(
                    new SpanLabel(text,"LargeWhiteText"),              
                            spacer
                    )
                    )
            );
    
    swipe.addTab("",res.getImage("news-tab-down-arrow.png"), page1);
    
    }
    public void bindButtonSelection(Button btn, Label l){
        btn.addActionListener(e->{
            if(btn.isSelected()){
                updateArrowPosition(btn,l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
l.getParent().repaint();
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    private void addButton(Image img1, Category cat,Resources res  ) {

        int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       
       Button image1 = new Button(img1.fill(width, height));
       image1.setUIID("Label");
       Container cont = BorderLayout.west(image1);       
      
        Label nomTxt = new Label("nom : "+cat.getNom(),"NewsTopLine2");
//        Label imageTxt = new Label("img : "+cat.getImg(),"NewsTopLine2");
      
  createLineSeparator();
    
  //delete Button
 Label lSupprimer = new Label(" ");
           lSupprimer.setUIID("NewsTopLine");
           Style supprimerStyle = new Style(lSupprimer.getUnselectedStyle());
           supprimerStyle.setFgColor(0xf21f1f);

           FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE,supprimerStyle);
           lSupprimer.setIcon(supprimerImage);
           lSupprimer.setTextPosition(RIGHT);
  
           //click delete icon
           lSupprimer.addPointerPressedListener(l -> {
               Dialog dig = new Dialog("Suppression");
               if(dig.show("Suppression","Vous voulez supprimer cette categorie ?","Annuler","Confirmer" )){
                   dig.dispose();
               }
           
                   else{
                           dig.dispose();
                           
                           if(ServiceCategory.getInstance().deleteCategory(cat.getId())){
                             new ListCategoryForm(res).show();  
                               refreshTheme(); 
                           }
                            refreshTheme(); 
                           }});
           
   
           //Update icon
           Label lModifier = new Label(" ");
           lModifier.setUIID("NewsTopLine");
           Style modifierStyle = new Style(lModifier.getUnselectedStyle());
           modifierStyle.setFgColor(0xf7ad02);
          
             FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT,modifierStyle);
           lModifier.setIcon(mFontImage);
           lModifier.setTextPosition(LEFT);
           
          lModifier.addPointerPressedListener(l -> {
                new ModifierCategoryForm(res,cat).show();
                refreshTheme(); 
                
          });  
  
         
                   
  cont.add(BorderLayout.CENTER,BoxLayout.encloseY(
          BoxLayout.encloseX(nomTxt,lModifier,lSupprimer)  
       // BoxLayout.encloseX(imageTxt,lModifier,lSupprimer)
  ));
  
  
  add(cont);
    
   
    
}

    @Override
    protected void showOtherForm(Resources res) {
        Image profilePic = res.getImage("user-picture.jpg");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("  Nadine Elleuch", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");
        
        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addMaterialCommandToSideMenu("  Article", FontImage.MATERIAL_DASHBOARD,  e -> new AjoutArticleForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Categorie", FontImage.MATERIAL_TRENDING_UP,  e -> new AjoutCategoryForm(res).show());
        getToolbar().addMaterialCommandToSideMenu("  Statistique", FontImage.MATERIAL_TRENDING_DOWN,  e -> new StatistiquePieForm(res).show());

    }
}
