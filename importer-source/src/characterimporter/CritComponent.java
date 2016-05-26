/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;
import java.util.ArrayList;

/**
 *
 * @author Family
 */
public class CritComponent {
    
    public CritComponent() {}
    
    private String BaseText;
    private ArrayList Rolls = new ArrayList(); // of strings like "3d6" or "2d10"

    public String getBaseText() {
        return BaseText;
    }

    public void setBaseText(String BaseText) {
        this.BaseText = BaseText;
    }

    public ArrayList getRolls() {
        return Rolls;
    }

    public void setRolls(ArrayList Rolls) {
        this.Rolls = Rolls;
    }
    
    
}
