/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filter;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Alex
 */
public class DataValidation {
    
 
public static boolean textFieldIsNull(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isNull = false;
        String validationString = null;

        System.out.println("*******************************************************");

        //point out difference between null and isEmpty() *FIND OUT WHEN TO USE NULL
        if (inputTextField.getText().isEmpty()) {
            isNull = true;
            validationString = validationText;

        }
        

        inputLabel.setText(validationString);

        
        return isNull;
    
}    
     
public static boolean artikelnummerFormat(TextField inputTextField, Label inputLabel, String validationText) {
        boolean isZID = true;
        String validationString = null;

        if (!inputTextField.getText().matches("[0-9]{3}" + "-?" + "[0-9]{4}" + "-?" + "[0-9]{3}")) {
            isZID = false;
            validationString = validationText;

        }
        inputLabel.setText(validationString);

        return isZID;

    } 



}
