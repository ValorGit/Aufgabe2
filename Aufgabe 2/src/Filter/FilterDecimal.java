
package Filter;

import java.awt.Toolkit;
import java.text.DecimalFormatSymbols;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author Alexander Dünne, Jürgen Christl
 */
public class FilterDecimal implements UnaryOperator<TextFormatter.Change> {
    private static final char SEPERATOR = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    public static final String REGEX = "-?\\d+(" + SEPERATOR + "\\d+)?";
    public static final String REGEX_EINGABE = "-?\\d*|-?\\d+" + SEPERATOR + "?|-?\\d+" + SEPERATOR + "\\d{1,2}";
//    public static final String REGEX_EINGABE = "-?\\d*|-?\\d+" + SEPERATOR + "?\\d*";
   
    
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