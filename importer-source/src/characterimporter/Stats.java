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
public class Stats {
    
    public Stats() { }
    
    private AbilityList Abilities = new AbilityList();
    private SkillList Skills = new SkillList();
    private DefenseList Defenses = new DefenseList();
    private ArrayList Languages = new ArrayList();
    private int MaxHP;
    private int ActionPoints = 0;
    private int Level;
    private int HalfLevel;
    private int InitiativeBonus;
    private String Size;
    private String Alignment;

    public AbilityList getAbilities() {
        return Abilities;
    }

    public void setAbilities(AbilityList Abilities) {
        this.Abilities = Abilities;
    }

    public int getHalfLevel() {
        return HalfLevel;
    }

    public void setHalfLevel(int HalfLevel) {
        this.HalfLevel = HalfLevel;
    }

    public int getInitiativeBonus() {
        return InitiativeBonus;
    }

    public void setInitiativeBonus(int InitiativeBonus) {
        this.InitiativeBonus = InitiativeBonus;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void setMaxHP(int MaxHP) {
        this.MaxHP = MaxHP;
    }

    public DefenseList getDefenses() {
        return Defenses;
    }

    public void setDefenses(DefenseList Defenses) {
        this.Defenses = Defenses;
    }

    public SkillList getSkills() {
        return Skills;
    }

    public void setSkills(SkillList Skills) {
        this.Skills = Skills;
    }

    public ArrayList getLanguages() {
        return Languages;
    }

    public void setLanguages(ArrayList Languages) {
        this.Languages = Languages;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getAlignment() {
        return Alignment;
    }

    public void setAlignment(String Alignment) {
        this.Alignment = Alignment;
    }

    public int getActionPoints() {
        return ActionPoints;
    }

    public void setActionPoints(int ActionPoints) {
        this.ActionPoints = ActionPoints;
    }

    
}
