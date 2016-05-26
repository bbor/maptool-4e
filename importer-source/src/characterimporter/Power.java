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
public class Power {
    
    public Power() {}
    
    private String Name;
    private String Flavor;
    private String Frequency;
    private String Keywords;
    private String ActionType;
    private String AttackRange;
    private Boolean IsBasic = false;
    private String Range;
    private String Target;
    private String Recharge;
    private String Effect;
    private String Aftereffect;
    private String Details;
    private String PowerType;
    private String Special;
    private String UsageDetails;
    private String Requirements;
    private String Category;
    private AttackConfig AttackConfig = new AttackConfig();
    private ArrayList WeaponStats = new ArrayList();
    private String Url;
    private Boolean isChannelDivinity = false;
    private Boolean isAnAttack = false;
    private Boolean hasTrigger = false;
    private String Trigger;
    private String itemID;
    private Boolean isAnItemPower = false;
    private ArrayList ChildTagNames = new ArrayList(); // Strings
    private ArrayList ChildTagContents = new ArrayList(); // Strings

    public String getActionType() {
        return ActionType;
    }

    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }

    public characterimporter.AttackConfig getAttackConfig() {
        return AttackConfig;
    }

    public void setAttackConfig(characterimporter.AttackConfig AttackConfig) {
        this.AttackConfig = AttackConfig;
    }

    public String getEffect() {
        return Effect;
    }

    public void setEffect(String Effect) {
        this.Effect = Effect;
    }

    public String getFlavor() {
        return Flavor;
    }

    public void setFlavor(String Flavor) {
        this.Flavor = Flavor;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String Frequency) {
        this.Frequency = Frequency;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String Keywords) {
        this.Keywords = Keywords;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
    public String getRange() {
        return Range;
    }

    public void setRange(String Range) {
        this.Range = Range;
    }

    public String getSpecial() {
        return Special;
    }

    public void setSpecial(String Special) {
        this.Special = Special;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String Target) {
        this.Target = Target;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public Boolean getIsChannelDivinity() {
        return isChannelDivinity;
    }

    public void setIsChannelDivinity(Boolean isChannelDivinity) {
        this.isChannelDivinity = isChannelDivinity;
    }

    public String getPowerType() {
        return PowerType;
    }

    public void setPowerType(String PowerType) {
        this.PowerType = PowerType;
    }

    public Boolean getIsAnAttack() {
        return isAnAttack;
    }

    public void setIsAnAttack(Boolean isAnAttack) {
        this.isAnAttack = isAnAttack;
    }

    public Boolean getIsAnItemPower() {
        return isAnItemPower;
    }

    public void setIsAnItemPower(Boolean isAnItemPower) {
        this.isAnItemPower = isAnItemPower;
    }

    public String getAttackRange() {
        return AttackRange;
    }

    public void setAttackRange(String AttackRange) {
        this.AttackRange = AttackRange;
    }

    public ArrayList getWeaponStats() {
        return WeaponStats;
    }

    public void setWeaponStats(ArrayList WeaponStats) {
        this.WeaponStats = WeaponStats;
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

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String weaponID) {
        this.itemID = weaponID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public Boolean getHasTrigger() {
        return hasTrigger;
    }

    public void setHasTrigger(Boolean hasTrigger) {
        this.hasTrigger = hasTrigger;
    }

    public Boolean getIsBasic() {
        return IsBasic;
    }

    public void setIsBasic(Boolean IsBasic) {
        this.IsBasic = IsBasic;
    }

    public String getTrigger() {
        return Trigger;
    }

    public void setTrigger(String Trigger) {
        this.Trigger = Trigger;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String Details) {
        this.Details = Details;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String Requirements) {
        this.Requirements = Requirements;
    }

    public String getAftereffect() {
        return Aftereffect;
    }

    public void setAftereffect(String Aftereffect) {
        this.Aftereffect = Aftereffect;
    }

    public String getRecharge() {
        return Recharge;
    }

    public void setRecharge(String Recharge) {
        this.Recharge = Recharge;
    }

    public String getUsageDetails() {
        return UsageDetails;
    }

    public void setUsageDetails(String UsageDetails) {
        this.UsageDetails = UsageDetails;
    }

    
    
    
    
            
            
}
