/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class SkillList {
    
    public SkillList() {}

    public SkillList(int inlevel, AbilityList inabs) 
    {
        int halflevel = (int)Math.floor(inlevel / 2);
        Acrobatics = new Skill(halflevel + inabs.getDexterity().getModifier(), 0, "");
        Arcana = new Skill(halflevel + inabs.getIntelligence().getModifier(), 0, "");
        Athletics = new Skill(halflevel + inabs.getStrength().getModifier(), 0, "");
        Bluff = new Skill(halflevel + inabs.getCharisma().getModifier(), 0, "");
        Diplomacy = new Skill(halflevel + inabs.getCharisma().getModifier(), 0, "");
        Dungeoneering = new Skill(halflevel + inabs.getWisdom().getModifier(), 0, "");
        Endurance = new Skill(halflevel + inabs.getConstitution().getModifier(), 0, "");
        Heal = new Skill(halflevel + inabs.getWisdom().getModifier(), 0, "");
        History = new Skill(halflevel + inabs.getIntelligence().getModifier(), 0, "");
        Insight = new Skill(halflevel + inabs.getWisdom().getModifier(), 0, "");
        Intimidate = new Skill(halflevel + inabs.getCharisma().getModifier(), 0, "");
        Nature = new Skill(halflevel + inabs.getWisdom().getModifier(), 0, "");
        Perception = new Skill(halflevel + inabs.getWisdom().getModifier(), 0, "");
        Religion = new Skill(halflevel + inabs.getIntelligence().getModifier(), 0, "");
        Stealth = new Skill(halflevel + inabs.getDexterity().getModifier(), 0, "");
        Streetwise = new Skill(halflevel + inabs.getCharisma().getModifier(), 0, "");
        Thievery = new Skill(halflevel + inabs.getDexterity().getModifier(), 0, "");
    }

    public void set (String inname, Skill inskill)
    {
        if (inname.equals("Acrobatics")) { Acrobatics = inskill; }
        if (inname.equals("Arcana")) { Arcana = inskill; }
        if (inname.equals("Athletics")) { Athletics = inskill; }
        if (inname.equals("Bluff")) { Bluff = inskill; }
        if (inname.equals("Diplomacy")) { Diplomacy = inskill; }
        if (inname.equals("Dungeoneering")) { Dungeoneering = inskill; }
        if (inname.equals("Endurance")) { Endurance = inskill; }
        if (inname.equals("Heal")) { Heal = inskill; }
        if (inname.equals("History")) { History = inskill; }
        if (inname.equals("Insight")) { Insight = inskill; }
        if (inname.equals("Intimidate")) { Intimidate = inskill; }
        if (inname.equals("Nature")) { Nature = inskill; }
        if (inname.equals("Perception")) { Perception = inskill; }
        if (inname.equals("Religion")) { Religion = inskill; }
        if (inname.equals("Stealth")) { Stealth = inskill; }
        if (inname.equals("Streetwise")) { Streetwise = inskill; }
        if (inname.equals("Thievery")) { Thievery = inskill; }
    }
            
    private Skill Acrobatics;
    private Skill Arcana;
    private Skill Athletics;
    private Skill Bluff;
    private Skill Diplomacy;
    private Skill Dungeoneering;
    private Skill Endurance;
    private Skill Heal;
    private Skill History;
    private Skill Insight;
    private Skill Intimidate;
    private Skill Nature;
    private Skill Perception;
    private Skill Religion;
    private Skill Stealth;
    private Skill Streetwise;
    private Skill Thievery;

    public Skill getAcrobatics() {
        return Acrobatics;
    }

    public void setAcrobatics(Skill Acrobatics) {
        this.Acrobatics = Acrobatics;
    }

    public Skill getArcana() {
        return Arcana;
    }

    public void setArcana(Skill Arcana) {
        this.Arcana = Arcana;
    }

    public Skill getAthletics() {
        return Athletics;
    }

    public void setAthletics(Skill Athletics) {
        this.Athletics = Athletics;
    }

    public Skill getBluff() {
        return Bluff;
    }

    public void setBluff(Skill Bluff) {
        this.Bluff = Bluff;
    }

    public Skill getDiplomacy() {
        return Diplomacy;
    }

    public void setDiplomacy(Skill Diplomacy) {
        this.Diplomacy = Diplomacy;
    }

    public Skill getDungeoneering() {
        return Dungeoneering;
    }

    public void setDungeoneering(Skill Dungeoneering) {
        this.Dungeoneering = Dungeoneering;
    }

    public Skill getEndurance() {
        return Endurance;
    }

    public void setEndurance(Skill Endurance) {
        this.Endurance = Endurance;
    }

    public Skill getHeal() {
        return Heal;
    }

    public void setHeal(Skill Heal) {
        this.Heal = Heal;
    }

    public Skill getHistory() {
        return History;
    }

    public void setHistory(Skill History) {
        this.History = History;
    }

    public Skill getInsight() {
        return Insight;
    }

    public void setInsight(Skill Insight) {
        this.Insight = Insight;
    }

    public Skill getIntimidate() {
        return Intimidate;
    }

    public void setIntimidate(Skill Intimidate) {
        this.Intimidate = Intimidate;
    }

    public Skill getNature() {
        return Nature;
    }

    public void setNature(Skill Nature) {
        this.Nature = Nature;
    }

    public Skill getPerception() {
        return Perception;
    }

    public void setPerception(Skill Perception) {
        this.Perception = Perception;
    }

    public Skill getReligion() {
        return Religion;
    }

    public void setReligion(Skill Religion) {
        this.Religion = Religion;
    }

    public Skill getStealth() {
        return Stealth;
    }

    public void setStealth(Skill Stealth) {
        this.Stealth = Stealth;
    }

    public Skill getStreetwise() {
        return Streetwise;
    }

    public void setStreetwise(Skill Streetwise) {
        this.Streetwise = Streetwise;
    }

    public Skill getThievery() {
        return Thievery;
    }

    public void setThievery(Skill Thievery) {
        this.Thievery = Thievery;
    }
    
    
}
