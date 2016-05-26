/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;

/**
 *
 * @author Family
 */
public class Ability {
    public Ability() { }
    
    public Ability(int inscore, int inmod) 
    { 
        Score = inscore;
        Modifier = inmod;
    }
    
    public Ability(int inscore) 
    { 
        Score = inscore;
        Modifier = (int)Math.floor(inscore/2) - 5;
    }
    
    private int Score;
    private int Modifier;

    public int getModifier() {
        return Modifier;
    }

    public void setModifier(int Modifier) {
        this.Modifier = Modifier;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    
}
