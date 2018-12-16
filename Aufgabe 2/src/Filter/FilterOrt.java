/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filter;

import java.awt.Toolkit;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author kb
 */
public class FilterOrt implements UnaryOperator<TextFormatter.Change> {
    
    /* Nur ein sehr einfacher Namensfilter, der auch ungültige 
     * Zeichenkombinationen zulässt */
//    public static final String REGEX = "[\\p{IsAlphabetic}0-9 \\/ \'.-]*";
//    public static final String REGEX = "[\\p{IsAlphabetic}[0-9]*\\p{IsAlphabetic}*.?\\p{IsAlphabetic}*[0-9]*]*|[\\p{IsAlphabetic}[0-9]*\\p{IsAlphabetic}*\\s?[\\/ ' -]\\s?\\p{IsAlphabetic}*.?\\p{IsAlphabetic}+[0-9]*]*";
    public static final String REGEX = "\\s*|([\\p{IsAlphabetic}0-9]+[-\'./]?[\\p{IsAlphabetic}0-9]*)*";
    public static final String REGEX_EINGABE = "[\\p{IsAlphabetic}0-9 \\/ \'.-]*";
    
    @Override
    public TextFormatter.Change apply(TextFormatter.Change tfc) {
        String neu = tfc.getControlNewText();
        
        if (!neu.matches(REGEX)) {
            Toolkit.getDefaultToolkit().beep();
            return null;
        }
        return tfc;
    }
}