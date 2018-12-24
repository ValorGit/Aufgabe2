package aufgabe02;

import Filter.FilterArtikelNummern;
import Filter.FilterHausnummer;
import Filter.FilterAlphaNumeric;
import Filter.FilterOrt;
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
 * @author Alexander Dünne, Jürgen Christl
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

    private static final String FEHLER_KEINE_GÜLTIGE_BIC = "Bitte geben Sie eine gültige BIC ein.";

    @FXML
    private TextField kontoinhaberTf;

    @FXML
    private TextField firmennamenTf;

    @FXML
    private TextField vornameTf;

    @FXML
    private TextField nachnameTf;

    @FXML
    private TextField straßeTf;

    @FXML
    private TextField hausnummerTf;

    @FXML
    private TextField plzTf;

    @FXML
    private TextField ortTf;

    @FXML
    private TextField telefonnummerTf;

    @FXML
    private TextField emailTf;

    @FXML
    private ComboBox<String> anredeCB;

    @FXML
    private ComboBox<String> landCB;

    @FXML
    private ComboBox<String> bundeslandCB;

    @FXML
    private RadioButton kontoRb;

    @FXML
    private CheckBox kontoCb;

    @FXML
    private Button abbrechen;

    @FXML
    private Button speichern;

    @FXML
    private TextField ibanTf;

    @FXML
    private TextField bicTf;

    @FXML
    private TextField bankTf;

    //Textfelderstellung bei abweichender Lieferanschrift
    @FXML
    private Label firmennameLabelNeu;

    @FXML
    private Label straßeLabelNeu;

    @FXML
    private Label ortLabelNeu;

    @FXML
    private Label landLabelNeu;

    @FXML
    private Label bundeslandLabelNeu;

    @FXML
    private TextField firmennamenTfNeu;

    @FXML
    private TextField vornameTfNeu;

    @FXML
    private TextField nachnameTfNeu;

    @FXML
    private TextField straßeTfNeu;

    @FXML
    private TextField hausnummerTfNeu;

    @FXML
    private TextField plzTfNeu;

    @FXML
    private TextField ortTfNeu;

    @FXML
    private ComboBox<String> landCb1;

    @FXML
    private ComboBox<String> bundeslandCb1;

    @FXML
    private RadioButton rechnungsadresseRb;

    @FXML
    private RadioButton neueLieferanschriftRb;

    @FXML
    private AnchorPane anchorpaneKd;

    @FXML
    private ScrollPane scrollpaneKd;

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

    @FXML
    private Label bicMeldung;

    @FXML
    private Label firmennameNeuMeldung;

    @FXML
    private Label straßeNeuMeldung;

    @FXML
    private Label hausnummerNeuMeldung;

    @FXML
    private Label plzNeuMeldung;

    @FXML
    private Label ortNeuMeldung;

    @FXML
    private Label bundeslandNeuMeldung;

    private static final String CONFIRMATION_TEXT = "Soll das Fenster geschlossen werden? Ihre Änderungen werden verworfen.";

    private static final String CONFIRMATION_TITLE = "Abbrechen";

    @FXML
    private TextField kundenIdTf;

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

        if (firmennamenTf.getText().isEmpty()
                & vornameTf.getText().isEmpty()
                & nachnameTf.getText().isEmpty()
                & straßeTf.getText().isEmpty()
                & hausnummerTf.getText().isEmpty()
                & plzTf.getText().isEmpty()
                & ortTf.getText().isEmpty()
                & telefonnummerTf.getText().isEmpty()
                & emailTf.getText().isEmpty()
                & (anredeCB.getValue() == null)
                & (landCB.getValue() == "Deutschland")
                & (bundeslandCB.getValue() == null)
                & telefonnummerTf.getText().isEmpty()
                & emailTf.getText().isEmpty()
                & kontoinhaberTf.getText().isEmpty()
                & ibanTf.getText().isEmpty()
                & bicTf.getText().isEmpty()
                & bankTf.getText().isEmpty()
                & rechnungsadresseRb.isSelected()) {

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
        kundenIdTf.textProperty().set(kundenId);
        hausnummerTf.setTextFormatter(new TextFormatter<>(new FilterHausnummer()));
        hausnummerTfNeu.setTextFormatter(new TextFormatter<>(new FilterHausnummer()));
        plzTf.setTextFormatter(new TextFormatter<>(new FilterPLZ()));
        plzTfNeu.setTextFormatter(new TextFormatter<>(new FilterPLZ()));
        ortTf.setTextFormatter(new TextFormatter<>(new FilterOrt()));
        ortTfNeu.setTextFormatter(new TextFormatter<>(new FilterOrt()));
        telefonnummerTf.setTextFormatter(new TextFormatter<>(new FilterTelefon()));
        ibanTf.setTextFormatter(new TextFormatter<>(new FilterAlphaNumeric()));
        bicTf.setTextFormatter(new TextFormatter<>(new FilterAlphaNumeric()));

        /* Items der Combobox "Anrede" setzen. */
        ObservableList<String> listAnrede = FXCollections.observableArrayList("", "Frau", "Herr");
        anredeCB.setItems(listAnrede);

        /* Items der Combobox "Land" */
        ObservableList<String> listLänder = FXCollections.observableArrayList("Deutschland", "Österreich");
        landCB.setItems(listLänder);
        landCB.setValue("Deutschland");

        /* Items der Combobox "Bundesland" */
        ObservableList<String> listBundesLänderDeutsch = FXCollections.observableArrayList("Baden-Würtemberg", "Bayern", "Berlin", "Brandenburg", "Bremen", "Hamburg", "Hessen", "Mecklenburg-Vorpommern", "Niedersachsen", "Nordrhein-Westfalen", "Rheinland-Pfalz", "Saarland", "Sachsen", "Sachsen-Anhalt", "Schleswig-Holstein", "Thüringen");
        ObservableList<String> listBundesLänderÖsterreich = FXCollections.observableArrayList("Burgenland", "Kärnten", "Nieder-österreich", "Ober-österreich", "Salzburg", "Steiermark", "Tirol", "Vorarlberg", "Wien");
        bundeslandCB.setItems(listBundesLänderDeutsch);

        /* Listener zur Anzeige der Bunsdesländer abhängig vom gewählten Land */
        landCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (landCB.getValue() == "Deutschland") {
                    bundeslandCB.setItems(listBundesLänderDeutsch);
                } else {
                    bundeslandCB.setItems(listBundesLänderÖsterreich);
                }
            }
        });

        /* Funktionen der Checkbox zum Übernehmen des Kontoinhabers*/
        kontoCb.setOnAction(e -> {
            if (kontoCb.isSelected()) {
                kontoinhaberTf.textProperty().bind(firmennamenTf.textProperty());
                kontoinhaberTf.setDisable(true);

            } else {

                kontoinhaberTf.setDisable(false);
                kontoinhaberTf.textProperty().unbind();
                kontoinhaberTf.selectAll();
                kontoinhaberTf.requestFocus();
                kontoinhaberTf.setEditable(true);

            }
        });

        /* Blendet abweichende Lieferanschrift ein */
        neueLieferanschriftRb.setOnAction(e -> {
            if (neueLieferanschriftRb.isSelected()) {

                firmennameLabelNeu.setVisible(true);
                straßeLabelNeu.setVisible(true);
                ortLabelNeu.setVisible(true);
                landLabelNeu.setVisible(true);
                bundeslandLabelNeu.setVisible(true);
                straßeTfNeu.setVisible(true);
                firmennamenTfNeu.setVisible(true);
                firmennamenTfNeu.requestFocus();
                hausnummerTfNeu.setVisible(true);
                plzTfNeu.setVisible(true);
                ortTfNeu.setVisible(true);
                landCb1.setVisible(true);
                bundeslandCb1.setVisible(true);

                scrollpaneKd.setPrefHeight(1100);
                anchorpaneKd.setPrefHeight(1100);

                speichern.setLayoutY(1050);
                abbrechen.setLayoutY(1050);

                landCb1.setItems(listLänder);
                landCb1.setValue("Deutschland");

                bundeslandCb1.setItems(listBundesLänderDeutsch);

                landCb1.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
                        if (landCb1.getValue() == "Deutschland") {
                            bundeslandCb1.setItems(listBundesLänderDeutsch);
                        } else {
                            bundeslandCb1.setItems(listBundesLänderÖsterreich);
                        }
                    }
                });

            }
        });

        /* Übernimmt die Rechnungsadresse und deaktiviert die Felder 
        für die abweichende Adresse */
        rechnungsadresseRb.setOnAction(e -> {
            if (rechnungsadresseRb.isSelected()) {

                firmennameLabelNeu.setVisible(false);
                straßeLabelNeu.setVisible(false);
                ortLabelNeu.setVisible(false);
                landLabelNeu.setVisible(false);
                bundeslandLabelNeu.setVisible(false);
                straßeTfNeu.setVisible(false);
                firmennamenTfNeu.setVisible(false);
                hausnummerTfNeu.setVisible(false);
                plzTfNeu.setVisible(false);
                ortTfNeu.setVisible(false);
                landCb1.setVisible(false);
                bundeslandCb1.setVisible(false);
                bundeslandNeuMeldung.setText("");
                ortNeuMeldung.setText("");
                plzNeuMeldung.setText("");
                hausnummerNeuMeldung.setText("");
                straßeNeuMeldung.setText("");
                firmennameNeuMeldung.setText("");

                scrollpaneKd.setPrefHeight(900);
                anchorpaneKd.setPrefHeight(900);

                abbrechen.setLayoutY(847);
                speichern.setLayoutY(847);

            }
        });

        speichern.setOnAction((ActionEvent event) -> {
            boolean formIsValid = validateForm();
            Alert meldung = new Alert(Alert.AlertType.INFORMATION, INFORMATION_TEXT, ButtonType.OK);

            meldung.setHeaderText("");
//            meldung.setTitle(CONFIRMATION_TITLE);

            if (firmennamenTf.getText().isEmpty() || straßeTf.getText().isEmpty() || hausnummerTf.getText().isEmpty() || plzTf.getText().isEmpty() || ortTf.getText().isEmpty() || (bundeslandCB.getValue() == null) || telefonnummerTf.getText().isEmpty() || neueLieferanschriftRb.isSelected()) {

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

    // Kontroliert das Formular auf Vollständigkeit und Korrektheit.
    private boolean validateForm() {

        boolean validate = true;

        if (neueLieferanschriftRb.isSelected()) {

            if (bundeslandCb1.getValue() == null) {

                bundeslandNeuMeldung.setText(FEHLER_KEIN_BUNDESLAND);
                bundeslandCb1.requestFocus();

                validate = false;

            } else {
                bundeslandNeuMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
            if (ortTfNeu.getText().isEmpty()) {

                ortNeuMeldung.setText(FEHLER_KEIN_ORT);
                ortTfNeu.requestFocus();

                validate = false;

            } else {
                ortNeuMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
            if (plzTfNeu.getText().isEmpty()) {

                plzNeuMeldung.setText(FEHLER_KEINE_PLZ);
                plzTfNeu.requestFocus();

                validate = false;

            } else {
                if (!plzTfNeu.getText().matches("[0-9]{5}")) {

                    plzNeuMeldung.setText(FEHLER_KEINE_GÜLTIGE_PLZ);

                    validate = false;
                } else {

                    plzNeuMeldung.setText("");

                    if (validate != false) {

                        validate = true;
                    }
                }
            }
            if (hausnummerTfNeu.getText().isEmpty()) {

                hausnummerNeuMeldung.setText(FEHLER_KEINE_HAUSNUMMER);
                hausnummerTfNeu.requestFocus();

                validate = false;

            } else {
                hausnummerNeuMeldung.setText("");

                if (validate != false) {

                    validate = true;

                }
            }
            if (straßeTfNeu.getText().isEmpty()) {

                straßeNeuMeldung.setText(FEHLER_KEINE_STRASSE);
                straßeTfNeu.requestFocus();

                validate = false;

            } else {
                straßeNeuMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
            if (firmennamenTfNeu.getText().isEmpty()) {

                firmennameNeuMeldung.setText(FEHLER_KEIN_FIRMENNAME);
                firmennamenTfNeu.requestFocus();

                validate = false;

            } else {

                firmennameNeuMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
        }

        if (rechnungsadresseRb.isSelected()) {

            bundeslandNeuMeldung.setText("");
            ortNeuMeldung.setText("");
            plzNeuMeldung.setText("");
            hausnummerNeuMeldung.setText("");
            straßeNeuMeldung.setText("");
            firmennameNeuMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (bicTf.getText().isEmpty() || bicTf.getText().matches("([A-Z]{4}DE[A-Z2-9]{1}[A-NP-Z0-2]{1}\\w{3})|([A-Z]{4}DE[A-Z2-9]{1}[A-NP-Z0-2]{1})") || bicTf.getText().matches("([A-Z]{4}AT[A-Z2-9]{1}[A-NP-Z0-2]{1}\\w{3})|([A-Z]{4}DE[A-Z2-9]{1}[A-NP-Z0-2]{1})")) {
            bicMeldung.setText("");
            bicTf.requestFocus();

            if (validate != false) {

                validate = true;

            }

        } else {
            bicMeldung.setText(FEHLER_KEINE_GÜLTIGE_BIC);

            validate = false;
        }

        if (ibanTf.getText().isEmpty() || ibanTf.getText().matches("DE[0-9]{20}") || ibanTf.getText().matches("AT[0-9]{18}")) {
            ibanMeldung.setText("");
            ibanTf.requestFocus();

            if (validate != false) {

                validate = true;

            }

        } else {
            ibanMeldung.setText(FEHLER_KEINE_GÜLTIGE_IBAN);

            validate = false;
        }

        if (emailTf.getText().isEmpty() || emailTf.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+" + "[.]{1}" + "[a-zA-Z]{2,3}")) {
            emailMeldung.setText("");
            emailTf.requestFocus();

            if (validate != false) {

                validate = true;
            }
        } else {
            emailMeldung.setText(FEHLER_KEINE_GÜLTIGE_EMAIL);

            validate = false;
        }

        if (telefonnummerTf.getText().isEmpty()) {

            telefonnummerMeldung.setText(FEHLER_KEINE_TELEFONNUMMER);
            telefonnummerTf.requestFocus();

            validate = false;

        } else {
            if (!telefonnummerTf.getText().matches("[0+]{1}[0-9-+ /]+")) {

                telefonnummerMeldung.setText(FEHLER_KEINE_GÜLTIGE_TELEFONNUMMER);

                validate = false;

            } else {
                telefonnummerMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
        }

        if (bundeslandCB.getValue() == null) {

            bundeslandMeldung.setText(FEHLER_KEIN_BUNDESLAND);
            bundeslandCB.requestFocus();

            validate = false;

        } else {
            bundeslandMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (ortTf.getText().isEmpty()) {

            ortMeldung.setText(FEHLER_KEIN_ORT);
            ortTf.requestFocus();

            validate = false;

        } else {
            ortMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (plzTf.getText().isEmpty()) {

            plzMeldung.setText(FEHLER_KEINE_PLZ);
            plzTf.requestFocus();

            validate = false;

        } else {
            if (!plzTf.getText().matches("[0-9]{5}")) {

                plzMeldung.setText(FEHLER_KEINE_GÜLTIGE_PLZ);

                validate = false;
            } else {

                plzMeldung.setText("");

                if (validate != false) {

                    validate = true;
                }
            }
        }

        if (hausnummerTf.getText().isEmpty()) {

            hausnummerMeldung.setText(FEHLER_KEINE_HAUSNUMMER);
            hausnummerTf.requestFocus();

            validate = false;

        } else {
            hausnummerMeldung.setText("");

            if (validate != false) {

                validate = true;

            }
        }

        if (straßeTf.getText().isEmpty()) {

            straßeMeldung.setText(FEHLER_KEINE_STRASSE);
            straßeTf.requestFocus();

            validate = false;

        } else {
            straßeMeldung.setText("");

            if (validate != false) {

                validate = true;
            }
        }

        if (firmennamenTf.getText().isEmpty()) {

            firmennameMeldung.setText(FEHLER_KEIN_FIRMENNAME);
            firmennamenTf.requestFocus();

            validate = false;

        } else {

            firmennameMeldung.setText("");

            if (validate != false) {

                validate = true;
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
     * Legt das aktive Fenster fest
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
