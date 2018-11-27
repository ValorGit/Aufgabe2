/*
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe.pkg2;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Alex
 */
public class ArtikelAnlegenController implements Initializable {
    
    private static final String FEHLER_KEINE_DEZIMALZAHL = "Bitte geben Sie eine Dezimalzahl ein.";
    
    private MainApp mainApp;
    
    @FXML
    private TextField artikelIdTextField;
    
    @FXML
    private TextField NettoTextfield;
    
    @FXML
    private TextField BruttoTextfield;
     
    @FXML
    private ComboBox<String> SteuersatzCb;
    
    @FXML
    private ComboBox<String> KategorieCb;
    
    
    @FXML
    private RadioButton KontoRb;
    
    @FXML
    private Label Meldung;
    
    @FXML
    private Button AbbrechenBtn;
    
    private String artikelId = setzeArtikelId();
    
    private int aId = 0;
    
    private String setzeArtikelId() {
        
        aId = aId + 1;
        Calendar cal = new GregorianCalendar();
        return "AR" + cal.get(Calendar.YEAR) + aId;
    }
//    
    
      
        
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        artikelIdTextField.textProperty().set(artikelId);
          /* Items der Combobox setzen. */
        ObservableList<String> listSteuersatz = FXCollections.observableArrayList("","19", "7");
        SteuersatzCb.setItems(listSteuersatz);
        SteuersatzCb.setValue("19");
       
        /* Vorbelegung der ComboBox. */
        
         NettoTextfield.textProperty().addListener(new ChangeListener <String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                umrechnen();
        }
        });
        
//         BruttoTextfield.clear();
         
         
          SteuersatzCb.setOnAction(e -> {
//            if (!tfAusgabe.getText().isEmpty()) {
                umrechnen();
//            }
        });

        ObservableList<String> listKategorie = FXCollections.observableArrayList("Kategorie 1","Kategorie 2", "Kategorie 3");
        KategorieCb.setItems(listKategorie);
      
    }
    
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
    Stage stage = (Stage) AbbrechenBtn.getScene().getWindow();
    stage.close();
}

    @FXML
    private void umrechnen() {
        /* Ausgabefeld leeren um inkonsistente Anzeigen zu vermeiden */
        BruttoTextfield.clear();

        /* Eingabetemperatur holen */
        double preis;
        double steuer;
        try {
            preis = Double.valueOf(NettoTextfield.getText());
            steuer = Double.valueOf(SteuersatzCb.getValue());
            
            Meldung.setText("");
            
        } catch (NumberFormatException e) {
            NettoTextfield.clear();
            System.out.println(e.getMessage());
            Meldung.setText(FEHLER_KEINE_DEZIMALZAHL);
           
            return;
        }

//       

        /* Errechneten Wert anzeigen */
        preis = (preis * (steuer + 100) / 100.0);
        preis = Math.floor(preis * 100) / 100.0;
        BruttoTextfield.setText(Double.toString(preis));
    }    
   
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
}
}

