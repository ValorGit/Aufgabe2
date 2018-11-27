/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe.pkg2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Jürgen
 */
public class HauptfensterController implements Initializable {

    private MainApp mainApp;

    @FXML
    private Menu dateiMenu;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private MenuItem exportMenuItem;

    @FXML
    private MenuItem prozessMenuItem;

    @FXML
    private MenuItem einstellungenMenuItem;

    @FXML
    private MenuItem beendenMenuItem;

    @FXML
    public void beendenMenuItemAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void kundenAnlegenMenuItemAction(ActionEvent event) {
        mainApp.zeigeKundenAnlegenDialog();
    }

    @FXML
    private void artikelAnlegenMenuItemAction(ActionEvent event) {
        mainApp.zeigeArtikelAnlegenDialog();
    }

    @FXML
    private void auftragAnlegenMenuItemAction(ActionEvent event) {
        mainApp.zeigeAuftragAnlegenDialog();
    }

    @FXML
    private void prozessMenuItemAction(ActionEvent event) {
        mainApp.zeigeProzessvisualisierungDialog();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}