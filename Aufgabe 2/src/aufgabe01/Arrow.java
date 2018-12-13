/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe01;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * Ein Objekt dieser Klasse repraesentiert einen Pfeil.
 * Adaptiert von: https://stackoverflow.com/questions/41353685/how-to-draw-arrow-javafx-pane
 */
public class Arrow extends Group {

    private final Line line;
    private final Line arrowHead1;
    private final Line arrowHead2;
    private static final Paint DEFAULT_STROKE_COLOR = Color.DARKGREEN;
    private static final double DEFAULT_STROKE_WIDTH = 3.0;
    private static final double arrowHeadLength = 15;
    private static final double arrowHeadWidth = 5;

    /**
     * Erstellt einen Pfeil im Ursprung 0,0.
     */
    public Arrow() {
        this(new Line(0, 0, 90, 0), new Line(80, -10, 90, 0), new Line(80, 10, 90, 0));
    }

    /**
     * Erstellt einen Pfeil anhand von drei zu uebergebenden Linien.
     * @param line 
     * @param arrow1
     * @param arrow2 
     */
    public Arrow(Line line, Line arrow1, Line arrow2) {
        super(line, arrow1, arrow2);

        this.line = line;
        this.arrowHead1 = arrow1;
        this.arrowHead2 = arrow2;

        this.setStrokeWidth(DEFAULT_STROKE_WIDTH);
        this.setStroke(DEFAULT_STROKE_COLOR);

        InvalidationListener updater = o -> {
            double endX = getEndX();
            double endY = getEndY();
            double startX = getStartX();
            double startY = getStartY();

            this.arrowHead1.setEndX(endX);
            this.arrowHead1.setEndY(endY);
            this.arrowHead2.setEndX(endX);
            this.arrowHead2.setEndY(endY);
            if (endX == startX && endY == startY) {
                // arrow parts of length 0
                this.arrowHead1.setStartX(endX);
                this.arrowHead1.setStartY(endY);
                this.arrowHead2.setStartX(endX);
                this.arrowHead2.setStartY(endY);
            } else {
                double factor = arrowHeadLength / Math.hypot(startX - endX, startY - endY);
                double factorO = arrowHeadWidth / Math.hypot(startX - endX, startY - endY);

                // entpsrechend der Linie des Pfeils
                double directionX = (startX - endX) * factor;
                double directionY = (startY - endY) * factor;

                // ortogonal zu der Linie des Pfeils
                double ortogonalX = (startX - endX) * factorO;
                double ortogonalY = (startY - endY) * factorO;

                this.arrowHead1.setStartX(endX + directionX - ortogonalY);
                this.arrowHead1.setStartY(endY + directionY + ortogonalX);
                this.arrowHead2.setStartX(endX + directionX + ortogonalY);
                this.arrowHead2.setStartY(endY + directionY - ortogonalX);
            }
        };
        // Listener den x und y Porperties hinzufuegen
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    public final void setStartX(double value) {
        line.setStartX(value);
    }

    public final double getStartX() {
        return line.getStartX();
    }

    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public final void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public final void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }

    public final void setStroke(Paint value) {
        line.setStroke(value);
        arrowHead1.setStroke(value);
        arrowHead2.setStroke(value);

    }

    public final void setStrokeWidth(double value) {
        line.setStrokeWidth(value);
        arrowHead1.setStrokeWidth(value);
        arrowHead2.setStrokeWidth(value);
    }

    public final Paint getStroke() {
        return line.getStroke();
    }
    
    public final double getStrokeWidth() {
        return line.getStrokeWidth();
    }

}