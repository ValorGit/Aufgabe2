/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe02;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Jürgen Christl
 */
public class Meldung {
    
    private static final String EXIT_TITLE = "Fenster schließen";
    private static final String EXIT_TEXT = "Soll das Fenster geschlossen werden?";
    
    protected boolean showExitPlatformAlert() {
//      Alert meldung = new Alert(Alert.AlertType.CONFIRMATION);
        Alert meldung = new Alert(Alert.AlertType.CONFIRMATION, EXIT_TEXT, ButtonType.YES, ButtonType.NO);
        /* Rückgabewert */
        boolean confirm = false;
        /* Aenderung der Modalitaet auf eine "nicht-modale" Meldung. */
//        meldung.initModality(Modality.NONE);
        /* "Entfernt" den Header und setzt das Symbol links vom Inhalt. */
        meldung.setHeaderText("");
        meldung.setTitle(EXIT_TITLE);
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
    
}
