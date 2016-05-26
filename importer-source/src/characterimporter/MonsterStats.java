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
public class MonsterStats extends Stats {
    
    public MonsterStats() { }
    
    private NameValueDetails Type;
    private NameValueDetails Origin;
    private NameValueDetails Regeneration;
    private String Role;
    private String GroupRole;
    private Boolean IsAMonster = true;
    private Boolean IsALeader;
    private ArrayList Senses;
    private ArrayList Keywords;
    private String Tactics;
    private String Description;
    private String MonsterName;
    private String Url;
    private int XP;
    private int SavingThrowBonus;
    private ArrayList Speeds;
    private ArrayList Vulnerabilities;
    private ArrayList Resistances;
    private ArrayList Immunities;

    public Boolean getIsAMonster() {
        return IsAMonster;
    }

    public void setIsAMonster(Boolean IsAMonster) {
        this.IsAMonster = IsAMonster;
    }

    public NameValueDetails getOrigin() {
        return Origin;
    }

    public void setOrigin(NameValueDetails Origin) {
        this.Origin = Origin;
    }

    public NameValueDetails getType() {
        return Type;
    }

    public void setType(NameValueDetails Type) {
        this.Type = Type;
    }

    public Boolean getIsALeader() {
        return IsALeader;
    }

    public void setIsALeader(Boolean IsALeader) {
        this.IsALeader = IsALeader;
    }

    public String getGroupRole() {
        return GroupRole;
    }

    public void setGroupRole(String GroupRole) {
        this.GroupRole = GroupRole;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public ArrayList getSenses() {
        return Senses;
    }

    public void setSenses(ArrayList Senses) {
        this.Senses = Senses;
    }

    public String getTactics() {
        return Tactics;
    }

    public void setTactics(String Tactics) {
        this.Tactics = Tactics;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public ArrayList getKeywords() {
        return Keywords;
    }

    public void setKeywords(ArrayList Keywords) {
        this.Keywords = Keywords;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public int getSavingThrowBonus() {
        return SavingThrowBonus;
    }

    public void setSavingThrowBonus(int SavingThrowBonus) {
        this.SavingThrowBonus = SavingThrowBonus;
    }

    public ArrayList getSpeeds() {
        return Speeds;
    }

    public void setSpeeds(ArrayList Speeds) {
        this.Speeds = Speeds;
    }

    public NameValueDetails getRegeneration() {
        return Regeneration;
    }

    public void setRegeneration(NameValueDetails Regeneration) {
        this.Regeneration = Regeneration;
    }

    public ArrayList getImmunities() {
        return Immunities;
    }

    public void setImmunities(ArrayList Immunities) {
        this.Immunities = Immunities;
    }

    public ArrayList getResistances() {
        return Resistances;
    }

    public void setResistances(ArrayList Resistances) {
        this.Resistances = Resistances;
    }

    public ArrayList getVulnerabilities() {
        return Vulnerabilities;
    }

    public void setVulnerabilities(ArrayList Vulnerabilities) {
        this.Vulnerabilities = Vulnerabilities;
    }

    public String getMonsterName() {
        return MonsterName;
    }

    public void setMonsterName(String MonsterName) {
        this.MonsterName = MonsterName;
    }
    
    
}
