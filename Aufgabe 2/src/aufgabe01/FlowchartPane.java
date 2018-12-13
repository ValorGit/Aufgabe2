/**
 * @Author Jürgen Christl
 */
package aufgabe01;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Diese Klasse repraesentiert die Pane fuer eine Flowchart.
 */
public class FlowchartPane extends AnchorPane {

    // Scene
    private Scene scene;

    // Breite und Höhe der Scene
    private float width = 1500;
    private float height = 900;

    /**
     * AnchorPane
     */
    public FlowchartPane() {
        super();
        scene = new Scene(this, width, height);

        this.getChildren().add(oberflaeche());
        this.getChildren().add(elemente());
        this.getChildren().add(createButton());
        this.getChildren().add(header());

        this.setOnMouseClicked(onKlick);
    }

    /**
     * Legt die Scene fest
     *
     * @param scene ist die Scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Legt die Breite der Scene fest
     *
     * @param width legt die Breite fest
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Legt die Höhe der Scene fest
     *
     * @param height legt die Höhe fest
     */
    public void setHeight(float height) {
        this.height = height;
    }

    //Elemente
    private Rectangle startEnde;
    private Arrow pfeil;
    private Polygon daten;
    private Rectangle prozess;
    private Polygon entscheidung;
    private Rectangle elementFlaeche;

    // Werte für Drag & Drop Methode
    private double initX;
    private double initY;
    private Point2D dragAnchor;

    //Standardwerte für Fonts und Rahmen
    private final Font standardFont = new Font("Georgia", 14.0);
    private final double standardStrokeWidth = 3.0;
    private final double selectedStrokeWidth = standardStrokeWidth * 2;

    //IDs der Elemente
    private static final String STARTENDE_ID = "StartEnde";
    private static final String PROZESS_ID = "Prozess";
    private static final String DATEN_ID = "Daten";
    private static final String ARROW_ID = "Arrow";
    private static final String ENTSCHEIDUNG_ID = "Entscheidung";
    private static final String RECTANGLE_NEW_ID = "Rectangle new";
    private static final String POLYGON_NEW_ID = "Polygon new";
    private static final String ARROW_NEW_ID = "Arrow new";

    //Wird zum Markieren von Elementen in der Diagrammfläche verwendet
    private Node marked = null;

    /**
     * Beinhaltet die Überschrift
     *
     * @return liefert das Label und Textfeld für die Überschrift
     */
    public Group header() {

        TextField eingabeFeld = new TextField();
        eingabeFeld.setLayoutX(375);
        eingabeFeld.setLayoutY(45);
        eingabeFeld.setPrefSize(750, 30);
        eingabeFeld.setFont(new Font("Georgia", 16.0));

        Label prozessname = new Label("Prozessname:");
        prozessname.setLayoutX(250);
        prozessname.setLayoutY(52);
        prozessname.setFont(new Font("Georgia", 16.0));

        Group g = new Group();
        g.getChildren().addAll(eingabeFeld, prozessname);

        return g;
    }

    /**
     * Erstellt die Elemente-Fläche und die Diagrammfläche und beschriftet
     * diese.
     *
     * @return liefert die Elemente- und Diagrammfläche.
     */
    public Group oberflaeche() {

        elementFlaeche = new Rectangle(15, 110, 250, 775);
        elementFlaeche.setFill(Color.WHITE);
        elementFlaeche.setStroke(Color.BLACK);
        elementFlaeche.setStrokeWidth(5);

        Label elemente = new Label("Elemente");
        elemente.setFont(new Font("Georgia", 16.0));
        elemente.setLayoutX(35);
        elemente.setLayoutY(120);

        Rectangle elementLabel = new Rectangle(17, 112, 105, 40);
        elementLabel.setFill(Color.WHITE);
        elementLabel.setStroke(Color.BLACK);
        elementLabel.setStrokeWidth(1);

        Rectangle diagrammFlaeche = new Rectangle(375, 110, 1100, 775);
        diagrammFlaeche.setFill(Color.WHITE);
        diagrammFlaeche.setStroke(Color.BLACK);
        diagrammFlaeche.setStrokeWidth(5);

        Rectangle diagrammLabel = new Rectangle(377, 112, 105, 40);
        diagrammLabel.setFill(Color.WHITE);
        diagrammLabel.setStroke(Color.BLACK);
        diagrammLabel.setStrokeWidth(1);

        Label diagramm = new Label("Diagramm");
        diagramm.setFont(new Font("Georgia", 16.0));
        diagramm.setLayoutX(392);
        diagramm.setLayoutY(120);

        Group g = new Group();
        g.getChildren().addAll(
                elementFlaeche, elementLabel, elemente,
                diagrammFlaeche, diagrammLabel, diagramm);

        return g;
    }

    /**
     * Beinhaltet den hinzufüge-Button und den lösch-Button, um Elemente im
     * Diagrammfeld hinzuzufügen oder zu löschen, die neuen zu erstellenden
     * Elemente und Drag&Drop
     *
     * @return liefert die Buttons und ihre Funktionen
     */
    public Group createButton() {

        //erstellt den "+"-Button
        Button bPlus = new Button("+");
        bPlus.setFont(standardFont);
        bPlus.setLayoutX(295);
        bPlus.setLayoutY(330);
        bPlus.setPrefSize(50, 50);

        //erstellt den "-"-Button
        Button bMinus = new Button("-");
        bMinus.setFont(standardFont);
        bMinus.setLayoutX(295);
        bMinus.setLayoutY(420);
        bMinus.setPrefSize(50, 50);

        EventHandler<ActionEvent> bKlick = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                //der "+"-Button wird gedrückt
                if (event.getSource() == bPlus) {

                    if (startEnde.getStrokeWidth() == selectedStrokeWidth) {

                        //erstellt ein neues Rechteck "Start/Ende"
                        Rectangle se = new Rectangle(
                                startEnde.getX() + 350,
                                startEnde.getY(),
                                startEnde.getWidth(),
                                startEnde.getHeight());
                        se.setFill(startEnde.getFill());
                        se.setStroke(startEnde.getStroke());
                        se.setStrokeWidth(standardStrokeWidth);
                        se.setArcHeight(startEnde.getArcHeight());
                        se.setArcWidth(startEnde.getArcWidth());
                        se.setId(RECTANGLE_NEW_ID);
                        se.setOnMouseClicked(select);
                        se.setOnMousePressed(new EventHandler<MouseEvent>() {

                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {

                                    initX = se.getTranslateX();
                                    initY = se.getTranslateY();
                                    dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
                                }
                            }
                        });
                        se.setOnMouseDragged((MouseEvent drag) -> {

                            startEnde.setStrokeWidth(standardStrokeWidth);
                            prozess.setStrokeWidth(standardStrokeWidth);
                            daten.setStrokeWidth(standardStrokeWidth);
                            pfeil.setStrokeWidth(standardStrokeWidth);
                            entscheidung.setStrokeWidth(standardStrokeWidth);

                            if (drag.getButton() == MouseButton.PRIMARY) {

                                se.requestFocus();
                                double startDragX = drag.getSceneX() - dragAnchor.getX();
                                double startDragY = drag.getSceneY() - dragAnchor.getY();
                                double newXPosition = initX + startDragX;
                                double newYPosition = initY + startDragY;

                                se.setTranslateX(newXPosition);
                                se.setTranslateY(newYPosition);
                                drag.consume();
                            }
                        });
                        se.focusedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                                if (newPropertyValue) {
                                    se.setStrokeWidth(selectedStrokeWidth);
                                } else {
                                    se.setStrokeWidth(standardStrokeWidth);
                                }
                            }
                        });
                        getChildren().add(se);
                        startEnde.setStrokeWidth(standardStrokeWidth);
                        prozess.setStrokeWidth(standardStrokeWidth);
                        daten.setStrokeWidth(standardStrokeWidth);
                        pfeil.setStrokeWidth(standardStrokeWidth);
                        entscheidung.setStrokeWidth(standardStrokeWidth);
                        event.consume();

                    } else if (pfeil.getStrokeWidth() == selectedStrokeWidth) {

                        //erstellt einen neuen Pfeil
                        Arrow a = new Arrow();
                        a.setStartX(pfeil.getStartX() + 350);
                        a.setEndX(pfeil.getEndX() + 350);
                        a.setStartY(pfeil.getStartY());
                        a.setEndY(pfeil.getEndY());
                        a.setId(ARROW_NEW_ID);
                        a.setOnMouseClicked(select);
                        a.setOnMousePressed(new EventHandler<MouseEvent>() {

                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {

                                    initX = a.getTranslateX();
                                    initY = a.getTranslateY();
                                    dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
                                }
                            }
                        });
                        a.setOnMouseDragged((MouseEvent drag) -> {

                            a.requestFocus();
                            startEnde.setStrokeWidth(standardStrokeWidth);
                            prozess.setStrokeWidth(standardStrokeWidth);
                            daten.setStrokeWidth(standardStrokeWidth);
                            pfeil.setStrokeWidth(standardStrokeWidth);
                            entscheidung.setStrokeWidth(standardStrokeWidth);

                            if (drag.getButton() == MouseButton.PRIMARY) {

                                a.requestFocus();
                                double startDragX = drag.getSceneX() - dragAnchor.getX();
                                double startDragY = drag.getSceneY() - dragAnchor.getY();
                                double newXPosition = initX + startDragX;
                                double newYPosition = initY + startDragY;

                                a.setTranslateX(newXPosition);
                                a.setTranslateY(newYPosition);
                                drag.consume();
                            } else if (drag.getButton() == MouseButton.SECONDARY) {
                                a.setStrokeWidth(selectedStrokeWidth);
                                a.setEndX(drag.getX());
                                a.setEndY(drag.getY());
                                drag.consume();
                            }
                        });
                        a.focusedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                                if (newPropertyValue) {
                                    a.setStrokeWidth(selectedStrokeWidth);
                                } else {
                                    a.setStrokeWidth(standardStrokeWidth);
                                }
                            }
                        });
                        startEnde.setStrokeWidth(standardStrokeWidth);
                        prozess.setStrokeWidth(standardStrokeWidth);
                        daten.setStrokeWidth(standardStrokeWidth);
                        pfeil.setStrokeWidth(standardStrokeWidth);
                        entscheidung.setStrokeWidth(standardStrokeWidth);

                        getChildren().add(a);
                        event.consume();

                    } else if (daten.getStrokeWidth() == selectedStrokeWidth) {

                        // Erstellt ein neues Polygon "Daten"
                        Polygon d = new Polygon();
                        d.getPoints().addAll(daten.getPoints());
                        d.setLayoutX(350);
                        d.setFill(daten.getFill());
                        d.setStroke(daten.getStroke());
                        d.setStrokeWidth(standardStrokeWidth);
                        d.setId(POLYGON_NEW_ID);
                        d.setOnMouseClicked(select);
                        d.setOnMousePressed(new EventHandler<MouseEvent>() {

                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {

                                    initX = d.getTranslateX();
                                    initY = d.getTranslateY();
                                    dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
                                }
                            }
                        });
                        d.setOnMouseDragged((MouseEvent drag) -> {

                            startEnde.setStrokeWidth(standardStrokeWidth);
                            prozess.setStrokeWidth(standardStrokeWidth);
                            daten.setStrokeWidth(standardStrokeWidth);
                            pfeil.setStrokeWidth(standardStrokeWidth);
                            entscheidung.setStrokeWidth(standardStrokeWidth);

                            if (drag.getButton() == MouseButton.PRIMARY) {

                                d.requestFocus();
                                double startDragX = drag.getSceneX() - dragAnchor.getX();
                                double startDragY = drag.getSceneY() - dragAnchor.getY();
                                double newXPosition = initX + startDragX;
                                double newYPosition = initY + startDragY;

                                d.setTranslateX(newXPosition);
                                d.setTranslateY(newYPosition);
                                drag.consume();
                            }
                        });
                        d.focusedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                                if (newPropertyValue) {
                                    d.setStrokeWidth(selectedStrokeWidth);
                                } else {
                                    d.setStrokeWidth(standardStrokeWidth);
                                }
                            }
                        });
                        startEnde.setStrokeWidth(standardStrokeWidth);
                        prozess.setStrokeWidth(standardStrokeWidth);
                        daten.setStrokeWidth(standardStrokeWidth);
                        pfeil.setStrokeWidth(standardStrokeWidth);
                        entscheidung.setStrokeWidth(standardStrokeWidth);

                        getChildren().add(d);
                        event.consume();

                    } else if (prozess.getStrokeWidth()
                            == selectedStrokeWidth) {

                        // Erstellt ein neues Rechteck "Prozess"
                        Rectangle p = new Rectangle(
                                prozess.getX() + 350,
                                prozess.getY(),
                                prozess.getWidth(),
                                prozess.getHeight());

                        p.setFill(prozess.getFill());
                        p.setStroke(prozess.getStroke());
                        p.setStrokeWidth(standardStrokeWidth);
                        p.setId(RECTANGLE_NEW_ID);
                        p.setOnMouseClicked(select);
                        p.setOnMousePressed(new EventHandler<MouseEvent>() {

                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {

                                    initX = p.getTranslateX();
                                    initY = p.getTranslateY();
                                    dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
                                }
                            }
                        });
                        p.setOnMouseDragged((MouseEvent drag) -> {

                            startEnde.setStrokeWidth(standardStrokeWidth);
                            prozess.setStrokeWidth(standardStrokeWidth);
                            daten.setStrokeWidth(standardStrokeWidth);
                            pfeil.setStrokeWidth(standardStrokeWidth);
                            entscheidung.setStrokeWidth(standardStrokeWidth);

                            if (drag.getButton() == MouseButton.PRIMARY) {

                                p.requestFocus();
                                double startDragX = drag.getSceneX() - dragAnchor.getX();
                                double startDragY = drag.getSceneY() - dragAnchor.getY();
                                double newXPosition = initX + startDragX;
                                double newYPosition = initY + startDragY;

                                p.setTranslateX(newXPosition);
                                p.setTranslateY(newYPosition);
                                drag.consume();
                            }
                        });
                        p.focusedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                                if (newPropertyValue) {
                                    p.setStrokeWidth(selectedStrokeWidth);
                                } else {
                                    p.setStrokeWidth(standardStrokeWidth);
                                }
                            }
                        });
                        startEnde.setStrokeWidth(standardStrokeWidth);
                        prozess.setStrokeWidth(standardStrokeWidth);
                        daten.setStrokeWidth(standardStrokeWidth);
                        pfeil.setStrokeWidth(standardStrokeWidth);
                        entscheidung.setStrokeWidth(standardStrokeWidth);

                        getChildren().add(p);
                        event.consume();

                    } else if (entscheidung.getStrokeWidth()
                            == selectedStrokeWidth) {

                        // Erstellt ein neues Polygon "Entscheidung"
                        Polygon e = new Polygon();
                        e.getPoints().addAll(entscheidung.getPoints());
                        e.setLayoutX(350);
                        e.setFill(entscheidung.getFill());
                        e.setStroke(entscheidung.getStroke());
                        e.setStrokeWidth(standardStrokeWidth);
                        e.setId(POLYGON_NEW_ID);
                        e.setOnMouseClicked(select);
                        e.setOnMousePressed(new EventHandler<MouseEvent>() {

                            public void handle(MouseEvent event) {
                                if (event.getButton() == MouseButton.PRIMARY) {

                                    initX = e.getTranslateX();
                                    initY = e.getTranslateY();
                                    dragAnchor = new Point2D(event.getSceneX(), event.getSceneY());
                                }
                            }
                        });
                        e.setOnMouseDragged((MouseEvent drag) -> {

                            startEnde.setStrokeWidth(standardStrokeWidth);
                            prozess.setStrokeWidth(standardStrokeWidth);
                            daten.setStrokeWidth(standardStrokeWidth);
                            pfeil.setStrokeWidth(standardStrokeWidth);
                            entscheidung.setStrokeWidth(standardStrokeWidth);

                            if (drag.getButton() == MouseButton.PRIMARY) {

                                e.requestFocus();
                                double startDragX = drag.getSceneX() - dragAnchor.getX();
                                double startDragY = drag.getSceneY() - dragAnchor.getY();
                                double newXPosition = initX + startDragX;
                                double newYPosition = initY + startDragY;

                                e.setTranslateX(newXPosition);
                                e.setTranslateY(newYPosition);
                                drag.consume();
                            }
                        });
                        e.focusedProperty().addListener(new ChangeListener<Boolean>() {

                            @Override
                            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                                if (newPropertyValue) {
                                    e.setStrokeWidth(selectedStrokeWidth);
                                } else {
                                    e.setStrokeWidth(standardStrokeWidth);
                                }
                            }
                        });
                        startEnde.setStrokeWidth(standardStrokeWidth);
                        prozess.setStrokeWidth(standardStrokeWidth);
                        daten.setStrokeWidth(standardStrokeWidth);
                        pfeil.setStrokeWidth(standardStrokeWidth);
                        entscheidung.setStrokeWidth(standardStrokeWidth);

                        getChildren().add(e);
                        event.consume();

                    }

                    //der "-"-Button wird gedrückt
                } else if (event.getSource() == bMinus) {

                    startEnde.setStrokeWidth(standardStrokeWidth);
                    prozess.setStrokeWidth(standardStrokeWidth);
                    daten.setStrokeWidth(standardStrokeWidth);
                    pfeil.setStrokeWidth(standardStrokeWidth);
                    entscheidung.setStrokeWidth(standardStrokeWidth);
                    
                    // Löscht das markierte Element
                    if (marked != null) {
                        getChildren().remove(marked);
                    }
                }
            }
        };

        bPlus.setOnAction(bKlick);
        bMinus.setOnAction(bKlick);

        Group g = new Group();
        g.getChildren().addAll(bPlus, bMinus);

        return g;
    }

    /**
     * Erstellt alle Formen und ihre Label im Elementefeld.
     *
     * @return liefert alle Elemente
     */
    public Group elemente() {

        /*
        Erstellt das Rechteck "Start/Ende" und das dazugehörige Label 
        im Elementefeld.
         */
        startEnde = new Rectangle(65, 175, 150, 75);
        startEnde.setFill(Color.LIGHTGREEN);
        startEnde.setArcHeight(100);
        startEnde.setArcWidth(100);
        startEnde.setStroke(Color.GREEN);
        startEnde.setStrokeWidth(standardStrokeWidth);
        startEnde.setId(STARTENDE_ID);
        Label startEndeLabel = new Label("Start/Ende");
        startEndeLabel.setFont(standardFont);
        startEndeLabel.setLayoutX(110);
        startEndeLabel.setLayoutY(257);

        /*
        Erstellt den Pfeil und das dazugehörige Label im Elementefeld.
         */
        pfeil = new Arrow();
        pfeil.setStartX(65);
        pfeil.setEndX(215);
        pfeil.setStartY(320);
        pfeil.setEndY(320);
        pfeil.setId(ARROW_ID);
        Label pfeilLabel = new Label("Pfeil");
        pfeilLabel.setFont(standardFont);
        pfeilLabel.setLayoutX(120);
        pfeilLabel.setLayoutY(327);

        /*
        Erstellt das Parallelogramm "Daten" und das dazugehörige Label 
        im Elementefeld.
         */
        daten = new Polygon(
                90, 385,
                215, 385,
                190, 460,
                65, 460);
        daten.setFill(Color.LIGHTGREEN);
        daten.setStroke(Color.GREEN);
        daten.setStrokeWidth(standardStrokeWidth);
        daten.setId(DATEN_ID);
        Label datenLabel = new Label("Daten");
        datenLabel.setFont(standardFont);
        datenLabel.setLayoutX(110);
        datenLabel.setLayoutY(467);

        /*
        Erstellt das Rechteck "Prozess" und das dazugehörige Label 
        im Elementefeld.
         */
        prozess = new Rectangle(65, 535, 150, 75);
        prozess.setFill(Color.LIGHTGREEN);
        prozess.setStroke(Color.GREEN);
        prozess.setStrokeWidth(standardStrokeWidth);
        prozess.setId(PROZESS_ID);
        Label prozessLabel = new Label("Prozess");
        prozessLabel.setFont(standardFont);
        prozessLabel.setLayoutX(110);
        prozessLabel.setLayoutY(617);

        /*
        Erstellt die Raute und das dazugehörige Label im Elementefeld.
         */
        entscheidung = new Polygon(
                140, 675,
                215, 715,
                140, 755,
                65, 715);
        entscheidung.setFill(Color.LIGHTGREEN);
        entscheidung.setStroke(Color.GREEN);
        entscheidung.setStrokeWidth(standardStrokeWidth);
        entscheidung.setId(ENTSCHEIDUNG_ID);
        Label entscheidungLabel = new Label("Entscheidung");
        entscheidungLabel.setFont(standardFont);
        entscheidungLabel.setLayoutX(97);
        entscheidungLabel.setLayoutY(762);

        /*
        Markiert das ausgewählte Element im Elemente-Feld.
         */
        EventHandler<MouseEvent> eKlick = (MouseEvent event) -> {

            Node shape1 = (Node) event.getSource();

            if (shape1.getId().equals(STARTENDE_ID)) {
                startEnde.setStrokeWidth(selectedStrokeWidth);
                prozess.setStrokeWidth(standardStrokeWidth);
                daten.setStrokeWidth(standardStrokeWidth);
                pfeil.setStrokeWidth(standardStrokeWidth);
                entscheidung.setStrokeWidth(standardStrokeWidth);
                ((Rectangle) event.getSource()).requestFocus();
                marked = null;
                event.consume();

            } else if (shape1.getId().equals(PROZESS_ID)) {
                startEnde.setStrokeWidth(standardStrokeWidth);
                prozess.setStrokeWidth(selectedStrokeWidth);
                daten.setStrokeWidth(standardStrokeWidth);
                pfeil.setStrokeWidth(standardStrokeWidth);
                entscheidung.setStrokeWidth(standardStrokeWidth);
                ((Rectangle) event.getSource()).requestFocus();
                marked = null;
                event.consume();

            } else if (shape1.getId().equals(ARROW_ID)) {
                startEnde.setStrokeWidth(standardStrokeWidth);
                prozess.setStrokeWidth(standardStrokeWidth);
                daten.setStrokeWidth(standardStrokeWidth);
                pfeil.setStrokeWidth(selectedStrokeWidth);
                entscheidung.setStrokeWidth(standardStrokeWidth);
                ((Arrow) event.getSource()).requestFocus();
                marked = null;
                event.consume();

            } else if (shape1.getId().equals(DATEN_ID)) {
                startEnde.setStrokeWidth(standardStrokeWidth);
                prozess.setStrokeWidth(standardStrokeWidth);
                daten.setStrokeWidth(selectedStrokeWidth);
                pfeil.setStrokeWidth(standardStrokeWidth);
                entscheidung.setStrokeWidth(standardStrokeWidth);
                ((Polygon) event.getSource()).requestFocus();
                marked = null;
                event.consume();

            } else if (shape1.getId().equals(ENTSCHEIDUNG_ID)) {
                startEnde.setStrokeWidth(standardStrokeWidth);
                prozess.setStrokeWidth(standardStrokeWidth);
                daten.setStrokeWidth(standardStrokeWidth);
                pfeil.setStrokeWidth(standardStrokeWidth);
                entscheidung.setStrokeWidth(selectedStrokeWidth);
                ((Polygon) event.getSource()).requestFocus();
                marked = null;
                event.consume();
            }
        };
        startEnde.setOnMouseClicked(eKlick);
        pfeil.setOnMouseClicked(eKlick);
        daten.setOnMouseClicked(eKlick);
        prozess.setOnMouseClicked(eKlick);
        entscheidung.setOnMouseClicked(eKlick);

        Group g = new Group();
        g.getChildren().addAll(
                startEnde, startEndeLabel,
                pfeil, pfeilLabel,
                daten, datenLabel,
                prozess, prozessLabel,
                entscheidung, entscheidungLabel);

        return g;
    }
    /**
     * Merkt sich das letzte ausgewählte Element(Im Diagrammfeld). Wird für den
     * MinusButton benötigt.
     */
    EventHandler<MouseEvent> select = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            Node shape = (Node) event.getSource();
            
            if (shape.getId().equals(RECTANGLE_NEW_ID)
                    || shape.getId().equals(POLYGON_NEW_ID)
                    || shape.getId().equals(ARROW_NEW_ID)) {

                startEnde.setStrokeWidth(standardStrokeWidth);
                prozess.setStrokeWidth(standardStrokeWidth);
                daten.setStrokeWidth(standardStrokeWidth);
                pfeil.setStrokeWidth(standardStrokeWidth);
                entscheidung.setStrokeWidth(standardStrokeWidth);

                shape.requestFocus();
                marked = shape;
                event.consume();

            } 
        }
    };

    /**
     * Setzt die StrokeWidth der Elemente auf standard zurück
     */
    EventHandler<MouseEvent> onKlick = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            startEnde.setStrokeWidth(standardStrokeWidth);
            prozess.setStrokeWidth(standardStrokeWidth);
            daten.setStrokeWidth(standardStrokeWidth);
            pfeil.setStrokeWidth(standardStrokeWidth);
            entscheidung.setStrokeWidth(standardStrokeWidth);
            marked = null;
            elementFlaeche.requestFocus();

        }
    };
}
