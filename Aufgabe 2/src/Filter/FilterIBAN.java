/*/*
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
 * @author Alexander Dünne, Jürgen Christl
 */
public class FilterIBAN implements UnaryOperator<TextFormatter.Change> {
    
   
//    public static final String REGEX = "-?\\d+(" + SEPERATOR + "\\d+)?";
    public static final String REGEX_EINGABE = "[A-Z]{1,2}[0-9]{0,18}";
    
    @Override
    public TextFormatter.Change apply(TextFormatter.Change tfc) {
        String neu = tfc.getControlNewText();
        
        if (neu.matches(REGEX_EINGABE) | neu.isEmpty()) {
            return tfc;
        }
            Toolkit.getDefaultToolkit().beep();
            return null;
    }
}
