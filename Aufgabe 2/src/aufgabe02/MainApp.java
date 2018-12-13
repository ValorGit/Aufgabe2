package aufgabe02;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    //legt die Stage für das Hauptfenster fest
    private Stage primaryStage;

    private final Meldung meldung = new Meldung();

    /**
     * Öffnet das Fenster "Prozessvisualisierung"
     */
    public void zeigeProzessvisualisierungDialog() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ProzessvisualisierungDocument.fxml"));
            AnchorPane ProzessvisualisierungView = (AnchorPane) loader.load();
            Stage ProzessStage = new Stage();
            ProzessStage.setTitle("Prozessvisualisierung");
            ProzessStage.initOwner(primaryStage);
            ProzessStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(ProzessvisualisierungView);
            ProzessStage.setScene(scene);

            ProzessStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Öffnet das Fenster "Artikel anlegen"
     */
    public void zeigeArtikelAnlegenDialog() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("ArtikelAnlegen.fxml"));
            ScrollPane ArtikelAnlegenView = (ScrollPane) loader.load();
            Stage ArtikelAnlegenStage = new Stage();
            ArtikelAnlegenController controller = loader.getController();
            controller.setStage(ArtikelAnlegenStage);
            ArtikelAnlegenStage.setTitle("Artikel anlegen");
            ArtikelAnlegenStage.initOwner(primaryStage);
            ArtikelAnlegenStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(ArtikelAnlegenView);
            ArtikelAnlegenStage.setScene(scene);
            ArtikelAnlegenStage.setOnCloseRequest((event) -> {
                controller.handleCloseButtonAction();
                event.consume();
            });

            ArtikelAnlegenStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Öffnet das Fenster "Auftrag anlegen"
     */
    public void zeigeAuftragAnlegenDialog() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader
                    .setLocation(MainApp.class
                            .getResource("AuftragAnlegen.fxml"));
            ScrollPane AuftragAnlegenView = (ScrollPane) loader.load();
            Stage AuftragAnlegenStage = new Stage();
            AuftragAnlegenController controller = loader.getController();
            controller.setStage(AuftragAnlegenStage);
            AuftragAnlegenStage.setTitle("Auftrag anlegen");
            AuftragAnlegenStage.initOwner(primaryStage);
            AuftragAnlegenStage.initModality(Modality.WINDOW_MODAL);
            
            Scene scene = new Scene(AuftragAnlegenView);
            AuftragAnlegenStage.setScene(scene);
            AuftragAnlegenStage.setOnCloseRequest((event) -> {
                controller.handleCloseButtonAction();
                event.consume();
            });
            AuftragAnlegenStage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Öffnet das Fenster "Kunden anlegen"
     */
    public void zeigeKundenAnlegenDialog() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader
                    .setLocation(MainApp.class
                            .getResource("KundenAnlegen.fxml"));
            ScrollPane KundenAnlegenView = (ScrollPane) loader.load();
            Stage KundenAnlegenStage = new Stage();
            KundenAnlegenController controller = loader.getController();
            controller.setStage(KundenAnlegenStage);
            KundenAnlegenStage.setTitle("Kunden anlegen");
            KundenAnlegenStage.initOwner(primaryStage);
            KundenAnlegenStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(KundenAnlegenView);
            KundenAnlegenStage.setScene(scene);
            KundenAnlegenStage.setOnCloseRequest((event) -> {
                controller.handleCloseButtonAction();
                event.consume();
            });

            KundenAnlegenStage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MainApp.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Hauptfenster");
        FXMLLoader loader = new FXMLLoader();
        loader
                .setLocation(MainApp.class
                        .getResource("Hauptfenster.fxml"));
        Parent root = loader.load();

        HauptfensterController controller = loader.getController();

        controller.setMainApp(this);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
