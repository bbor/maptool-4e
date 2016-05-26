/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class Skill {
    
    public Skill() { }
    
    public Skill(int inbonus, int inistrained, String inurl) 
    {
        Bonus = inbonus;
        isTrained = inistrained;
        Url = inurl;
    }

    private String Url;
    private int Bonus;
    private int isTrained;

    public int getBonus() {
        return Bonus;
    }

    public void setBonus(int Bonus) {
        this.Bonus = Bonus;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public int getIsTrained() {
        return isTrained;
    }

    public void setIsTrained(int isTrained) {
        this.isTrained = isTrained;
    }
    
    
    
}
