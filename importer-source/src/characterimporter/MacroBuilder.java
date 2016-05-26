/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import net.sf.json.JSONArray;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Result;
import javax.xml.transform.Source;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 *
 * @author Family
 */
public class MacroBuilder {
    
    public Document macrosDoc;
    public Document propertiesDoc;
    
    public MacroBuilder ()
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            macrosDoc = dBuilder.newDocument();
            propertiesDoc = dBuilder.newDocument();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DndFileImporter.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void Build(CharacterData data)
    {
        Element rootElement = macrosDoc.createElement("list");
        macrosDoc.appendChild(rootElement);
        rootElement.appendChild(BuildStatsMacro(data.Stats));
        rootElement.appendChild(BuildAttackMacro(data.Powers));
        rootElement.appendChild(BuildUtilitiesMacro(data.Powers));
        rootElement.appendChild(BuildItemPowersMacro(data.Powers));
        rootElement.appendChild(BuildItemsMacro(data.Items));
    
        if (data.isAPC)
        {
            rootElement.appendChild(BuildCharSheetDisplayMacro());
            rootElement.appendChild(BuildCombatDisplayMacro());
            rootElement.appendChild(BuildStatsDisplayMacro());
            rootElement.appendChild(BuildInventoryDisplayMacro());
            rootElement.appendChild(BuildSkillsDisplayMacro());
            rootElement.appendChild(BuildConditionsDisplayMacro());
            rootElement.appendChild(BuildAllDisplayMacro());
        } 
        
        
        rootElement.appendChild(BuildInitMacro(data.isAPC));
        
        if (data.isAPC)
        {
            rootElement.appendChild(BuildDebugMacro());
            rootElement.appendChild(BuildDebugRefreshConditionsMacro());
            rootElement.appendChild(BuildPrefsMacro());
        }
        
        Element propRootElement = propertiesDoc.createElement("map");
        propertiesDoc.appendChild(propRootElement);
        Element entryElement = propertiesDoc.createElement("entry");
        propRootElement.appendChild(entryElement);
        Element stringElement = propertiesDoc.createElement("string");
        stringElement.setTextContent("version");
        entryElement.appendChild(stringElement);
        Element stringElement2 = propertiesDoc.createElement("string");
        stringElement2.setTextContent("1.3.b87");
        entryElement.appendChild(stringElement2);
       
    }
    
    
    public Element BuildCharSheetDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99010");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh Character Sheet");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays the character's statistics and abilities.");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Character\"][h:BBrefresh()]");
        
        return eMacro;        
    }
    
    public Element BuildCombatDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99011");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh Combat");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays the character's current attack powers and utilities.");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Combat\"][h:BBrefresh()]");
        
        return eMacro;        
    }

    public Element BuildStatsDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99012");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh Stats");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays the character's current hit points, temporary hit points, defenses, and attack bonuses.");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Stats\"][h:BBrefresh()]");
        
        return eMacro;        
    }

    public Element BuildInventoryDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99013");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh Inventory");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays the character's current gear list.");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Inventory\"][h:BBrefresh()]");
        
        return eMacro;        
    }

    public Element BuildConditionsDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99014");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh Conditions");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays the character's current conditions and bonuses.");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Conditions\"][h:BBrefresh()]");
        
        return eMacro;        
    }

    public Element BuildSkillsDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99015");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh Skills");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays the character's current skill stats.");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Skills\"][h:BBrefresh()]");
        
        return eMacro;        
    }

    public Element BuildAllDisplayMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Control panels");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99016");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh All");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("displays all frames..");
        eMacro.getElementsByTagName("command").item(0).setTextContent("[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Skills\"]"
                + "[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Character\"]"
                + "[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Inventory\"]"
                + "[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Combat\"]"
                + "[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Conditions\"]"
                + "[MACRO(\"NeedsRefresh@Lib:BBFramework\"):\"Stats\"]"
                + "[h:BBrefresh()]");
        
        return eMacro;        
    }

    public Element BuildDebugMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("ZZZ Debug");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99020");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Show Properties");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("shows the character properties and library properties.");
        
        String maccmd = "[h:propnamelist = getPropertyNamesRaw()]\n"
                + "[h:libpropnamelist = getLibProperty(\"BBLibPropertyNames\", \"Lib:BBFramework\")]\n"
                + "\n"
                + "[h:inputfields = \"\"]\n"
                + "[h:current = 0]\n"
                + "[foreach (propertyname, propnamelist, \"\"), code: {\n"
                + "[h:val = getProperty(propertyname)]"
                + "[h, if (json.isEmpty(val)) : val = \"-\"]"
                + "[h:inputfields = listAppend(inputfields, \"field\" + current + \"|\" + val + \"|\" + propertyname + \"|TEXT\", \"##\")]\n"
                + "[h:current = current + 1]\n"
                + "}]\n"
                + "[h:inputfields = listAppend(inputfields, \"junkvar| |Library properties|LABEL|TEXT=FALSE\", \"##\")]\n"
                + "[foreach (propertyname, libpropnamelist, \"\"), code: {\n"
                + "[h:val = getLibProperty(propertyname, \"Lib:\" + token.name)]"
                + "[h, if (json.isEmpty(val)) : val = \"-\"]"
                + "[h:inputfields = listAppend(inputfields, \"field\" + current + \"|\" + val + \"|\" + propertyname + \"|TEXT\", \"##\")]\n"
                + "[h:current = current + 1]\n"
                + "}]\n"
                + "\n"
                + "[h:check = input (inputfields)]\n"
                + "[h:abort(check)]\n"
                + "\n"
                + "[h:current = 0]\n"
                + "[foreach (propertyname, propnamelist, \"\"), code: {\n"
                + "[h:val = eval(\"field\" + current)]"
                + "[h, if (json.type(val) == \"UNKNOWN\"), code : { [h, if (val == \"-\") : val = \"\"] };{}]\n"
                + "[h: setProperty(propertyname, val)]"
                + "[h: current = current + 1]\n"
                + "}]\n"
                + "[foreach (propertyname, libpropnamelist, \"\"), code: {\n"
                + "[h:val = eval(\"field\" + current)]"
                + "[h, if (json.type(val) == \"UNKNOWN\"), code : { [h, if (val == \"-\") : val = \"\"] };{}]\n"
                + "[h: setLibProperty(propertyname, val, \"Lib:\" + token.name)]\n"
                + "[h: current = current + 1]\n"
                + "}]";
        
        eMacro.getElementsByTagName("command").item(0).setTextContent(maccmd);
        
        return eMacro;        
    }
    
    public Element BuildDebugRefreshConditionsMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("ZZZ Debug");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99021");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Refresh condition bonuses");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("forces all condition bonuses to be refreshed from scratch.");
        
        String maccmd = "[h, macro(\"ForceRefreshAllConditions@Lib:BBFramework\") : \"\"][h:BBrefresh()]";
        
        eMacro.getElementsByTagName("command").item(0).setTextContent(maccmd);
        
        return eMacro;        
    }

    public Element BuildPrefsMacro()
    { 
        Element eMacro = SetUpBlankMacroXml("Preferences");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99030");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Preferences");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("set user preferences.");
        
        String maccmd = "[h:tooltips = getProperty(\"BBPrefs_ShowPowerTooltipInChat\")]\n"
                + "[h:combatsorting = getProperty(\"BBPrefs_CombatFrameSorting\")]\n"
                + "[h:autorefresh = getProperty(\"BBPrefs_CombatFrameAutoRefresh\")]\n"
                + "[h:buttonsperline = getProperty(\"BBPrefs_CombatButtonsInRow\")]\n"
                + "[h:buttoncharlimit = getProperty(\"BBPrefs_CombatButtonCharacterLimit\")]\n"
                + "[h:complinks = getProperty(\"BBPrefs_ShowCompendiumLinksForCombatPowers\")]\n"
                + "[h:rvs = getProperty(\"BBPrefs_HandleRVs\")]\n"
                + "[h:autoproc = getProperty(\"BBPrefs_AutoProcessNotifications\")]\n"
                + "[h:unsublist = getProperty(\"BBPrefs_UnsubscribeList\")]\n"
                + "[h:playername = getPlayerName()]\n"
                + "[h:isinunsublist = json.contains(unsublist, playername)]\n"
                + "[h:addtounsublist = isinunsublist]\n"
                + "\n"
                + "[h:input(\"tooltips|\" + tooltips + \"|Show power tooltips in chat|CHECK\",\n"
                + "\"combatsorting|\" + combatsorting + \"|Sort combat frame by action type *|CHECK\",\n"
                + "\"autorefresh|\" + autorefresh + \"|Auto-refresh combat panel|CHECK\",\n"
                + "\"buttonsperline|\" + buttonsperline + \"|Max combat buttons per line *|TEXT\",\n"
                + "\"buttoncharlimit|\" + buttoncharlimit + \"|Max characters per combat button|TEXT\",\n"
                + "\"complinks|\" + complinks + \"|Show Compendium links for combat powers *|CHECK\",\n"
                + "\"rvs|\" + rvs + \"|Check resistances and vulnerabilities|CHECK\",\n"
                + "\"addtounsublist|\" + addtounsublist + \"|Unsubscribe me (\" + playername + \") from notifications from this token|CHECK\",\n"
                + "\"autoproc|\" + autoproc + \"|Auto-process notifications for this token|CHECK\",\n"
                + "\"junkval|* requires re-initialization||LABEL|span=true\"\n"
                + ")]\n"
                + "\n"
                + "[h:setProperty(\"BBPrefs_ShowPowerTooltipInChat\", tooltips)]\n"
                + "[h:setProperty(\"BBPrefs_CombatFrameSorting\", combatsorting)]\n"
                + "[h:setProperty(\"BBPrefs_CombatFrameAutoRefresh\", autorefresh)]\n"
                + "[h:setProperty(\"BBPrefs_CombatButtonsInRow\", buttonsperline)]\n"
                + "[h:setProperty(\"BBPrefs_CombatButtonCharacterLimit\", buttoncharlimit)]\n"
                + "[h:setProperty(\"BBPrefs_ShowCompendiumLinksForCombatPowers\", complinks)]\n"
                + "[h:setProperty(\"BBPrefs_HandleRVs\", rvs)]\n"
                + "[h:setProperty(\"BBPrefs_AutoProcessNotifications\", autoproc)]\n"
                + "[h, if (!isinunsublist && addtounsublist) : unsublist = json.append(unsublist, playername)]\n"
                + "[h, if (isinunsublist && !addtounsublist) : unsublist = json.removeAll(unsublist, playername)]\n"
                + "[h:setProperty(\"BBPrefs_UnsubscribeList\", unsublist)]\n"
                + "";
        
        eMacro.getElementsByTagName("command").item(0).setTextContent(maccmd);
        
        return eMacro;        
    }

    public Element BuildInitMacro(Boolean isapc)
    { 
        Element eMacro = SetUpBlankMacroXml("BBFramework initialization");
        
        eMacro.getElementsByTagName("index").item(0).setTextContent("99000");
        eMacro.getElementsByTagName("label").item(0).setTextContent("Initialize");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("initializes the character's data, and creates macros for powers, items, etc.");
        if (isapc)
            eMacro.getElementsByTagName("command").item(0).setTextContent("[h,macro('Initialize@Lib:BBFramework'): ''][r,s:'Initialization completed successfully.']");
        else
            eMacro.getElementsByTagName("command").item(0).setTextContent("[h,macro('Initialize@Lib:BBFramework'): '0'][r,s:'Initialization completed successfully.']");
        
        return eMacro;        
    }
    
    public Element BuildStatsMacro(Stats Stats)
    { 
        JSONArray jsonObject = JSONArray.fromObject( Stats );  
        
        Element eMacro = SetUpBlankMacroXml();
        eMacro.getElementsByTagName("index").item(0).setTextContent("99001");
        eMacro.getElementsByTagName("label").item(0).setTextContent("StatsData");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("returns a JSON object that lists the character's basic stats.");
        eMacro.getElementsByTagName("command").item(0).setTextContent(jsonObject.toString(3));
        
        return eMacro;        
    }
    
    public Element BuildAttackMacro(ArrayList Powers)
    {
        ArrayList Attacks = new ArrayList();
         
        for (int i_power = 0; i_power < Powers.size(); i_power++)
        {
            Power thisPower = (Power) Powers.get(i_power);
            if (thisPower.getIsAnAttack())
                Attacks.add(thisPower);
        }                
                
        JSONArray jsonObjectAttacks = JSONArray.fromObject( Attacks );  
        
        Element eMacro = SetUpBlankMacroXml();
        eMacro.getElementsByTagName("index").item(0).setTextContent("99002");
        eMacro.getElementsByTagName("label").item(0).setTextContent("AttackData");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("returns a JSON object that lists the character's attack powers.");
        eMacro.getElementsByTagName("command").item(0).setTextContent(jsonObjectAttacks.toString(3));
        
        return eMacro;        
    }
    
    public Element BuildUtilitiesMacro(ArrayList Powers)
    {
        ArrayList Utilities = new ArrayList();
         
        for (int i_power = 0; i_power < Powers.size(); i_power++)
        {
            Power thisPower = (Power) Powers.get(i_power);
            if (!thisPower.getIsAnAttack() && !thisPower.getIsAnItemPower())
                Utilities.add(thisPower);
        }                
                
        JSONArray jsonObjectUtilities = JSONArray.fromObject( Utilities );  
        
        Element eMacro = SetUpBlankMacroXml();
        eMacro.getElementsByTagName("index").item(0).setTextContent("99003");
        eMacro.getElementsByTagName("label").item(0).setTextContent("UtilitiesData");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("returns a JSON object that lists the character's utility powers.");
        eMacro.getElementsByTagName("command").item(0).setTextContent(jsonObjectUtilities.toString(3));
        
        return eMacro;        
    }
    
    public Element BuildItemPowersMacro(ArrayList Powers)
    {
        ArrayList IPs = new ArrayList();
         
        for (int i_power = 0; i_power < Powers.size(); i_power++)
        {
            Power thisPower = (Power) Powers.get(i_power);
            if (thisPower.getIsAnItemPower())
                IPs.add(thisPower);
        }                
                
        JSONArray jsonObjectIPs = JSONArray.fromObject( IPs );  
        
        Element eMacro = SetUpBlankMacroXml();
        eMacro.getElementsByTagName("index").item(0).setTextContent("99004");
        eMacro.getElementsByTagName("label").item(0).setTextContent("ItemPowersData");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("returns a JSON object that lists the character's item powers.");
        eMacro.getElementsByTagName("command").item(0).setTextContent(jsonObjectIPs.toString(3));
        
        return eMacro;        
    }
    
    public Element BuildItemsMacro(ArrayList Items)
    {
        JSONArray jsonObject = JSONArray.fromObject( Items );  
        
        Element eMacro = SetUpBlankMacroXml();
        eMacro.getElementsByTagName("index").item(0).setTextContent("99005");
        eMacro.getElementsByTagName("label").item(0).setTextContent("ItemData");
        eMacro.getElementsByTagName("toolTip").item(0).setTextContent("returns a JSON object that lists the character's gear.");
        eMacro.getElementsByTagName("command").item(0).setTextContent(jsonObject.toString(3));
        
        return eMacro;        
    }
    
    public Element SetUpBlankMacroXml()
    {
        return SetUpBlankMacroXml("BBFramework data");
    }
    
    public Element SetUpBlankMacroXml(String group)
    {
        Element rootElement = macrosDoc.createElement("net.rptools.maptool.model.MacroButtonProperties");
        Element element = macrosDoc.createElement("saveLocation");
        element.setTextContent("Token");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("index");
        element.setTextContent("");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("colorKey");
        element.setTextContent("default");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("hotKey");
        element.setTextContent("Node");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("command");
        element.setTextContent("");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("label");
        element.setTextContent("");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("group");
        element.setTextContent(group);
        rootElement.appendChild(element);
        element = macrosDoc.createElement("sortby");
        element.setTextContent(" ");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("autoExecute");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("includeLabel");
        element.setTextContent("false");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("applyToTokens");
        element.setTextContent("false");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("fontColorKey");
        element.setTextContent("black");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("fontSize");
        element.setTextContent("1.00em");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("minWidth");
        element.setTextContent("");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("maxWidth");
        element.setTextContent("");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("allowPlayerEdits");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("toolTip");
        element.setTextContent("");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("commonMacro");
        element.setTextContent("false");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("compareGroup");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("compareSortPrefix");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("compareCommand");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("compareIncludeLabel");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("compareAutoExecute");
        element.setTextContent("true");
        rootElement.appendChild(element);
        element = macrosDoc.createElement("compareApplyToSelectedTokens");
        element.setTextContent("true");
        rootElement.appendChild(element);
       return rootElement;
    }

    
    
    public void Export(String AbsoluteOutputFilename)
    {
        File file = new File("content.xml");
        File propfile = new File("properties.xml");
        try {            

            // Write the DOM document to the file
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("indent-number", new Integer(4));
            Transformer xformer = factory.newTransformer();
            xformer.setOutputProperty("indent", "yes");
            
             // Prepare the output file
            Result result = new StreamResult("content.xml");
           // Prepare the DOM document for writing
            Source source = new DOMSource(macrosDoc);
            xformer.transform(source, result);
            
             // Prepare the output file
            result = new StreamResult("properties.xml");
            // Prepare the DOM document for writing
            source = new DOMSource(propertiesDoc);
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }       

        String[] source = new String[]{"content.xml", "properties.xml"};

        // Create a buffer for reading the files
        byte[] buf = new byte[1024];

        try {
            // Create the ZIP file
            String target = AbsoluteOutputFilename;
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(target));

            // Compress the files
            for (int i=0; i<source.length; i++) {
                FileInputStream in = new FileInputStream(source[i]);

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(source[i]));

                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                in.close();
            }

            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
        }
        
        propfile.delete();
        file.delete();
        
 
    }
    
}
