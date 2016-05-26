/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class Defense {
    
    public Defense() {}
    
    public Defense(int inval) 
    { 
        Value = inval;
    }
    
    private int Value;

    public int getValue() {
        return Value;
    }

    public void setValue(int Value) {
        this.Value = Value;
    }
    
    
}
