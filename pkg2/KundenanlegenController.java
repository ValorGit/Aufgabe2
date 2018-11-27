/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe.pkg2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class KundenanlegenController implements Initializable {
    
    private MainApp mainApp;
    
    @FXML
    private TextField KontoinhaberTextfield;
    
    @FXML
    private TextField FirmennamenTextfield;
     
    @FXML
    private ComboBox<String> AnredeCB;
    
    @FXML
    private ComboBox<String> LandCB;
    
    @FXML
    private ComboBox<String> BundeslandCB;
    
    @FXML
    private RadioButton KontoRb;
    
    @FXML
    private CheckBox KontoCb;
    
    @FXML
    private Button AbbrechenBtn;
    
    @FXML
    private Button SpeichernBtn;
    
    
    //Textfeld erstellung bei abweichender Lieferanschrift
    
    @FXML
    private Label FirmennameLabelNeu;
    
    @FXML
    private Label StraßeLabelNeu;
    
    @FXML
    private Label OrtLabelNeu;
    
    @FXML
    private Label LandLabelNeu;
    
    @FXML
    private Label BundeslandLabelNeu;
    
    @FXML
    private TextField FirmennamenTextfieldNeu;
    
    @FXML
    private TextField VornameTextfieldNeu;
    
    @FXML
    private TextField NachnameTextfieldNeu;
    
    @FXML
    private TextField StraßeTextfieldNeu;
    
    @FXML
    private TextField HausnummerTextfieldNeu;
    
    @FXML
    private TextField PLZTextfieldNeu;
    
    @FXML
    private TextField OrtTextfieldNeu;
    
    @FXML
    private ComboBox<String> LandCB1;
    
    @FXML
    private ComboBox<String> BundeslandCB1;
    
    @FXML
    private RadioButton RechnungsadresseRb;
    
    @FXML
    private RadioButton NeueLieferanschriftRb;
    
    @FXML
    private AnchorPane AnchorpaneKd;
    
    @FXML
    private ScrollPane ScrollpaneKd;
    
    
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
    Stage stage = (Stage) AbbrechenBtn.getScene().getWindow();
    stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          /* Items der Combobox setzen. */
        ObservableList<String> listAnrede = FXCollections.observableArrayList("","Frau", "Herr");
        AnredeCB.setItems(listAnrede);
        /* Vorbelegung der ComboBox. */
        
        ObservableList<String> listLänder = FXCollections.observableArrayList("Deutschland","Österreich");
        LandCB.setItems(listLänder);
        LandCB.setValue("Deutschland");
        
        ObservableList<String> listBundesLänderDeutsch = FXCollections.observableArrayList("Baden-Würtemberg","Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen");
        ObservableList<String> listBundesLänderÖsterreich = FXCollections.observableArrayList("Burgenland","Kärnten", "Nieder-österreich", "Ober-österreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien");
        
        BundeslandCB.setItems(listBundesLänderDeutsch);
        
        LandCB.valueProperty().addListener(new ChangeListener <String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                if(LandCB.getValue() == "Deutschland"){
            BundeslandCB.setItems(listBundesLänderDeutsch);
        } else{ 
            BundeslandCB.setItems(listBundesLänderÖsterreich);
        }
        }
        });
        
        KontoCb.setOnAction(e -> {
            if (KontoCb.isSelected()) {
                 KontoinhaberTextfield.textProperty().bind(FirmennamenTextfield.textProperty());
                 
                 KontoinhaberTextfield.setEditable(false);
                 
                 
                 
         
                 
            } else {
                
                KontoinhaberTextfield.textProperty().unbind();
                KontoinhaberTextfield.selectAll();
                KontoinhaberTextfield.requestFocus();
                KontoinhaberTextfield.setEditable(true);
                
            }
        }); 
        
       NeueLieferanschriftRb.setOnAction(e -> {
            if (NeueLieferanschriftRb.isSelected()) {
                 
                FirmennameLabelNeu.setVisible(true); 
                StraßeLabelNeu.setVisible(true); 
                OrtLabelNeu.setVisible(true); 
                LandLabelNeu.setVisible(true); 
                BundeslandLabelNeu.setVisible(true); 
                StraßeTextfieldNeu.setVisible(true);
                FirmennamenTextfieldNeu.setVisible(true);
                FirmennamenTextfieldNeu.requestFocus();
                HausnummerTextfieldNeu.setVisible(true);
                PLZTextfieldNeu.setVisible(true);
                OrtTextfieldNeu.setVisible(true);
                LandCB1.setVisible(true);
                BundeslandCB1.setVisible(true);
                
                ScrollpaneKd.setPrefHeight(1100);
                AnchorpaneKd.setPrefHeight(1100);
                
                SpeichernBtn.setLayoutY(1050);
                AbbrechenBtn.setLayoutY(1050);
                
                
                LandCB1.setItems(listLänder);
                LandCB1.setValue("Deutschland"); 
                
                BundeslandCB1.setItems(listBundesLänderDeutsch);
        
                LandCB1.valueProperty().addListener(new ChangeListener <String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                        String oldValue, String newValue) {
                    if(LandCB1.getValue() == "Deutschland"){
                BundeslandCB1.setItems(listBundesLänderDeutsch);
            } else{ 
                BundeslandCB1.setItems(listBundesLänderÖsterreich);
            }
         }
         });
                 
         
           
                
            
            }   
        }); 
       
       RechnungsadresseRb.setOnAction(e -> {
            if (RechnungsadresseRb.isSelected()) {
                 
               FirmennameLabelNeu.setVisible(false);
                StraßeLabelNeu.setVisible(false); 
                OrtLabelNeu.setVisible(false); 
                LandLabelNeu.setVisible(false); 
                BundeslandLabelNeu.setVisible(false); 
                StraßeTextfieldNeu.setVisible(false);
                FirmennamenTextfieldNeu.setVisible(false);
                HausnummerTextfieldNeu.setVisible(false);
                PLZTextfieldNeu.setVisible(false);
                OrtTextfieldNeu.setVisible(false);
                LandCB1.setVisible(false);
                BundeslandCB1.setVisible(false);
                
                
                ScrollpaneKd.setPrefHeight(820);
                AnchorpaneKd.setPrefHeight(820);
                
                AbbrechenBtn.setLayoutY(847);
                SpeichernBtn.setLayoutY(847);
        
         
         
       
           }   
        });   
    }    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
}
  
}
