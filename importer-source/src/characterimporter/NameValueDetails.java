/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class NameValueDetails {
    
    public NameValueDetails() { }
    
    public NameValueDetails(String inname, int inval, String indetails) {
        Name = inname;
        Value = inval;
        Details = indetails;
    }
    
    private String Name;
    private int Value;
    private String Details;

    public String getDetails() {
        return Details;
    }

    public void setDetails(String Details) {
        this.Details = Details;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int Value) {
        this.Value = Value;
    }
    
    
}
