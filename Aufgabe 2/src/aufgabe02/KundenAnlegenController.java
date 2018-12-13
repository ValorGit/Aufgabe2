package aufgabe02;

import Filter.FilterArtikelNummern;
import Filter.FilterHausnummer;
import Filter.FilterPLZ;
import Filter.FilterTelefon;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Zeigt das Formular zum Anlegen von Kunden in einem neuen Fenster an
 *
 * @author Alexander Dünne
 */
public class KundenAnlegenController implements Initializable {

    // Variablen deklarieren
    private MainApp mainApp;

    private static final String INFORMATION_TEXT = "Bitte füllen Sie die mit * markierten Pflichtfelder aus.";

    private static final String FEHLER_KEIN_FIRMENNAME = "Bitte geben Sie einen Firmennamen ein.";

    private static final String FEHLER_KEINE_STRASSE = "Bitte geben Sie eine Straße ein.";

    private static final String FEHLER_KEINE_HAUSNUMMER = "Bitte geben Sie eine Hausnummer ein.";

    private static final String FEHLER_KEINE_PLZ = "Bitte geben Sie eine Postleitzahl ein.";
    
    private static final String FEHLER_KEINE_GÜLTIGE_PLZ = "Bitte geben Sie eine gültige Postleitzahl ein.";

    private static final String FEHLER_KEIN_ORT = "Bitte geben Sie einen Ort ein.";

    private static final String FEHLER_KEIN_BUNDESLAND = "Bitte wählen Sie ein Bundesland aus.";

    private static final String FEHLER_KEINE_TELEFONNUMMER = "Bitte geben Sie eine Telefonnummer ein.";
    
    private static final String FEHLER_KEINE_GÜLTIGE_TELEFONNUMMER = "Bitte geben Sie eine gültige Telefonnummer ein.";
    
    private static final String FEHLER_KEINE_GÜLTIGE_EMAIL = "Bitte geben Sie eine gültige E-Mail ein.";
    
    private static final String FEHLER_KEINE_GÜLTIGE_IBAN = "Bitte geben Sie eine gültige IBAN ein.";

    @FXML
    private TextField KontoinhaberTextfield;

    @FXML
    private TextField FirmennamenTextfield;

    @FXML
    private TextField VornameTextfield;

    @FXML
    private TextField NachnameTextfield;

    @FXML
    private TextField StraßeTextfield;

    @FXML
    private TextField HausnummerTextfield;

    @FXML
    private TextField PlzTextfield;

    @FXML
    private TextField OrtTextfield;

    @FXML
    private TextField TelefonnummerTextfield;

    @FXML
    private TextField EmailTextfield;

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

    @FXML
    private TextField IbanTextfield;

    @FXML
    private TextField BicTextfield;

    @FXML
    private TextField BankTextfield;

    //Textfelderstellung bei abweichender Lieferanschrift
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
    private Label firmennameMeldung;

    @FXML
    private Label straßeMeldung;

    @FXML
    private Label hausnummerMeldung;

    @FXML
    private Label plzMeldung;

    @FXML
    private Label ortMeldung;

    @FXML
    private Label bundeslandMeldung;

    @FXML
    private Label telefonnummerMeldung;
    
    @FXML
    private Label emailMeldung;
    
    @FXML
    private Label ibanMeldung;

    private static final String CONFIRMATION_TEXT = "Soll das Fenster geschlossen werden? Ihre Änderungen werden verworfen.";

    private static final String CONFIRMATION_TITLE = "Abbrechen";

    @FXML
    private TextField kundenIdTextField;

    private int kId = 0;

    private String kundenId = setzeKundenId();
    
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
     * Schließt das Fenster bei Bestätigung des abbrechen-Buttons oder des X
     * oben rechts.
     */
    @FXML
    public void handleCloseButtonAction() {

        if (FirmennamenTextfield.getText().isEmpty()
                & VornameTextfield.getText().isEmpty()
                & NachnameTextfield.getText().isEmpty()
                & StraßeTextfield.getText().isEmpty()
                & HausnummerTextfield.getText().isEmpty()
                & PlzTextfield.getText().isEmpty()
                & OrtTextfield.getText().isEmpty()
                & TelefonnummerTextfield.getText().isEmpty()
                & EmailTextfield.getText().isEmpty()
                & (AnredeCB.getValue() == null)
                & (LandCB.getValue() == "Deutschland")
                & (BundeslandCB.getValue() == null)
                & TelefonnummerTextfield.getText().isEmpty()
                & EmailTextfield.getText().isEmpty()
                & KontoinhaberTextfield.getText().isEmpty()
                & IbanTextfield.getText().isEmpty()
                & BicTextfield.getText().isEmpty()
                & BankTextfield.getText().isEmpty()
                & FirmennamenTextfieldNeu.getText().isEmpty()
                & //                VornameTextfieldNeu.getText().isEmpty() &
                //                NachnameTextfieldNeu.getText().isEmpty() &
                StraßeTextfieldNeu.getText().isEmpty()
                & HausnummerTextfieldNeu.getText().isEmpty()
                & PLZTextfieldNeu.getText().isEmpty()
                & OrtTextfieldNeu.getText().isEmpty()
                & (LandCB1.getValue() == "Deutschland")
                & (BundeslandCB1.getValue() == null)) {

            stage.close();
        } else {
            if (meldung.showExitPlatformAlert()) {
                stage.close();
            }
        }
}

    /**
     * Schließt das aktive Fenster bei Betätigung des "abbrechen"-Buttons
     *
     * @param event wird bei Betätigung des "abbrechen"-Buttons ausgelöst
     */
    

    /**
     * Legt die Kunden-ID fest.
     *
     * @return Gibt die Kunden-ID aus.
     */
    private String setzeKundenId() {

        kId = kId + 1;
        Calendar cal = new GregorianCalendar();
        return "KU" + cal.get(Calendar.YEAR) + kId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Schreibt die Kunden-ID in das Textfeld
        kundenIdTextField.textProperty().set(kundenId);
        HausnummerTextfield.setTextFormatter(new TextFormatter<>(new FilterHausnummer()));
        PlzTextfield.setTextFormatter(new TextFormatter<>(new FilterPLZ()));
        TelefonnummerTextfield.setTextFormatter(new TextFormatter<>(new FilterTelefon()));

        /* Items der Combobox "Anrede" setzen. */
        ObservableList<String> listAnrede = FXCollections.observableArrayList("", "Frau", "Herr");
        AnredeCB.setItems(listAnrede);

        /* Items der Combobox "Land" */
        ObservableList<String> listLänder = FXCollections.observableArrayList("Deutschland", "Österreich");
        LandCB.setItems(listLänder);
        LandCB.setValue("Deutschland");

        /* Items der Combobox "Bundesland" */
        ObservableList<String> listBundesLänderDeutsch = FXCollections.observableArrayList("Baden-Würtemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen");
        ObservableList<String> listBundesLänderÖsterreich = FXCollections.observableArrayList("Burgenland", "Kärnten", "Nieder-österreich", "Ober-österreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien");
        BundeslandCB.setItems(listBundesLänderDeutsch);

        /* Listener zur Anzeige der Bunsdesländer abhängig vom gewählten Land */
        LandCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (LandCB.getValue() == "Deutschland") {
                    BundeslandCB.setItems(listBundesLänderDeutsch);
                } else {
                    BundeslandCB.setItems(listBundesLänderÖsterreich);
                }
            }
        });

        /* Funktionen der Checkbox zum Übernehmen des Kontoinhabers*/
        KontoCb.setOnAction(e -> {
            if (KontoCb.isSelected()) {
                KontoinhaberTextfield.textProperty().bind(FirmennamenTextfield.textProperty());
                KontoinhaberTextfield.setDisable(true);

            } else {

                KontoinhaberTextfield.setDisable(false);
                KontoinhaberTextfield.textProperty().unbind();
                KontoinhaberTextfield.selectAll();
                KontoinhaberTextfield.requestFocus();
                KontoinhaberTextfield.setEditable(true);

            }
        });

        /* Blendet abweichende Lieferanschrift ein */
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

                LandCB1.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        if (LandCB1.getValue() == "Deutschland") {
                            BundeslandCB1.setItems(listBundesLänderDeutsch);
                        } else {
                            BundeslandCB1.setItems(listBundesLänderÖsterreich);
                        }
                    }
                });

            }
        });

        /* Übernimmt die Rechnungsadresse und deaktiviert die Felder 
        für die abweichende Adresse */
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

        SpeichernBtn.setOnAction((ActionEvent event) -> {
            boolean formIsValid = validateForm();
            Alert meldung = new Alert(Alert.AlertType.INFORMATION, INFORMATION_TEXT, ButtonType.OK);

            meldung.setHeaderText("");
//            meldung.setTitle(CONFIRMATION_TITLE);

            if (FirmennamenTextfield.getText().isEmpty() || StraßeTextfield.getText().isEmpty() || HausnummerTextfield.getText().isEmpty() || PlzTextfield.getText().isEmpty() || OrtTextfield.getText().isEmpty() || (BundeslandCB.getValue() == null) || TelefonnummerTextfield.getText().isEmpty()) {

                meldung.showAndWait();
            }
            if (formIsValid) {
                speichern();

            }
        });

    }

    public boolean showExitPlatformAlert() {
//      Alert meldung = new Alert(Alert.AlertType.CONFIRMATION);
        Alert meldung = new Alert(Alert.AlertType.CONFIRMATION, CONFIRMATION_TEXT, ButtonType.YES, ButtonType.NO);
        /* Rückgabewert */
        boolean confirm = false;
        /* Aenderung der Modalitaet auf eine "nicht-modale" Meldung. */
//        meldung.initModality(Modality.NONE);
        /* "Entfernt" den Header und setzt das Symbol links vom Inhalt. */
        meldung.setHeaderText("");
        meldung.setTitle(CONFIRMATION_TEXT);
        System.out.println("Programm läuft nicht weiter");
//        meldung.setContentText(EXIT_TEXT);
//        meldung.show(); //Programm laeuft weiter
        Optional<ButtonType> antwort = meldung.showAndWait(); // Programm wartet vor naechster Zeile
        if (antwort.isPresent()) {
            System.out.println("Die Antwortet lautet: " + antwort.get().getText());
            if (antwort.get().equals(ButtonType.YES)) {
                /* Anwendung schliessen. */
                confirm = true;

            } else {
                confirm = false;
            }

        }
        return confirm;

    }

    private boolean validateForm() {

        boolean validate = true;

        if (FirmennamenTextfield.getText().isEmpty()) {

            firmennameMeldung.setText(FEHLER_KEIN_FIRMENNAME);
//            artikelnummerTf.requestFocus();

            validate = false;

        } else {

            firmennameMeldung.setText("");

            validate = true;
        }

        if (StraßeTextfield.getText().isEmpty()) {

            straßeMeldung.setText(FEHLER_KEINE_STRASSE);

            validate = false;

        } else {
            straßeMeldung.setText("");

            validate = true;
        }

        if (HausnummerTextfield.getText().isEmpty()) {

            hausnummerMeldung.setText(FEHLER_KEINE_HAUSNUMMER);

            validate = false;

        } else {
            hausnummerMeldung.setText("");

            validate = true;
        }

        if (PlzTextfield.getText().isEmpty()) {

            plzMeldung.setText(FEHLER_KEINE_PLZ);

            validate = false;

       } else {
            if (!PlzTextfield.getText().matches("[0-9]{5}")) {

                plzMeldung.setText(FEHLER_KEINE_GÜLTIGE_PLZ);

                validate = false;
            } else {

                plzMeldung.setText("");

                validate = true;
            }
        }

        if (OrtTextfield.getText().isEmpty()) {

            ortMeldung.setText(FEHLER_KEIN_ORT);

            validate = false;

        } else {
            ortMeldung.setText("");

            validate = true;
        }

        if (BundeslandCB.getValue() == null) {

            bundeslandMeldung.setText(FEHLER_KEIN_BUNDESLAND);

            validate = false;

        } else {
            bundeslandMeldung.setText("");

            validate = true;
        }

        if (TelefonnummerTextfield.getText().isEmpty()) {

            telefonnummerMeldung.setText(FEHLER_KEINE_TELEFONNUMMER);

            validate = false;

        } else {
            if (!TelefonnummerTextfield.getText().matches("[0+]{1}[0-9-+ /]+")) {

                telefonnummerMeldung.setText(FEHLER_KEINE_GÜLTIGE_TELEFONNUMMER);

                validate = false;

            } else {
                telefonnummerMeldung.setText("");

                validate = true;
            }
        }
            if (EmailTextfield.getText().isEmpty() || EmailTextfield.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+" + "[.]{1}" + "[a-zA-Z]{2,3}")){
                emailMeldung.setText("");

                validate = true;
                
            }else {
                emailMeldung.setText(FEHLER_KEINE_GÜLTIGE_EMAIL);

                validate = false;
            }
            
            if (IbanTextfield.getText().isEmpty() || IbanTextfield.getText().matches("DE[0-9]{20}") || IbanTextfield.getText().matches("AT[0-9]{18}")){
                ibanMeldung.setText("");

                validate = true;
                
            }else {
                ibanMeldung.setText(FEHLER_KEINE_GÜLTIGE_IBAN);

                validate = false;
            }
        
        return validate;
    }

    private void speichern() {

        System.out.println("Hab gespeichert");
        // In Datenstruktur ablegen

//        resetForm();
    }

    /**
     * Legt das aktive Fenster fest
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
