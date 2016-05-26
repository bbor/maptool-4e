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
public class PCStats extends Stats {
    
    public PCStats() {
        this.setActionPoints(1);
    }
    
    public ArrayList Feats;
    private NameUrl DndClass = new NameUrl();
    private NameUrl Race = new NameUrl();
    private NameUrl ParagonPath = new NameUrl();
    private int DeathSaves;
    private int HealingSurges;
    private int RingSlots;
    private int XPNeeded;
    private int PowerPoints = 0;
    private int Regeneration;
    private int Speed;
    private int SurgeBonus = 0;
    private Boolean IsAPc = true;
    private Details Details;

    
    public NameUrl getDndClass() {
        return DndClass;
    }

    public void setDndClass(NameUrl DndClass) {
        this.DndClass = DndClass;
    }

    public NameUrl getRace() {
        return Race;
    }

    public void setRace(NameUrl Race) {
        this.Race = Race;
    }

    public int getDeathSaves() {
        return DeathSaves;
    }

    public void setDeathSaves(int DeathSaves) {
        this.DeathSaves = DeathSaves;
    }

    public int getHealingSurges() {
        return HealingSurges;
    }

    public void setHealingSurges(int HealingSurges) {
        this.HealingSurges = HealingSurges;
    }

    public int getRingSlots() {
        return RingSlots;
    }

    public void setRingSlots(int RingSlots) {
        this.RingSlots = RingSlots;
    }

    public int getXPNeeded() {
        return XPNeeded;
    }

    public void setXPNeeded(int XPNeeded) {
        this.XPNeeded = XPNeeded;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int Speed) {
        this.Speed = Speed;
    }

    public NameUrl getParagonPath() {
        return ParagonPath;
    }

    public void setParagonPath(NameUrl ParagonPath) {
        this.ParagonPath = ParagonPath;
    }

    public ArrayList getFeats() {
        return Feats;
    }

    public void setFeats(ArrayList Feats) {
        this.Feats = Feats;
    }

    public int getSurgeBonus() {
        return SurgeBonus;
    }

    public void setSurgeBonus(int SurgeBonus) {
        this.SurgeBonus = SurgeBonus;
    }

    public int getPowerPoints() {
        return PowerPoints;
    }

    public void setPowerPoints(int PowerPoints) {
        this.PowerPoints = PowerPoints;
    }

    public Boolean getIsAPc() {
        return IsAPc;
    }

    public void setIsAPc(Boolean IsAPc) {
        this.IsAPc = IsAPc;
    }

    public int getRegeneration() {
        return Regeneration;
    }

    public void setRegeneration(int Regeneration) {
        this.Regeneration = Regeneration;
    }

    public characterimporter.Details getDetails() {
        return Details;
    }

    public void setDetails(characterimporter.Details Details) {
        this.Details = Details;
    }


}
