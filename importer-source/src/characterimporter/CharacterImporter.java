/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author Family
 */
public class CharacterImporter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ImportPrefs = Prefs.Read();
        
        if (args.length > 0)
        {
            isGUI = false;
            InputXml = args[0];
            File inFile = new File(InputXml);
            if (!inFile.exists())
            {
                System.out.println("The input file you specified does not exist.");
                return;
            }                  
            inputXmlFileList = new File[]{inFile};
            if (args.length > 1)
            {
                OutputMtMacSet = args[1];
                File outFile = new File(OutputMtMacSet);
                OutputMtMacDir = outFile.getParent();
            }
            else
            {
                OutputMtMacSet = inFile.getAbsolutePath().concat(".mtmacset");
                OutputMtMacDir = inFile.getParent();
            }
        }
        else
        {
            isGUI = true;
            JFileChooser inputChooser = new JFileChooser();
            inputChooser.setDialogTitle("Select input files");
            FileNameExtensionFilter inputFilter = new FileNameExtensionFilter("DnD files", "dnd4e", "monster");
            inputChooser.setFileFilter(inputFilter);
            File startdir = new File(ImportPrefs.getInputPath());
            inputChooser.setCurrentDirectory(startdir);
            inputChooser.setMultiSelectionEnabled(true);
            Boolean needInput = true;
            while (needInput)
            {
                int returnVal = inputChooser.showDialog(null, "Select");
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    for (int i_infile = 0; i_infile < inputChooser.getSelectedFiles().length; i_infile++)
                    {
                        if (!inputChooser.getSelectedFiles()[i_infile].exists())
                        {
                            JOptionPane.showMessageDialog(null, "An input file you specified does not exist.", "No input file", JOptionPane.ERROR_MESSAGE);
                            needInput = true;
                            continue;
                        }
                    }
                    inputXmlFileList = inputChooser.getSelectedFiles();
                    ImportPrefs.setInputPath(inputChooser.getSelectedFiles()[0].getParent());
                    needInput = false;
                }
                if(returnVal == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null, "At least one input file is required.\nQuitting...", "No input file", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }
            
            JFileChooser outputChooser = new JFileChooser();
            Boolean isDoingBatch;
            if (inputXmlFileList.length > 1)
            {
                outputChooser.setDialogTitle("Set an output directory");
                outputChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                isDoingBatch = true;
            }
            else
            {
                outputChooser.setDialogTitle("Set an output file");
                FileNameExtensionFilter outputFilter = new FileNameExtensionFilter("MapTool macro sets", "mtmacset");
                outputChooser.setFileFilter(outputFilter);
                outputChooser.setSelectedFile(new File(inputXmlFileList[0].getName().concat(".mtmacset")));
                isDoingBatch = false;
            }
            startdir = new File(ImportPrefs.getOutputPath());
            outputChooser.setCurrentDirectory(startdir);
            Boolean needOutput = true;
            while (needOutput)
            {
                int returnVal = outputChooser.showDialog(null, "Select");
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                     String filename = outputChooser.getSelectedFile().getAbsolutePath();
                     if (isDoingBatch)
                     {
                        ImportPrefs.setOutputPath(outputChooser.getSelectedFile().getAbsolutePath());
                        OutputMtMacDir = filename;
                     }
                     else
                     {
                         OutputMtMacSet = filename;
                         if (!OutputMtMacSet.endsWith(".mtmacset"))
                             OutputMtMacSet = OutputMtMacSet.concat(".mtmacset");
                         OutputMtMacDir = outputChooser.getSelectedFile().getParent();
                         ImportPrefs.setOutputPath(OutputMtMacDir);
                     }
                }
                if(returnVal == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null, "An output file or directory is required.\nQuitting...", "No output file", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                needOutput = false;
            }
        }
        
        if (inputXmlFileList == null || inputXmlFileList.length == 0)
        {
            System.out.println("No input file!");
            JOptionPane.showMessageDialog(null, "You did not specify any input .dnd4e files.\n The input file is mandatory.\n Quitting...", "No input file", JOptionPane.ERROR_MESSAGE);
        }
        else try {
            
            
            for (int i_infile = 0; i_infile < inputXmlFileList.length; i_infile++)
            {
                InputXml = inputXmlFileList[i_infile].getAbsolutePath();
                String inputFilename = inputXmlFileList[i_infile].getName();
                
                //1. Import the Wizards XML into our objects
                DndFileImporter Importer = new DndFileImporter(InputXml);
                CharacterData Data = Importer.Import(); // makes an array of objects that represent all we need to know about powers

                String absoluteOutput;
                if (OutputMtMacSet != null)
                {
                    absoluteOutput = OutputMtMacSet;
                }
                else
                {
                    absoluteOutput = OutputMtMacDir.concat(File.separator).concat(inputFilename).concat(".mtmacset");
                }
                        
                //2. Create and save a set of macro objects from the data.
                MacroBuilder Builder = new MacroBuilder();
                Builder.Build(Data); // uses the object arrays to come up with the list of macros, converting to json when necessary
                Builder.Export(absoluteOutput);
            }
            
            ImportPrefs.Write();
            
            
            String message = "Macro set(s) generated successfully!\nLook in " + OutputMtMacDir;
            if (isGUI)
            {
                JOptionPane.showMessageDialog(null, message, "Success!", JOptionPane.PLAIN_MESSAGE);
            }
            else
            {
                System.out.println(message);
            }
                        
	  } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An unknown error occurred during processing!\nCould not generate all macro sets.", "Error!", JOptionPane.ERROR_MESSAGE);
                System.out.println("An error occurred during processing.");
		e.printStackTrace();
	  }
    }
    
    public static String InputXml;
    public static File[] inputXmlFileList;
    public static String OutputMtMacDir;
    public static String OutputMtMacSet;
    public static Boolean isGUI;     
    public static Prefs ImportPrefs = new Prefs();
    
}
