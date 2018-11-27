/*
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe.pkg2;

import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author Alex
 */
public class AuftragAnlegenController implements Initializable {
    
    
    private MainApp mainApp;
    
    @FXML
    private Button AbbrechenBtn;
    
    @FXML
    private MenuItem ProzessMenuItem;
    
    @FXML
    private DatePicker BestelldatumDp;
    
    @FXML
    private TextField auftragsIdTextField;


    private String auftragsId = setzeAuftragsId();

    @FXML
    private TableView ArtikelTable;

    @FXML
    private Button artikelBearbeitenButton;
    
    @FXML
    private Button LöschenButton;
    
    @FXML 
    private TableView<Auftragsposition> artikelTableView;
    
    @FXML
    private TableColumn<Auftragsposition, String> artikelIdColumn;
    
    @FXML 
    private TableColumn<Auftragsposition, String> bezeichnungColumn;
    
    @FXML
    private TableColumn<Auftragsposition, Integer> anzahlColumn;
    
    @FXML
    private TableColumn<Auftragsposition, Double> einzelpreisColumn;
    
    @FXML
    private TableColumn<Auftragsposition, Double> gesamtpreisColumn;
    
    @FXML 
    private TextField artikelIdTextField;
    
    @FXML 
    private TextField bezeichnungTextField;
    
    @FXML 
    private TextField anzahlTextField;
    
    @FXML 
    private TextField einzelpreisTextField;
    
    @FXML
    private TextField auftragswertTextField;
    
    public void changeArtikelIdCellEvent(CellEditEvent edittedCell)
    {
        Auftragsposition positionSelected =  artikelTableView.getSelectionModel().getSelectedItem();
        positionSelected.setArtikelID(edittedCell.getNewValue().toString());
        
}
    
//     public void changeQuantityCellEvent(CellEditEvent edittedCell)
//    {
//        Auftragsposition positionSelected =  artikelTableView.getSelectionModel().getSelectedItem();
//        positionSelected.setAnzahl(edittedCell.getNewValue());
//        
//}
    
    private int aId = 0;

    private String setzeAuftragsId() {

        aId = aId + 1;
        Calendar cal = new GregorianCalendar();
        return "AU" + cal.get(Calendar.YEAR) + aId;
    }
    
    
    
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
        BestelldatumDp.setValue(LocalDate.now());
        
        auftragsIdTextField.textProperty().set(auftragsId);
        
        artikelIdColumn.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("ArtikelID"));
        bezeichnungColumn.setCellValueFactory(new PropertyValueFactory<Auftragsposition, String>("Artikelbezeichnung"));
        anzahlColumn.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Integer>("Anzahl"));
        einzelpreisColumn.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Double>("Einzelpreis"));
        gesamtpreisColumn.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Double>("Gesamtpreis"));
        
        artikelTableView.setItems(getAuftragsposition());     
      
        artikelTableView.setEditable(true);
        artikelIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bezeichnungColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        
        
        anzahlColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        einzelpreisColumn.textProperty().addListener(new ChangeListener <String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                gesamtpreisColumn.setCellValueFactory(new PropertyValueFactory<Auftragsposition, Double>("Gesamtpreis"));
        }
        });
        



        artikelTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
     
      
    }
    
//    /**
//     * Diese Methode löscht die ausgewählten Zeilen 
//     */
    public void deleteButtonPushed()
    {
        ObservableList<Auftragsposition> selectedRows, alleAuftragspos;
        alleAuftragspos = artikelTableView.getItems();
        
        
        selectedRows = artikelTableView.getSelectionModel().getSelectedItems();
        
        
        for (Auftragsposition position: selectedRows)
        {
            alleAuftragspos.remove(position);
        }
        
}
//    
//    /**
//     * Fügt eine neue Auftragsposition in der Tabelle ein
//     */
    public void neueAuftragsposButtonPushed()
    {
        Auftragsposition newPosition = new Auftragsposition(artikelIdTextField.getText(),
                                      bezeichnungTextField.getText(),
                                      Integer.valueOf(anzahlTextField.getText()),
                                      Double.valueOf(einzelpreisTextField.getText()),
                                      Integer.valueOf(anzahlTextField.getText()) *
                                      Double.valueOf(einzelpreisTextField.getText()));    
        
       
        artikelTableView.getItems().add(newPosition);
}
//    
    public ObservableList<Auftragsposition>  getAuftragsposition()
    {
        ObservableList<Auftragsposition> position = FXCollections.observableArrayList();
        position.add(new Auftragsposition("AR20181","Uran", 2, 50.0, 100.0));
       
        position.add(new Auftragsposition("AR20182","Plutonium", 2, 80.0, 160.0));
        
        return position;
}
    
//     public void getSum()
//    {
//        int sum = 0;
//        for(int i = 0; i < artikelTableView.getItems().size(); i++)
//        {
//            sum = Integer.parseInt(artikelTableView.getValueAt(i, 3).toString()) + sum;
//        }
//        
//        a.setText(Integer.toString(sum));
//    }
    
//    public int getSum(){
//        
//        int sum = artikelTableView.getItems().size();
//        
//        
//        
//        return sum;
//    }
//    
   
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
}
    
}

