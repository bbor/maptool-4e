package characterimporter;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Family
 */
public class AttackWeaponStats {
    
    public AttackWeaponStats() { }

    private String Name = "";
    private String WeaponID = ""; // string IDs: charelem values
    private String WeaponInternalID = ""; // string IDs: internal-id values
    private int AttackBonus;
    private String Damage;
    private String AttackStat;
    private String Defense;
    private int CriticalBaseDamage;
    private ArrayList CriticalExtraDamageComponents = new ArrayList(); //CritComponents
    private String CritRange;
    private String CritThreshold;
    private String Conditions; // make into a bulleted list by \n -- and make it a tooltip in MT?
    private String HitComponents;
    private String DamageComponents;

    public int getAttackBonus() {
        return AttackBonus;
    }

    public void setAttackBonus(int AttackBonus) {
        this.AttackBonus = AttackBonus;
    }

    public String getAttackStat() {
        return AttackStat;
    }

    public void setAttackStat(String AttackStat) {
        this.AttackStat = AttackStat;
    }

    public String getConditions() {
        return Conditions;
    }

    public void setConditions(String Conditions) {
        this.Conditions = Conditions;
    }

    public int getCriticalBaseDamage() {
        return CriticalBaseDamage;
    }

    public void setCriticalBaseDamage(int CriticalBaseDamage) {
        this.CriticalBaseDamage = CriticalBaseDamage;
    }

    public String getDamage() {
        return Damage;
    }

    public void setDamage(String Damage) {
        this.Damage = Damage;
    }

    public String getDefense() {
        return Defense;
    }

    public void setDefense(String Defense) {
        this.Defense = Defense;
    }

    public String getCritRange() {
        return CritRange;
    }

    public void setCritRange(String CritRange) {
        this.CritRange = CritRange;
    }

    public ArrayList getCriticalExtraDamageComponents() {
        return CriticalExtraDamageComponents;
    }

    public void setCriticalExtraDamageComponents(ArrayList CriticalExtraDamageComponents) {
        this.CriticalExtraDamageComponents = CriticalExtraDamageComponents;
    }

    public String getWeaponID() {
        return WeaponID;
    }

    public void setWeaponID(String WeaponID) {
        this.WeaponID = WeaponID;
    }

    public String getDamageComponents() {
        return DamageComponents;
    }

    public void setDamageComponents(String DamageComponents) {
        this.DamageComponents = DamageComponents;
    }

    public String getHitComponents() {
        return HitComponents;
    }

    public void setHitComponents(String HitComponents) {
        this.HitComponents = HitComponents;
    }

    public String getCritThreshold() {
        return CritThreshold;
    }

    public void setCritThreshold(String CritThreshold) {
        this.CritThreshold = CritThreshold;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getWeaponInternalID() {
        return WeaponInternalID;
    }

    public void setWeaponInternalID(String WeaponInternalID) {
        this.WeaponInternalID = WeaponInternalID;
    }
    
    
}
