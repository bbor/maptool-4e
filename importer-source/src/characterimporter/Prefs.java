/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
 *
 * @author Family
 */

public class Prefs implements Serializable {
    
    public Prefs() { }
    
    public static Prefs Read() {
        Prefs newPrefs = new Prefs();
        File prefsfile = new File("prefs.dat");
        if (prefsfile.exists())
        try {
            FileInputStream fis = new FileInputStream("prefs.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            newPrefs = (Prefs) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newPrefs;
     }
    
    public void Write() {
        FileOutputStream fos = null;
        {
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream("prefs.dat");
                oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(Prefs.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
         
}
    
    private String InputPath = "";
    private String OutputPath = "";

    public String getInputPath() {
        return InputPath;
    }

    public void setInputPath(String InputPath) {
        this.InputPath = InputPath;
    }

    public String getOutputPath() {
        return OutputPath;
    }

    public void setOutputPath(String OutputPath) {
        this.OutputPath = OutputPath;
    }
    
    
}
