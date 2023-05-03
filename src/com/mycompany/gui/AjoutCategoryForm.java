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

/**
 *
 * @author Nadine
 */
public class AjoutCategoryForm extends SideMenuBaseForm {
   
    
    Form AjoutCategory;
    
    
    public AjoutCategoryForm(Resources res){
        
  super("Categorie",BoxLayout.y()); 

         setupSideMenu(res);
  
    Toolbar  tb = new Toolbar(true);
    AjoutCategory = this;
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
        RadioButton mesListes = RadioButton.createToggle("Categories", barGroup);
        mesListes.setUIID("SelectBar");
       // RadioButton liste = RadioButton.createToggle("Autres", barGroup);
       // liste.setUIID("SelectBar");
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
        add.addActionListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(add, arrow);
                 
    new AjoutCategoryForm(res).show();
    refreshTheme();
        });
        
        bindButtonSelection(mesListes, arrow);
       // bindButtonSelection(liste, arrow);
        bindButtonSelection(add, arrow);

        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        
        //
    
    
   TextField nom = new TextField("","Saisir nom");
   nom.setUIID("TextFieldBlack");
   addStringValue("Nom du Categorie", nom);
   
  /* TextField image = new TextField("","Saisir description");
   image.setUIID("TextFieldBlack");
   addStringValue("Image", image);*/
   
   
   Button btnAjouter = new Button("Ajouter");
   addStringValue("",btnAjouter);
   
   
   btnAjouter.addActionListener((e) -> {
       try{
           if(nom.getText() =="" /*|| image.getText() ==""*/)
               Dialog.show("Veuillez verifier les données","","Annuler","OK");
              if (nom.getText().length() < 2) 
        Dialog.show("Le Nom du categorie doit faire au moins 2 caractères","", "Annuler","OK");
           
           else{
               InfiniteProgress ip = new InfiniteProgress();
               
               final Dialog iDialog = ip.showInfiniteBlocking();
               
               Category catg = new Category( 
                       
                       String.valueOf(nom.getText()).toString()
                      // String.valueOf(image.getText()).toString()
               );
                       
               System.out.println("data Category == "+catg);
               
               ServiceCategory.getInstance().ajoutCategory(catg);
               
               iDialog.dispose();
               
                new ListCategoryForm(res).show();
                
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
