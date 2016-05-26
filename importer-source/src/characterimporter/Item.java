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
public class Item {
    
    public Item() {}
    
    private ArrayList Names = new ArrayList(); //NameUrls
    private String ID = ""; //String charelem values
    private String InternalID = ""; //String internal-id values
    private String Type = ""; // Weapon, Armor, Gear
    private String MagicItemType = ""; // Holy Symbol, Arms Slot Item, etc.
    private String Group = ""; // keyword: Heavy Blade
    private String Slot = "";
    private String Properties = "";
    private ArrayList Powers = new ArrayList();
    private int DamageMultiplier;
    private int DamageDie;
    private int ProficiencyBonus = 0;
    private int ItemBonusToHit = 0;
    private int ItemBonusToDamage = 0;
    private int isAnImplement = 0;
    private String CriticalDamage = "";
    private String CriticalDamageType = "";
    private int isEquipped = 0;
    private int Quantity = 0;
    private ArrayList ChildTagNames = new ArrayList(); // Strings
    private ArrayList ChildTagContents = new ArrayList(); // Strings

    public String getCriticalDamage() {
        return CriticalDamage;
    }

    public void setCriticalDamage(String CriticalDamage) {
        this.CriticalDamage = CriticalDamage;
    }

    public String getCriticalDamageType() {
        return CriticalDamageType;
    }

    public void setCriticalDamageType(String CriticalDamageType) {
        this.CriticalDamageType = CriticalDamageType;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String Group) {
        this.Group = Group;
    }

    public int getItemBonusToDamage() {
        return ItemBonusToDamage;
    }

    public void setItemBonusToDamage(int ItemBonusToDamage) {
        this.ItemBonusToDamage = ItemBonusToDamage;
    }

    public int getItemBonusToHit() {
        return ItemBonusToHit;
    }

    public void setItemBonusToHit(int ItemBonusToHit) {
        this.ItemBonusToHit = ItemBonusToHit;
    }

    public ArrayList getNames() {
        return Names;
    }

    public void setNames(ArrayList Names) {
        this.Names = Names;
    }

    public ArrayList getPowers() {
        return Powers;
    }

    public void setPowers(ArrayList Powers) {
        this.Powers = Powers;
    }

    public int getProficiencyBonus() {
        return ProficiencyBonus;
    }

    public void setProficiencyBonus(int ProficiencyBonus) {
        this.ProficiencyBonus = ProficiencyBonus;
    }

    public String getSlot() {
        return Slot;
    }

    public void setSlot(String Slot) {
        this.Slot = Slot;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getDamageDie() {
        return DamageDie;
    }

    public void setDamageDie(int DamageDie) {
        this.DamageDie = DamageDie;
    }

    public int getDamageMultiplier() {
        return DamageMultiplier;
    }

    public void setDamageMultiplier(int DamageMultiplier) {
        this.DamageMultiplier = DamageMultiplier;
    }

    public String getMagicItemType() {
        return MagicItemType;
    }

    public void setMagicItemType(String MagicItemType) {
        this.MagicItemType = MagicItemType;
    }

    public int getIsAnImplement() {
        return isAnImplement;
    }

    public void setIsAnImplement(int isAnImplement) {
        this.isAnImplement = isAnImplement;
    }

    public int getIsEquipped() {
        return isEquipped;
    }

    public void setIsEquipped(int isEquipped) {
        this.isEquipped = isEquipped;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList getChildTagContents() {
        return ChildTagContents;
    }

    public void setChildTagContents(ArrayList ChildTagContents) {
        this.ChildTagContents = ChildTagContents;
    }

    public ArrayList getChildTagNames() {
        return ChildTagNames;
    }

    public void setChildTagNames(ArrayList ChildTagNames) {
        this.ChildTagNames = ChildTagNames;
    }

    public String getProperties() {
        return Properties;
    }

    public void setProperties(String Properties) {
        this.Properties = Properties;
    }

    public String getInternalID() {
        return InternalID;
    }

    public void setInternalID(String InternalID) {
        this.InternalID = InternalID;
    }

    
    
}
