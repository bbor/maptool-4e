/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class NameUrl {
    
    public NameUrl() { }
    
    public NameUrl(String inname, String inurl) {
        this.Name = inname;
        this.Url = inurl;
    }

    private String Name;
    private String Url;
    private String Index;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String Index) {
        this.Index = Index;
    }
    
    
}
