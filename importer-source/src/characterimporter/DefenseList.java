/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class DefenseList {
    
    public DefenseList() {}
    
    private Defense AC;
    private Defense Reflex;
    private Defense Will;
    private Defense Fortitude;
    
    public void set(String indef, Defense val)
    {
        if (indef.equals("AC"))
            setAC(val);
        if (indef.equals("Reflex"))
            setReflex(val);
        if (indef.equals("Will"))
            setWill(val);
        if (indef.equals("Fortitude"))
            setFortitude(val);
    }

    public Defense getAC() {
        return AC;
    }

    public void setAC(Defense AC) {
        this.AC = AC;
    }

    public Defense getFortitude() {
        return Fortitude;
    }

    public void setFortitude(Defense Fortitude) {
        this.Fortitude = Fortitude;
    }

    public Defense getReflex() {
        return Reflex;
    }

    public void setReflex(Defense Reflex) {
        this.Reflex = Reflex;
    }

    public Defense getWill() {
        return Will;
    }

    public void setWill(Defense Will) {
        this.Will = Will;
    }
    
    
}
