/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aufgabe01;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;

/**
 * 
 */
public class Aufgabe01 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        FlowchartPane root = new FlowchartPane();
        
       
        
        primaryStage.setTitle("Flowchart Tool");
        primaryStage.setScene(root.getScene());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
