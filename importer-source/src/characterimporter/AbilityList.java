/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class AbilityList {
    
    public AbilityList() {}
    
    private Ability Strength;
    private Ability Constitution;
    private Ability Dexterity;
    private Ability Intelligence;
    private Ability Wisdom;
    private Ability Charisma;
    
    public Ability getCharisma() {
        return Charisma;
    }
    
    public void set(String inab, Ability val)
    {
        if (inab.equals("Strength"))
            setStrength(val);
        if (inab.equals("Constitution"))
            setConstitution(val);
        if (inab.equals("Dexterity"))
            setDexterity(val);
        if (inab.equals("Intelligence"))
            setIntelligence(val);
        if (inab.equals("Wisdom"))
            setWisdom(val);
        if (inab.equals("Charisma"))
            setCharisma(val);
    }

    public void setCharisma(Ability Charisma) {
        this.Charisma = Charisma;
    }

    public Ability getConstitution() {
        return Constitution;
    }

    public void setConstitution(Ability Constitution) {
        this.Constitution = Constitution;
    }

    public Ability getDexterity() {
        return Dexterity;
    }

    public void setDexterity(Ability Dexterity) {
        this.Dexterity = Dexterity;
    }

    public Ability getIntelligence() {
        return Intelligence;
    }

    public void setIntelligence(Ability Intelligence) {
        this.Intelligence = Intelligence;
    }

    public Ability getStrength() {
        return Strength;
    }

    public void setStrength(Ability Strength) {
        this.Strength = Strength;
    }

    public Ability getWisdom() {
        return Wisdom;
    }

    public void setWisdom(Ability Wisdom) {
        this.Wisdom = Wisdom;
    }
    
    
    
}
