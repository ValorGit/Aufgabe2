package aufgabe02;

import Filter.FilterArtikelNummern;
import Filter.FilterDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Zeigt das Fenster zum Anlegen neuer Artikel an.
 *
 * @author Alexander Dünne, Jürgen Christl
 */
public class ArtikelAnlegenController implements Initializable {

    /*
    Festlegen der Variablen
     */
    private static final String FEHLER_KEINE_DEZIMALZAHL = "Bitte geben Sie eine Dezimalzahl ein.";

    private static final String FEHLER_KEINE_ARTIKELNUMMER = "Bitte geben Sie eine Artikelnummer ein.";

    private static final String FEHLER_KEINE_GÜLTIGE_ARTIKELNUMMER = "Bitte geben Sie eine gültige Artikelnummer ein.";

    private static final String FEHLER_KEINE_BEZEICHNUNG = "Bitte geben Sie eine Bezeichnung ein.";

    private static final String FEHLER_KEINE_KATEGORIE = "Bitte wählen Sie eine Kategorie aus.";

    private static final String FEHLER_KEINE_TD = "Bitte geben Sie die technischen Details ein.";

    private static final String FEHLER_KEIN_PREIS = "Bitte geben Sie einen Preis ein.";

    private static final String INFORMATION_TEXT = "Bitte füllen Sie die mit * markierten Pflichtfelder aus.";

    private static final String INFORMATION_TEXT_PREIS = "Der eingebene Preis liegt über den vorgebenen Preisbereich. Bitte überprüfen Sie die Eingabe";

    private MainApp mainApp;

    @FXML
    private TextField artikelIdTextField;

    @FXML
    private TextField nettoTf;

    @FXML
    private TextField bruttoTf;

    @FXML
    private ComboBox<String> steuersatzCb;

    @FXML
    private ComboBox<String> kategorieCb;

    @FXML
    private RadioButton kontoRb;

    @FXML
    private Label artikelnummerMeldung;

    @FXML
    private Label bezeichnungMeldung;

    @FXML
    private Label kategorieMeldung;

    @FXML
    private Label preisMeldung;

    @FXML
    private Label TdMeldung;

    @FXML
    private TextField artikelnummerTf;

    @FXML
    private TextField bezeichnungTf;

    @FXML
    private TextArea beschreibungTextArea;

    @FXML
    private TextArea TdTextArea;

    @FXML
    private Button abbrechen;

    @FXML
    private Button speichern;

    private String artikelId = setzeArtikelId();

    private int aId = 0;

    private static final String CONFIRMATION_TITLE = "Fenster schließen";

    private static final String CONFIRMATION_TEXT = "Sollen alle Änderungen verworfen und das Fenster geschlossen werden?";

    private final Meldung meldung = new Meldung();

    private Stage stage;

    /**
     * Weist dem Controller die Stage aus der Mainapp zu
     *
     * @param stage legt die Stage fest
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Generiert eine Artikel-ID und vergibt diese beim Anlegen eines Artikels
     */
    private String setzeArtikelId() {

        aId = aId + 1;
        Calendar cal = new GregorianCalendar();
        return "AR" + cal.get(Calendar.YEAR) + aId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        artikelnummerTf.setTextFormatter(new TextFormatter<>(new FilterArtikelNummern()));
        nettoTf.setTextFormatter(new TextFormatter<>(new FilterDecimal()));

        // artikelId in das Textfeld eintragen
        artikelIdTextField.textProperty().set(artikelId);
        // Steuersätze festlegen
        ObservableList<String> listSteuersatz = FXCollections.observableArrayList("", "19", "7");
        steuersatzCb.setItems(listSteuersatz);
        steuersatzCb.setValue("19");

        // Listener zur Umrechnung des Nettopreises in Brutto
        nettoTf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                umrechnen();
            }
        });

//        datum.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                if (!newValue) {
//                    if (datum.getValue().isBefore(LocalDate.now().minusWeeks(1))) {
//                        fehler.setText("Rückfrage: Datum liegt weit zurück");
//                    }
//                    if (datum.getValue().isAfter(LocalDate.now())) {
//                        datum.requestFocus();
//                        fehler.setText("Datum liegt in der Zukunft");
//                        datum.getEditor().selectAll();
//                    }
//                } else {
//                    datum.getEditor().selectAll();
//                }
//            }
//        });
        // Umrechnung bei Änderung des Steuerssatzes
        steuersatzCb.setOnAction(e -> {

            umrechnen();
        });

        //Artikelkategorien festlegen
        ObservableList<String> listKategorie = FXCollections.observableArrayList("Kategorie 1", "Kategorie 2", "Kategorie 3");
        kategorieCb.setItems(listKategorie);

        speichern.setOnAction((ActionEvent event) -> {
            boolean formIsValid = validateForm();
            Alert meldung = new Alert(Alert.AlertType.INFORMATION, INFORMATION_TEXT, ButtonType.OK);

            meldung.setHeaderText("");
//            meldung.setTitle(CONFIRMATION_TITLE);

            if (artikelnummerTf.getText().isEmpty() || bezeichnungTf.getText().isEmpty() || (kategorieCb.getValue() == null) || TdTextArea.getText().isEmpty() || nettoTf.getText().isEmpty()) {

                meldung.showAndWait();
            }
            if (formIsValid) {
                speichern();

            }
        });

    }

    // Schließt das Fenster bei Betätigung des abbrechen-Buttons
    @FXML
    public void handleCloseButtonAction() {

        if ((nettoTf.getText() == null || nettoTf.getText().trim().isEmpty())
                && (bruttoTf.getText() == null || bruttoTf.getText().trim().isEmpty())
                && (steuersatzCb.getValue() == "19")
                && (kategorieCb.getValue() == null)
                && (artikelnummerTf.getText() == null || artikelnummerTf.getText().trim().isEmpty())
                && (beschreibungTextArea.getText() == null || beschreibungTextArea.getText().trim().isEmpty())
                && (bezeichnungTf.getText() == null || bezeichnungTf.getText().trim().isEmpty())
                && (TdTextArea.getText() == null || TdTextArea.getText().trim().isEmpty())) {

            stage.close();
        } else {
            if (meldung.showExitPlatformAlert()) {
                stage.close();
            }
        }
    }

    // Rechnet einen Nettobetrag in brutto um
    @FXML
    private void umrechnen() {

        // Ausgabefeld leeren um inkonsistente Anzeigen zu vermeiden
        bruttoTf.clear();

        // Nettopreis und Steuersatz holen
        NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMANY);

        Alert meldung = new Alert(Alert.AlertType.INFORMATION, INFORMATION_TEXT_PREIS, ButtonType.OK);

        meldung.setHeaderText("");
//            meldung.setTitle(CONFIRMATION_TITLE);

        double preis;
        double steuer;
        try {
            Number p = nf_in.parse(nettoTf.getText());
            Number s = nf_in.parse(steuersatzCb.getValue());
            preis = p.doubleValue();
            steuer = s.doubleValue();

            preisMeldung.setText("");

        } catch (ParseException e) {
            nettoTf.clear();
            System.out.println(e.getMessage());
            preisMeldung.setText(FEHLER_KEINE_DEZIMALZAHL);

            return;
        }

        // Errechneten Bruttowert anzeigen
        preis = (preis * (steuer + 100) / 100.0);
        preis = Math.floor(preis * 100) / 100.0;

        if (preis > 1000) {

            meldung.showAndWait();
        }

        String output = nf_in.format(preis);
        bruttoTf.setText(output);
    }

    // Kontroliert das Formular auf Vollständigkeit und Korrektheit.
    private boolean validateForm() {

        boolean validate = true;

        if (nettoTf.getText().isEmpty()) {

            preisMeldung.setText(FEHLER_KEIN_PREIS);
            nettoTf.requestFocus();

            validate = false;

        } else {
            preisMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (TdTextArea.getText().isEmpty()) {

            TdMeldung.setText(FEHLER_KEINE_TD);
            TdTextArea.requestFocus();

            validate = false;

        } else {
            TdMeldung.setText("");

            if (validate != false) {

                validate = true;

            }
        }

        if (kategorieCb.getValue() == null) {

            kategorieMeldung.setText(FEHLER_KEINE_KATEGORIE);
            kategorieCb.requestFocus();

            validate = false;

        } else {
            kategorieMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (bezeichnungTf.getText().isEmpty()) {

            bezeichnungMeldung.setText(FEHLER_KEINE_BEZEICHNUNG);
            bezeichnungTf.requestFocus();

            validate = false;

        } else {
            bezeichnungMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (artikelnummerTf.getText().isEmpty()) {

            artikelnummerMeldung.setText(FEHLER_KEINE_ARTIKELNUMMER);
            artikelnummerTf.requestFocus();

            validate = false;

        } else {
            if (!artikelnummerTf.getText().matches("[0-9]{3}" + "-?" + "[0-9]{4}" + "-?" + "[0-9]{3}")) {

                artikelnummerMeldung.setText(FEHLER_KEINE_GÜLTIGE_ARTIKELNUMMER);
                artikelnummerTf.requestFocus();

                validate = false;
            } else {

                artikelnummerMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
        }

        return validate;
    }

    private void speichern() {

        System.out.println("Hab gespeichert");
        // In Datenstruktur ablegen

//        resetForm();
    }

    /**
     * Legt das Fenster als aktives Fenster fest
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
