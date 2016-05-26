/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class AttackConfig {
    
    private String Attack;
    private String Delivery; // Weapon or implement
    private Boolean HalfDamageOnMiss = false;
    private String Miss;
    private String Hit;

    public String getDelivery() {
        return Delivery;
    }

    public void setDelivery(String Delivery) {
        this.Delivery = Delivery;
    }

    public Boolean getHalfDamageOnMiss() {
        return HalfDamageOnMiss;
    }

    public void setHalfDamageOnMiss(Boolean HalfDamageOnMiss) {
        this.HalfDamageOnMiss = HalfDamageOnMiss;
    }

    public String getMiss() {
        return Miss;
    }

    public void setMiss(String Miss) {
        this.Miss = Miss;
    }

    public String getHit() {
        return Hit;
    }

    public void setHit(String Hit) {
        this.Hit = Hit;
    }

    public String getAttack() {
        return Attack;
    }

    public void setAttack(String Attack) {
        this.Attack = Attack;
    }
    
    
}
