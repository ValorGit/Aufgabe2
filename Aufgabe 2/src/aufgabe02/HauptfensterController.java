package aufgabe02;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Zeigt das Hauptfenster mit Auswahlmenü an.
 *
 * @author Alexander Dünne, Jürgen Christl
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

    private static final String CONFIRMATION_TITLE = "Programm beenden";
    private static final String INFORMATION_TITLE = "Info";
    
    private static final String CONFIRMATION_TEXT = "Sollen alle Fenster geschlossen und die Anwendung beendet werden?";
    private static final String INFORMATION_TEXT = "Entwickler: \n\nJürgen Christl \nAlexander Dünne";
    

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

    @FXML
    private void beendenMenuItemAction() {
        
        Alert meldung = new Alert(Alert.AlertType.CONFIRMATION, CONFIRMATION_TEXT, ButtonType.YES, ButtonType.NO);

        /* "Entfernt" den Header und setzt das Symbol links vom Inhalt. */
        meldung.setHeaderText("");
        meldung.setTitle(CONFIRMATION_TITLE);
        Optional<ButtonType> antwort = meldung.showAndWait(); // Programm wartet vor naechster Zeile
        if (antwort.isPresent()) {
            if (antwort.get().equals(ButtonType.YES)) {
                /* Anwendung schliessen. */
                Platform.exit();
            }
        }
    }
    
    @FXML
    private void infoButtonItemAction() {
        Alert meldung = new Alert(Alert.AlertType.INFORMATION);
//        Image image = new Image("/ok.png");
//        ImageView imageScale = new ImageView(image);
//        imageScale.setFitHeight(20);
//        imageScale.setFitWidth(20);
//        meldung.setGraphic(imageScale);
        meldung.setHeaderText("");
        meldung.setTitle(INFORMATION_TITLE);
        meldung.setContentText(INFORMATION_TEXT);
        meldung.showAndWait();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
