/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characterimporter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import org.xml.sax.SAXException;
import java.util.regex.*;
import java.util.ArrayList;

/**
 *
 * @author Family
 */
public class DndFileImporter {
    
    private Document DndXml;
    public CharacterData Data;
    
    public DndFileImporter(String inFile)
    {
        try {
            File fXmlFile = new File(inFile);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            DndXml = dBuilder.parse(fXmlFile);
            DndXml.getDocumentElement().normalize();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DndFileImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DndFileImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DndFileImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public CharacterData Import()
    {
        Data = new CharacterData();
        
        switch (GetDataType()) {
            case 1:
                Data.Stats = ReadPCStats();
                Data.Items = ReadPCItems();
                Data.Powers.addAll(ReadPCPowers());
                Data.isAPC = true;
                break;
            case 2:
                Data.Stats = ReadMonsterStats();
                Data.Powers = ReadMonsterPowers();
                Data.isAPC = false;
                break;
        }
        
        return Data;
    }
    
    int GetDataType()
    {
        Element rootelem = DndXml.getDocumentElement();
        if (rootelem.getTagName().equals("D20Character"))
        {
            return 1;
        }
        if (rootelem.getTagName().equals("Monster"))
        {
            return 2;
        }
        return 0;
    }
    
    public String FormatStringForJson(String inString)
    {
        return inString.replace("'", "&#39;")
                .replace("\n", "<br>")
                .replace("\t", "")
                .replace("\"", "&quot;")
                .replace("–", "-")
                .replace("•", "&#149;")
                .replace("’", "&#39;")
                .trim();
    }
    
    int GetIntValueFromChildTag(Element e, String childTag)
    {
        String raw = GetStringValueFromChildTag(e, childTag);
        if (raw.isEmpty())
            return 0;
        return Integer.parseInt(raw);
    }
    
    Boolean GetBooleanValueFromChildTag(Element e, String childTag)
    {
        String raw = GetStringValueFromChildTag(e, childTag);
        if (raw.isEmpty())
            return false;
        return Boolean.parseBoolean(raw);
    }
    
    String GetStringValueFromChildTag(Element e, String childTag)
    {
        NodeList children = e.getElementsByTagName(childTag);
        if (children.getLength() < 1)
            return "";
        Element eChild = (Element)children.item(0);
        return FormatStringForJson(eChild.getTextContent());
    }
    
    String GetStringValueFromFirstLevelChildTag(Element e, String childTag)
    {
        NodeList children = e.getChildNodes();
        for (int iTag = 0; iTag < children.getLength(); iTag++)
        {
            if (children.item(iTag).getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element eChild = (Element)children.item(iTag);
            if (eChild.getTagName().equals(childTag))
            {
                return FormatStringForJson(eChild.getTextContent());
            }
        }
        return "";       
    }
    
    int GetIntFinalValueFromChildTag(Element e, String childTag)
    {
        NodeList children = e.getElementsByTagName(childTag);
        if (children.getLength() < 1)
            return 0;
        Element eChild = (Element)children.item(0);
        int ifv = 0;
        String sfv = eChild.getAttribute("FinalValue");
        if (!sfv.isEmpty())
            ifv = Integer.parseInt(sfv);
        return ifv;
    }

    ArrayList GetStringListFromChildNameTags(Element e, String childTag)
    {
        ArrayList names = new ArrayList();
        
        NodeList children = e.getElementsByTagName(childTag);
        if (children.getLength() < 1)
            return names;
        Element eChild = (Element)children.item(0);
        NodeList nametags = eChild.getElementsByTagName("Name");
        for (int iTag = 0; iTag < nametags.getLength(); iTag++)
        {
            names.add(FormatStringForJson(nametags.item(iTag).getTextContent()));
        }
        return names;
    }    
    
    NameValueDetails GetNVDFromChild(Element e, String childTag)
    {
        return GetNVDFromChild(e, childTag, "Details");
    }
    
    NameValueDetails GetNVDFromChild(Element e, String childTag, String desctag)
    {
        String name = GetStringValueFromChildNameTag(e, childTag);
        int value = GetIntFinalValueFromChildTag(e, childTag);
        String details = GetStringValueFromChildNameTag(e, childTag, desctag);        
        return new NameValueDetails(name, value, details);
    }

    String GetStringValueFromChildNameTag(Element e, String childTag)
    {
        return GetStringValueFromChildNameTag(e, childTag, "Name");
    }

    String GetStringValueFromChildNameTag(Element e, String childTag, String childNameTag)
    {
        NodeList children = e.getElementsByTagName(childTag);
        if (children.getLength() < 1)
            return "";
        Element eChild = (Element)children.item(0);
        NodeList nametags = eChild.getElementsByTagName(childNameTag);
        if (nametags.getLength() < 1)
            return "";
        return FormatStringForJson(nametags.item(0).getTextContent());
    }

    public int GetValueOfStatWithName(Element parent, String name)
    {
        Element e = GetStatWithName(parent, name);
        String s = "";
        if (e != null)
            s = e.getAttribute("value");
        if (!s.isEmpty())
            return Integer.parseInt(s);
        return 0;
    }
   
    public Element GetStatWithName(Element parent, String name)
    {
        NodeList children = parent.getChildNodes();
        for (int iChild = 0; iChild < children.getLength(); iChild++)
        {
            NodeList subs = children.item(iChild).getChildNodes();
            for (int iSub = 0; iSub < subs.getLength(); iSub++)
            {
                if (subs.item(iSub).getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element subelement = (Element)subs.item(iSub);
                if (subelement.getTagName().equals("alias") && subelement.getAttribute("name").equals(name))
                    return (Element)children.item(iChild);
            }
        }
        return null;
    }
    
    public Defense SetUpDefense(Element parent, String name)
    {
        Defense thisDefense = new Defense();
        Element e = GetStatWithName(parent, name);
        thisDefense.setValue(Integer.parseInt(e.getAttribute("value")));
        return thisDefense;
    }
    public Ability SetUpAbility(Element parent, String name)
    {
        Ability thisAbility = new Ability();
        Element e = GetStatWithName(parent, name);
        thisAbility.setScore(Integer.parseInt(e.getAttribute("value")));
        e = GetStatWithName(parent, name + " modifier");
        thisAbility.setModifier(Integer.parseInt(e.getAttribute("value")));
        return thisAbility;
    }
    public Skill SetUpSkill(Element parent, String name)
    {
        Skill thisSkill = new Skill();
        Element e = GetStatWithName(parent, name);
        thisSkill.setBonus(Integer.parseInt(e.getAttribute("value")));
        e = GetStatWithName(parent, name + " Trained");
        thisSkill.setIsTrained(Integer.parseInt(e.getAttribute("value")));
        
        Element eRuleElementTally = (Element)DndXml.getElementsByTagName("RulesElementTally").item(0);
        NodeList children = eRuleElementTally.getElementsByTagName("RulesElement");
        for (int iRE = 0; iRE < children.getLength(); iRE++)
        {
            Element thisElem = (Element)children.item(iRE);
            if (thisElem.getAttribute("name").equals(name))
            {
                thisSkill.setUrl(thisElem.getAttribute("url"));
                break;
            }
        }
        
        return thisSkill;
    }
    
    public String GetDetail(String s)
    {
        Element eDetailsBlock = (Element)DndXml.getElementsByTagName("Details").item(0);
        return FormatStringForJson(eDetailsBlock.getElementsByTagName(s).item(0).getTextContent());
    }
    
    public Element GetPowerRuleElement(String parent, String attribute, String value)
    {
        Element eRuleElementTally = (Element)DndXml.getElementsByTagName(parent).item(0);
        NodeList children = eRuleElementTally.getElementsByTagName("RulesElement");
        for (int iRE = 0; iRE < children.getLength(); iRE++)
        {
            Element thisElem = (Element)children.item(iRE);
            if (thisElem.getAttribute(attribute).equals(value) && thisElem.getAttribute("type").equals("Power"))
            {
                return thisElem;
            }
        }
         return null;
    }

    public Element GetRuleElement(String parent, String attribute, String value)
    {
        Element eRuleElementTally = (Element)DndXml.getElementsByTagName(parent).item(0);
        NodeList children = eRuleElementTally.getElementsByTagName("RulesElement");
        for (int iRE = 0; iRE < children.getLength(); iRE++)
        {
            Element thisElem = (Element)children.item(iRE);
            if (thisElem.getAttribute(attribute).equals(value))
            {
                return thisElem;
            }
        }
         return null;
    }
    
    public ArrayList GetRuleElements(String parent, String attribute, String value)
    {
        ArrayList returnElements = new ArrayList();
        Element eRuleElementTally = (Element)DndXml.getElementsByTagName(parent).item(0);
        NodeList children = eRuleElementTally.getElementsByTagName("RulesElement");
        for (int iRE = 0; iRE < children.getLength(); iRE++)
        {
            Element thisElem = (Element)children.item(iRE);
            if (thisElem.getAttribute(attribute).equals(value))
            {
                returnElements.add(thisElem);
            }
        }
         return returnElements;
    }
    
    public Stats ReadPCStats()
    {
        PCStats Stats = new PCStats();
        
        Details deets = new Details();
        deets.setAge(GetDetail("Age"));
        deets.setAlignment(GetDetail("Alignment"));
        deets.setAppearance(GetDetail("Appearance"));
        deets.setCarriedMoney(GetDetail("CarriedMoney"));
        deets.setCompanions(GetDetail("Companions"));
        deets.setCompany(GetDetail("Company"));
        deets.setExperience(GetDetail("Experience"));
        deets.setGender(GetDetail("Gender"));
        deets.setHeight(GetDetail("Height"));
        deets.setName(GetDetail("name"));
        deets.setNotes(GetDetail("Notes"));
        deets.setPlayer(GetDetail("Player"));
        deets.setStoredMoney(GetDetail("StoredMoney"));
        deets.setTraits(GetDetail("Traits"));
        deets.setWeight(GetDetail("Weight"));
        Stats.setDetails(deets);
        
        Element eStatBlock = (Element)DndXml.getElementsByTagName("StatBlock").item(0);
        
        Stats.getAbilities().setStrength(SetUpAbility(eStatBlock, "Strength"));
        Stats.getAbilities().setConstitution(SetUpAbility(eStatBlock, "Constitution"));
        Stats.getAbilities().setDexterity(SetUpAbility(eStatBlock, "Dexterity"));
        Stats.getAbilities().setIntelligence(SetUpAbility(eStatBlock, "Intelligence"));
        Stats.getAbilities().setWisdom(SetUpAbility(eStatBlock, "Wisdom"));
        Stats.getAbilities().setCharisma(SetUpAbility(eStatBlock, "Charisma"));
        
        Stats.getSkills().setAcrobatics(SetUpSkill(eStatBlock, "Acrobatics"));
        Stats.getSkills().setArcana(SetUpSkill(eStatBlock, "Arcana"));
        Stats.getSkills().setAthletics(SetUpSkill(eStatBlock, "Athletics"));
        Stats.getSkills().setBluff(SetUpSkill(eStatBlock, "Bluff"));
        Stats.getSkills().setDiplomacy(SetUpSkill(eStatBlock, "Diplomacy"));
        Stats.getSkills().setDungeoneering(SetUpSkill(eStatBlock, "Dungeoneering"));
        Stats.getSkills().setEndurance(SetUpSkill(eStatBlock, "Endurance"));
        Stats.getSkills().setHeal(SetUpSkill(eStatBlock, "Heal"));
        Stats.getSkills().setHistory(SetUpSkill(eStatBlock, "History"));
        Stats.getSkills().setInsight(SetUpSkill(eStatBlock, "Insight"));
        Stats.getSkills().setIntimidate(SetUpSkill(eStatBlock, "Intimidate"));
        Stats.getSkills().setNature(SetUpSkill(eStatBlock, "Nature"));
        Stats.getSkills().setPerception(SetUpSkill(eStatBlock, "Perception"));
        Stats.getSkills().setReligion(SetUpSkill(eStatBlock, "Religion"));
        Stats.getSkills().setStealth(SetUpSkill(eStatBlock, "Stealth"));
        Stats.getSkills().setStreetwise(SetUpSkill(eStatBlock, "Streetwise"));
        Stats.getSkills().setThievery(SetUpSkill(eStatBlock, "Thievery"));
        
        Stats.getDefenses().setAC(SetUpDefense(eStatBlock, "Armor Class"));
        Stats.getDefenses().setFortitude(SetUpDefense(eStatBlock, "Fortitude Defense"));
        Stats.getDefenses().setReflex(SetUpDefense(eStatBlock, "Reflex Defense"));
        Stats.getDefenses().setWill(SetUpDefense(eStatBlock, "Will Defense"));
        
        Stats.setDeathSaves(GetValueOfStatWithName(eStatBlock, "Death Saves Count"));
        Stats.setLevel(GetValueOfStatWithName(eStatBlock, "Level"));
        Stats.setHalfLevel(GetValueOfStatWithName(eStatBlock, "HALF-LEVEL"));
        Stats.setMaxHP(GetValueOfStatWithName(eStatBlock, "Hit Points"));
        Stats.setPowerPoints(GetValueOfStatWithName(eStatBlock, "Power Points"));
        Stats.setHealingSurges(GetValueOfStatWithName(eStatBlock, "Healing Surges"));
        Stats.setInitiativeBonus(GetValueOfStatWithName(eStatBlock, "Initiative"));
        Stats.setXPNeeded(GetValueOfStatWithName(eStatBlock, "XP Needed"));
        Stats.setSpeed(GetValueOfStatWithName(eStatBlock, "Speed"));
        Stats.setRingSlots(GetValueOfStatWithName(eStatBlock, "Ring Slots"));
        Stats.setSurgeBonus(GetValueOfStatWithName(eStatBlock, "Healing Surge Value"));
        
        
        NameUrl race = new NameUrl();
        Element raceElem = GetRuleElement("RulesElementTally", "type", "Race");
        if (raceElem != null)
        {
            race.setName(raceElem.getAttribute("name"));
            race.setUrl(raceElem.getAttribute("url"));
            Stats.setRace(race);
        }
        NameUrl dclass = new NameUrl();
        Element classElem = GetRuleElement("RulesElementTally", "type", "Class");
        if (classElem != null)
        {
            dclass.setName(classElem.getAttribute("name"));
            dclass.setUrl(classElem.getAttribute("url"));
            Stats.setDndClass(dclass);
        }
        NameUrl pp = new NameUrl();
        Element ppElem = GetRuleElement("RulesElementTally","type", "Paragon Path");
        if (ppElem != null)
        {
            pp.setName(ppElem.getAttribute("name"));
            pp.setUrl(ppElem.getAttribute("url"));
            Stats.setParagonPath(pp);
        }
        ArrayList langElems = GetRuleElements("RulesElementTally","type", "Language");
        for (int iLang = 0; iLang < langElems.size(); iLang++)
        {
            Element thisElem = (Element)langElems.get(iLang);
            Stats.getLanguages().add(thisElem.getAttribute("name"));
        }
        Element sizeElem = GetRuleElement("RulesElementTally", "type", "Size");
        if (sizeElem != null)
        {
            Stats.setSize(sizeElem.getAttribute("name"));
        }
        Element alignElem = GetRuleElement("RulesElementTally", "type", "Alignment");
        if (alignElem != null)
        {
            Stats.setAlignment(alignElem.getAttribute("name"));
        }
        
        Stats.Feats = ReadPCFeats();

        return Stats;
    }
    
    public ArrayList ReadPCFeats()
    {
        ArrayList Feats = new ArrayList();
        Element eRETally = (Element)DndXml.getElementsByTagName("RulesElementTally").item(0);
        NodeList nREs = eRETally.getElementsByTagName("RulesElement");
        for (int iRE = 0; iRE < nREs.getLength(); iRE++) {
            Element eThisRE = (Element)nREs.item(iRE);
            if (eThisRE.getAttribute("type").equals("Feat"))
            {
                Feat thisFeat = new Feat();
                thisFeat.setName(FormatStringForJson(eThisRE.getAttribute("name")));
                thisFeat.setUrl(eThisRE.getAttribute("url")); 
                thisFeat.setFlavor(GetValueFromSpecificChildWithName(eThisRE, "Flavor"));
                for (int child = 0; child < eThisRE.getChildNodes().getLength(); child++)
                {
                    Node nChild = eThisRE.getChildNodes().item(child);
                    if (nChild.getNodeType() == Node.TEXT_NODE)
                    {
                        thisFeat.setDescription(FormatStringForJson(nChild.getTextContent()));
                    }
                }
                Feats.add(thisFeat);
            }   
        }
        return Feats;
    }
    
    public ArrayList ReadPCItems()
    {
        ArrayList Items = new ArrayList();
        Node nLoot = DndXml.getElementsByTagName("LootTally").item(0);
        Element eList = (Element) nLoot;
        NodeList nLootList = eList.getElementsByTagName("loot");
        for (int iLootItem = 0; iLootItem < nLootList.getLength(); iLootItem++) 
        {
            Element nThisLoot = (Element)nLootList.item(iLootItem);
            int quantity = Integer.parseInt(nThisLoot.getAttribute("count").trim());
            if (quantity < 1)
                continue;
            NodeList nRuleElements = nThisLoot.getElementsByTagName("RulesElement");
            
            Item thisItem = new Item();
            thisItem.setQuantity(quantity);
            int equipped = Integer.parseInt(nThisLoot.getAttribute("equip-count").trim());
            if (equipped > 0)
                thisItem.setIsEquipped(1);
            for (int iRuleElement = 0; iRuleElement < nRuleElements.getLength(); iRuleElement++)
            {
                Element thisElement = (Element)nRuleElements.item(iRuleElement);
                if (thisElement.getParentNode() != nThisLoot)
                    continue; // take only RulesElement that are first-level children of the loot item
                if (iRuleElement == 0) //take these values from initial entry only
                {
                    thisItem.setType( thisElement.getAttribute("type").trim() ); 
                    thisItem.setGroup( GetValueFromSpecificChildWithName(thisElement, "Group") ); 
                    thisItem.setSlot( GetValueFromSpecificChildWithName(thisElement, "Item Slot") );
                    thisItem.setProperties( GetValueFromSpecificChildWithName(thisElement, "Properties") ); 
                    Pattern pDamage = Pattern.compile("(\\d)d(\\d)");
                    Matcher mDamage = pDamage.matcher(GetValueFromSpecificChildWithName(thisElement, "Damage"));
                    if (mDamage.find())
                    {
                        thisItem.setDamageMultiplier( Integer.parseInt(mDamage.group(1)) );
                        thisItem.setDamageDie( Integer.parseInt(mDamage.group(2)) );
                    }
                }
                NameUrl thisName = new NameUrl();
                String recordname = FormatStringForJson(thisElement.getAttribute("name").trim());
                thisName.setName(recordname);
                thisName.setUrl(thisElement.getAttribute("url").trim());
                thisItem.getNames().add( thisName );
                String idSoFar = thisItem.getID();
                String internalIdSoFar = thisItem.getInternalID();
                idSoFar = idSoFar.concat(thisElement.getAttribute("charelem"));
                internalIdSoFar = internalIdSoFar.concat(thisElement.getAttribute("internal-id"));
                thisItem.setID(idSoFar);
                thisItem.setInternalID(internalIdSoFar);
                String magicItemType = GetValueFromSpecificChildWithName(thisElement, "Magic Item Type");
                thisItem.setMagicItemType(magicItemType);
                if (magicItemType.equals("Holy Symbol") || magicItemType.equals("Orb") || magicItemType.equals("Rod") || 
                        magicItemType.equals("Staff") || magicItemType.equals("Tome") || magicItemType.equals("Totem") || 
                        magicItemType.equals("Wand") || magicItemType.equals("Ki Focus") ||
                        recordname.equals("Holy Symbol") || recordname.equals("Orb") || recordname.equals("Rod") || 
                        recordname.equals("Staff") || recordname.equals("Tome") || recordname.equals("Totem") || 
                        recordname.equals("Wand") || recordname.equals("Ki Focus"))
                    thisItem.setIsAnImplement(1);
                
                if (magicItemType.equals("Staff"))
                {
                    thisItem.setProficiencyBonus(2);
                    thisItem.setDamageDie(8);
                    thisItem.setDamageMultiplier(1);
                }
                
                String profbonus = GetValueFromSpecificChildWithName(thisElement,"Proficiency Bonus");
                if (!profbonus.isEmpty())
                    thisItem.setProficiencyBonus( thisItem.getProficiencyBonus() + Integer.parseInt(profbonus) );
                
                // split "Enhancement" into plus and to-hit/damage
                String enhancement = GetValueFromSpecificChildWithName(thisElement, "Enhancement");
                int bonus = 0;
                if (!enhancement.isEmpty())
                {
                    Pattern p = Pattern.compile("\\+(\\d)");
                    Matcher m = p.matcher(enhancement);
                    if (m.find())
                    {
                        bonus = Integer.parseInt(m.group(1));
                        if (enhancement.contains("attack rolls"))
                            thisItem.setItemBonusToHit( thisItem.getItemBonusToHit() + bonus);
                        if (enhancement.contains("damage rolls"))
                            thisItem.setItemBonusToDamage( thisItem.getItemBonusToDamage() + bonus);
                    }
                }
                // split "Critical" into damage and damage types
                String critical = GetValueFromSpecificChildWithName(thisElement, "Critical");
                if (!critical.isEmpty())
                {
                    Boolean perPlus = false;
                    if (critical.contains("per plus"))
                        perPlus = true;
                    Pattern p = Pattern.compile("\\+(\\d)(d\\d) (.*)");
                    Matcher m = p.matcher(critical);
                    // TODO:handle items with "+xdx damage, or +ydy damage against..."
                    // make critical into an array
                    if (m.find())
                    {
                        int multiplier = Integer.parseInt(m.group(1)) * bonus;                       
                        thisItem.setCriticalDamage( Integer.toString(multiplier) + m.group(2));
                        String damagetypes = m.group(3);
                        if (!damagetypes.isEmpty())
                            thisItem.setCriticalDamageType( thisItem.getCriticalDamageType() + damagetypes.replace(" per plus", ""));
                    }
                }
                // make powers into real powers!
                NodeList childlist = thisElement.getElementsByTagName("specific");
                int powercount = 1;
                for (int iChild = 0; iChild < childlist.getLength(); iChild++) 
                {
                    Element thisChild = (Element)childlist.item(iChild);
                    if (thisChild.getParentNode() != thisElement)
                        continue; // take only specific elements that are first-level children of the ruleselement
                    String tagname = thisChild.getAttribute("name");
                    String content = FormatStringForJson(thisChild.getTextContent());
                    if (tagname.startsWith("_") || 
                            tagname.equals("Level") || 
                            tagname.equals("Class") || 
                            tagname.equals("Power Type") ||
                            content.isEmpty()
                        ) { continue; }
                    
                    thisItem.getChildTagNames().add(tagname.replaceAll("^ ", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
                    thisItem.getChildTagContents().add(content);
                    
                    if (thisChild.getAttribute("name").equals("Power"))
                    {
                        if (!thisChild.hasChildNodes())
                            continue;
                        Power newPower = new Power();
                        Pattern pPower = Pattern.compile("Power(.*?(Standard Action|Minor Action|Move Action|Free Action|Immediate Interrupt|Immediate Reaction|Immediate Reaction Action|No Action)(\\.|\\)))(.*)", Pattern.DOTALL);
                        Matcher mPower = pPower.matcher(thisChild.getTextContent());
                        if (mPower.find())
                        {
                            newPower.setEffect(FormatStringForJson(mPower.group(4)).trim());
                            newPower.setFrequency("At-Will");
                            if (mPower.group(1).contains("Daily"))
                                newPower.setFrequency("Daily");
                            if (mPower.group(1).contains("Encounter"))
                                newPower.setFrequency("Encounter");
                            
                            newPower.setActionType(mPower.group(2));
                            String num = "";
                            if (powercount > 1)
                                num = " " + String.valueOf(powercount);
                            newPower.setName(FormatStringForJson("~" + thisElement.getAttribute("name").replaceAll(" \\+\\d", "") + num));
                            newPower.setUrl(thisElement.getAttribute("url").trim());
                            newPower.setIsAnItemPower(true);
                        }
                        powercount++;
                        if (thisChild.getTextContent().contains("Trigger:"))
                        {
                            newPower.setHasTrigger(true);
                        }
                        newPower.setItemID(thisItem.getID());
                        newPower.setCategory("Item Powers");
                        Data.Powers.add(newPower);
                        thisItem.getPowers().add(newPower.getName());
                    }
                }
            }
            Items.add(thisItem);     
        }         
        return Items;
    }
    
    public String GetValueFromSpecificChildWithName(Element parent, String name)
    {
        NodeList childList = parent.getElementsByTagName("specific");
        for (int iChild = 0; iChild < childList.getLength(); iChild++) 
        {
            Element thisChild = (Element)childList.item(iChild);
            if (thisChild.getAttribute("name").trim().equals(name))
                return FormatStringForJson(thisChild.getTextContent());
        }
        return "";
    }
    
    public ArrayList ReadPCPowers()
    {
        ArrayList Powers = new ArrayList();
        Element eRETally = (Element)DndXml.getElementsByTagName("RulesElementTally").item(0);
        NodeList nREs = eRETally.getElementsByTagName("RulesElement");
        Element ePowerStatList = (Element)DndXml.getElementsByTagName("PowerStats").item(0);
        NodeList nPowerStats = ePowerStatList.getElementsByTagName("Power");
        for (int iPower = 0; iPower < nPowerStats.getLength(); iPower++) 
        {
            Element eThisPower = (Element)nPowerStats.item(iPower);
   
            Power thisPower = new Power();
            String powername = eThisPower.getAttribute("name");
            thisPower.setName(FormatStringForJson(powername));
            Element eThisPowerRE = GetPowerRuleElement("RulesElementTally", "name", powername);
            thisPower.setUrl(eThisPowerRE.getAttribute("url"));
            
            NodeList specificChildList = eThisPower.getElementsByTagName("specific");
            for (int iChild = 0; iChild < specificChildList.getLength(); iChild++) 
            {
                Element thisChildXmlElement = (Element)specificChildList.item(iChild);
                String tagname = thisChildXmlElement.getAttribute("name");
                if (tagname.startsWith("_") || 
                        tagname.equals("Level") || 
                        tagname.equals("Class") || 
                        tagname.equals("Power Type")
                    ) { continue; }
                thisPower.getChildTagNames().add(tagname.replaceAll("^ ", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
                thisPower.getChildTagContents().add(FormatStringForJson(thisChildXmlElement.getTextContent()));
            }            
            
            thisPower.setFlavor(GetValueFromSpecificChildWithName(eThisPower, "Flavor")); 
            thisPower.setFrequency(GetValueFromSpecificChildWithName(eThisPower, "Power Usage").replace(" (Special)", ""));
            thisPower.setKeywords(GetValueFromSpecificChildWithName(eThisPower, "Keywords")); 
            thisPower.setActionType(GetValueFromSpecificChildWithName(eThisPower, "Action Type"));
            thisPower.setAttackRange(GetValueFromSpecificChildWithName(eThisPower, "Attack Type")); 
            thisPower.setPowerType(GetValueFromSpecificChildWithName(eThisPower, "Power Type"));
            
            if (!GetValueFromSpecificChildWithName(eThisPower, "Trigger").isEmpty())
            { 
                thisPower.setHasTrigger(true);
            }
            
            if (thisPower.getPowerType().equals("Attack") || !GetValueFromSpecificChildWithName(eThisPower, "Attack").isEmpty())
            {
                thisPower.setIsAnAttack(true);
                thisPower.setCategory("Attacks");
            }
            else
            {
                thisPower.setCategory("Utilities");
            }
            // TODO: fix this. Include the Level line if the value is equal to or lower than the PC level.
            int pclevel = Data.Stats.getLevel();
            String effecttext = GetValueFromSpecificChildWithName(eThisPower, "Effect");
            if (pclevel >= 21)
            {
                effecttext = effecttext.replaceAll("Level 11.*$", "");
            }
            if (pclevel < 21)
            {
                effecttext = effecttext.replaceAll("Level 21.*$", "");
            }
            if (pclevel < 11)
            {
                effecttext = effecttext.replaceAll("Level 11.*$", "");
            }
            thisPower.setEffect(effecttext);
            thisPower.setSpecial(GetValueFromSpecificChildWithName(eThisPower, "Special")); 
            String targets = GetValueFromSpecificChildWithName(eThisPower, "Target");
            thisPower.setTarget(targets);
            if (targets.isEmpty())
                targets = GetValueFromSpecificChildWithName(eThisPower, "Primary Target");
            thisPower.setTarget(targets);
            if (!GetValueFromSpecificChildWithName(eThisPower, "Channel Divinity").isEmpty() || thisPower.getKeywords().contains("Channel Divinity"))
                thisPower.setIsChannelDivinity(true);
            if (thisPower.getIsAnAttack())
            {
                AttackConfig config = thisPower.getAttackConfig();
                String opposedPair = GetValueFromSpecificChildWithName(eThisPower, "Attack");
                if (opposedPair.isEmpty())
                    opposedPair = GetValueFromSpecificChildWithName(eThisPower, "Primary Attack");
                config.setAttack(opposedPair);
                config.setMiss(GetValueFromSpecificChildWithName(eThisPower, "Miss").replaceAll("Level.*$", ""));
                if (config.getMiss().contains("Half damage"))
                    config.setHalfDamageOnMiss(true);
                // todo: read hit text from scaling powers like Abjure Undead
                String hit = GetValueFromSpecificChildWithName(eThisPower, "Hit").replaceAll("Level.*$", "");
                if (!hit.matches("^\\d+d\\d+.*") && !hit.matches("^\\d+\\[W\\].*"))
                {   config.setHit(hit); }
                else {
                    Pattern pHit = Pattern.compile("(, and |\\. )(.*\\.)");
                    Matcher mHit = pHit.matcher(hit);
                    if (mHit.find())
                        config.setHit(mHit.group(2));
                }
                if (thisPower.getKeywords().contains("Weapon"))
                    config.setDelivery("Weapon");
                else
                    config.setDelivery("Implement");         
            

                NodeList weaponList = eThisPower.getElementsByTagName("Weapon");
                for (int iWeapon = 0; iWeapon < weaponList.getLength(); iWeapon++)
                {
                    Element eWeapon = (Element)weaponList.item(iWeapon);
                    AttackWeaponStats thisWeapon = new AttackWeaponStats();
                    thisWeapon.setName(eWeapon.getAttribute("name"));
                    thisWeapon.setAttackBonus(GetIntValueFromChildTag(eWeapon, "AttackBonus"));
                    thisWeapon.setDamage(GetStringValueFromChildTag(eWeapon, "Damage"));
                    thisWeapon.setAttackStat(GetStringValueFromChildTag(eWeapon, "AttackStat"));
                    
                    //set weapon id
                    String id = "";
                    String internalid = "";
                    NodeList weaponrecordlist = eWeapon.getElementsByTagName("RulesElement");
                    for (int iweaponRE = 0; iweaponRE < weaponrecordlist.getLength(); iweaponRE++)
                    {
                        Element eWeaponRE = (Element)weaponrecordlist.item(iweaponRE);
                        if (eWeaponRE.getParentNode() != eWeapon)
                            continue;
                        id = id.concat(eWeaponRE.getAttribute("charelem"));
                        internalid = internalid.concat(eWeaponRE.getAttribute("internal-id"));
                    }
                    thisWeapon.setWeaponID(id);
                    thisWeapon.setWeaponInternalID(internalid);
                    
                    String defense = GetStringValueFromChildTag(eWeapon, "Defense");
                    if(defense.equals("AC") || defense.equals("Reflex") || defense.equals("Fortitude") || defense.equals("Will"))
                    {
                        thisWeapon.setDefense(defense);
                    }
                    else {
                        String baseOpposedPair = thisPower.getAttackConfig().getAttack();
                        Pattern pDef = Pattern.compile("vs. (.*)");
                        Matcher mDef = pDef.matcher(baseOpposedPair);
                        if (mDef.find())
                            thisWeapon.setDefense(mDef.group(1));
                    }
                    thisWeapon.setCritRange(GetStringValueFromChildTag(eWeapon, "CritRange"));
                    thisWeapon.setCritThreshold(thisWeapon.getCritRange().substring(0, 2));
                    thisWeapon.setConditions(GetStringValueFromChildTag(eWeapon, "Conditions"));
                    thisWeapon.setHitComponents(GetStringValueFromChildTag(eWeapon, "HitComponents"));
                    thisWeapon.setDamageComponents(GetStringValueFromChildTag(eWeapon, "DamageComponents"));
                    String critcontent = GetStringValueFromChildTag(eWeapon, "CritDamage");
                    if (!critcontent.isEmpty())
                    {
                        Pattern pCritBase = Pattern.compile("^[\\d]*");
                        Matcher mCritBase = pCritBase.matcher(critcontent);
                        if (mCritBase.find())
                            thisWeapon.setCriticalBaseDamage(Integer.parseInt(mCritBase.group(0)));

                        Pattern pCritComponents = Pattern.compile("\\+.*?(\\.|$)");
                        Matcher mCritComponents = pCritComponents.matcher(critcontent);
                        if (!mCritComponents.find())
                        {
                            String allcritminusbase = "";
                            Pattern pGetAllCritMinusBase = Pattern.compile("^[\\d]*(.*)");
                            Matcher mGetAllCritMinusBase = pGetAllCritMinusBase.matcher(critcontent);
                            if (mGetAllCritMinusBase.find())
                            {
                                allcritminusbase = mGetAllCritMinusBase.group(1);
                                CritComponent allCrit = new CritComponent();
                                allCrit.setBaseText(allcritminusbase);
                                thisWeapon.getCriticalExtraDamageComponents().add(allCrit); 
                            }
                        }
                        mCritComponents.reset();
                        while (mCritComponents.find())
                        {
                            String assembled = mCritComponents.group(0);
                            Pattern pTimes = Pattern.compile("(\\d+)(d\\d+[^\\+]*? )per plus");
                            Matcher mTimes = pTimes.matcher(mCritComponents.group(0));
                            while(mTimes.find())
                            {
                                int enhancement = 0;
                                for (int iItem = 0; iItem < Data.Items.size(); iItem++)
                                {
                                    Item thisItem = (Item)Data.Items.get(iItem);
                                    if (thisItem.getInternalID().equals(thisWeapon.getWeaponInternalID()))
                                    {
                                        enhancement = thisItem.getItemBonusToHit();
                                    }
                                    else if (thisWeapon.getName().contains("Ki Focused") && 
                                            thisWeapon.getWeaponInternalID().contains(thisItem.getInternalID()))
                                    {
                                        enhancement = thisItem.getItemBonusToHit();
                                    }
                                }
                                int multiplier = Integer.parseInt(mTimes.group(1));
                                String newstring = (enhancement * multiplier) + mTimes.group(2);
                                assembled = assembled.replaceAll(mTimes.group(0), newstring);
                            }
                            
                            CritComponent thisCrit = new CritComponent();
                            
                            Pattern pRolls = Pattern.compile("\\+(\\d+d\\d+)");
                            Matcher mRolls = pRolls.matcher(assembled);
                            while (mRolls.find())
                            {
                                thisCrit.getRolls().add(mRolls.group(1));
                            }
                            assembled = assembled.replaceAll("\\d+d\\d+", "%d");
                            thisCrit.setBaseText(assembled);
                            
                            thisWeapon.getCriticalExtraDamageComponents().add(thisCrit);
                        }
                    }
                    
                    // handle special properties on the weapon that may not be included in the base stats
                    Item weaponItemRecord = null;
                    for (int i_weapon = 0; i_weapon < Data.Items.size(); i_weapon++)
                    {
                        Item thisrecord = (Item)Data.Items.get(i_weapon);
                        if (thisrecord.getID().equals(thisWeapon.getWeaponID()))
                            weaponItemRecord = thisrecord;
                    }
                    if (weaponItemRecord != null)
                    {
                        String weaponprops = weaponItemRecord.getProperties();
                        if (weaponprops.contains("Brutal"))
                        {
                            String damageRoll = thisWeapon.getDamage();
                            damageRoll = damageRoll.replaceAll("(\\d+d\\d+)", "$1r2");
                            thisWeapon.setDamage(damageRoll);                      
                        }
                    }

                    thisPower.getWeaponStats().add(thisWeapon);
                }
            }
            Powers.add(thisPower);   
        }
        return Powers;
    }
    
    NameValueDetails GetComplexNVD(Element e, String subsection)
    {
        String name = "";
        NodeList eRefObj = e.getElementsByTagName("ReferencedObject");
        if (eRefObj.getLength() > 0)
        {
            Element eRefObjElem = (Element)eRefObj.item(0);
            name = GetStringValueFromChildTag(eRefObjElem, "Name");
        }
        int value = GetIntFinalValueFromChildTag(e, subsection);
        String details = GetStringValueFromChildTag(e, "Details");
        return new NameValueDetails(name, value, details);
    }
    
    MonsterStats ReadMonsterStats()
    {
        MonsterStats mStats = new MonsterStats(); 
        
        mStats.setMonsterName(GetStringValueFromFirstLevelChildTag(DndXml.getDocumentElement(), "Name"));

        NodeList eAbilities = DndXml.getElementsByTagName("AbilityScores");
        if (eAbilities.getLength() > 0)
        {
            Element eAbilitiesElem = (Element)eAbilities.item(0);
            NodeList eAbilityTags = eAbilitiesElem.getElementsByTagName("AbilityScoreNumber");
            for (int iTag = 0; iTag < eAbilityTags.getLength(); iTag++)
            {
                Element thisElem = (Element)eAbilityTags.item(iTag);
                int val = Integer.parseInt(thisElem.getAttribute("FinalValue"));
                String ab = thisElem.getElementsByTagName("Name").item(0).getTextContent();
                mStats.getAbilities().set(ab, new Ability(val));
            }
        }
        
        NodeList eDefenses = DndXml.getElementsByTagName("Defenses");
        if (eDefenses.getLength() > 0)
        {
            Element eDefensesElem = (Element)eDefenses.item(0);
            NodeList eDefensesTags = eDefensesElem.getElementsByTagName("SimpleAdjustableNumber");
            for (int iTag = 0; iTag < eDefensesTags.getLength(); iTag++)
            {
                Element thisElem = (Element)eDefensesTags.item(iTag);
                int val = Integer.parseInt(thisElem.getAttribute("FinalValue"));
                String def = thisElem.getElementsByTagName("Name").item(0).getTextContent();
                mStats.getDefenses().set(def, new Defense(val));
            }
        }
               
        mStats.setLevel(GetIntValueFromChildTag(DndXml.getDocumentElement(), "Level"));
        mStats.setHalfLevel((int)Math.floor(mStats.getLevel() / 2));
        
        SkillList slist = new SkillList(mStats.getLevel(), mStats.getAbilities());
        NodeList eSkills = DndXml.getElementsByTagName("Skills");
        if (eSkills.getLength() > 0)
        {
            Element eSkillsElem = (Element)eSkills.item(0);
            NodeList eSkillTags = eSkillsElem.getElementsByTagName("SkillNumber");
            for (int iTag = 0; iTag < eSkillTags.getLength(); iTag++)
            {
                Element thisElem = (Element)eSkillTags.item(iTag);
                int val = Integer.parseInt(thisElem.getAttribute("FinalValue"));
                int trained = 0;
                if (thisElem.getElementsByTagName("Trained").item(0).getTextContent().equals("true"))
                    trained = 1;
                String skill = thisElem.getElementsByTagName("Name").item(0).getTextContent();
                slist.set(skill, new Skill(val, trained, ""));
            }    
        }
        mStats.setSkills(slist);
        
        mStats.setSize(GetStringValueFromChildNameTag(DndXml.getDocumentElement(), "Size"));
        
        mStats.setOrigin(GetNVDFromChild(DndXml.getDocumentElement(), "Origin", "Description")); 
        
        mStats.setType(GetNVDFromChild(DndXml.getDocumentElement(), "Type", "Description"));
        
        mStats.setIsALeader(GetBooleanValueFromChildTag(DndXml.getDocumentElement(), "IsALeader"));
        
        mStats.setLanguages(GetStringListFromChildNameTags(DndXml.getDocumentElement(), "Languages"));

        mStats.setAlignment(GetStringValueFromChildNameTag(DndXml.getDocumentElement(), "Alignment"));
        
        mStats.setGroupRole(GetStringValueFromChildNameTag(DndXml.getDocumentElement(), "GroupRole"));
        
        mStats.setRole(GetStringValueFromChildNameTag(DndXml.getDocumentElement(), "Role"));

        ArrayList senses = new ArrayList();
        NodeList eSenses = DndXml.getElementsByTagName("Senses");
        if (eSenses.getLength() > 0)
        {
            Element eSensesElem = (Element)eSenses.item(0);
            NodeList senselist = eSensesElem.getElementsByTagName("SenseReference");
            for (int iTag = 0; iTag < senselist.getLength(); iTag++)
            {
                Element senseref = (Element)senselist.item(iTag);
                senses.add(new NameValueDetails(GetStringValueFromChildTag(senseref, "Name"), GetIntValueFromChildTag(senseref, "Range"), GetStringValueFromChildTag(senseref, "Description")));
            }
        }
        mStats.setSenses(senses);

        mStats.setRegeneration(GetNVDFromChild(DndXml.getDocumentElement(), "Regeneration"));

        mStats.setTactics(GetStringValueFromFirstLevelChildTag(DndXml.getDocumentElement(), "Tactics"));
        
        mStats.setDescription(GetStringValueFromFirstLevelChildTag(DndXml.getDocumentElement(), "Description"));

        mStats.setKeywords(GetStringListFromChildNameTags(DndXml.getDocumentElement(), "Keywords"));

        mStats.setInitiativeBonus(GetIntFinalValueFromChildTag(DndXml.getDocumentElement(), "Initiative"));
        
        mStats.setMaxHP(GetIntFinalValueFromChildTag(DndXml.getDocumentElement(), "HitPoints"));

        mStats.setActionPoints(GetIntFinalValueFromChildTag(DndXml.getDocumentElement(), "ActionPoints"));

        mStats.setXP(GetIntFinalValueFromChildTag(DndXml.getDocumentElement(), "Experience"));
        
        mStats.setUrl(GetStringValueFromFirstLevelChildTag(DndXml.getDocumentElement(), "CompendiumUrl"));

        mStats.setSavingThrowBonus(GetIntFinalValueFromChildTag(DndXml.getDocumentElement(), "MonsterSavingThrow"));
        
        ArrayList speedRecords = new ArrayList();
        NodeList eLandSpeed = DndXml.getElementsByTagName("LandSpeed");
        if (eLandSpeed.getLength() > 0)
        {
            Element eLandSpeedElem = (Element)eLandSpeed.item(0);
            speedRecords.add(GetComplexNVD(eLandSpeedElem, "Speed"));
        }
        NodeList eCreatureSpeeds = DndXml.getElementsByTagName("CreatureSpeed");
        for (int iCS = 0; iCS < eCreatureSpeeds.getLength(); iCS++)
        {
            Element ielem = (Element)eCreatureSpeeds.item(iCS);
            speedRecords.add(GetComplexNVD(ielem, "Speed"));
        }
        mStats.setSpeeds(speedRecords);
        
        mStats.setImmunities(GetStringListFromChildNameTags(DndXml.getDocumentElement(), "Immunities"));
        
        ArrayList vulRecords = new ArrayList();
        ArrayList resistRecords = new ArrayList();
        NodeList eSucs = DndXml.getElementsByTagName("CreatureSusceptibility");
        for (int iCS = 0; iCS < eSucs.getLength(); iCS++)
        {
            Element ielem = (Element)eSucs.item(iCS);
            Element eParent = (Element)ielem.getParentNode();
            if (eParent.getTagName().equals("Weaknesses"))
                vulRecords.add(GetComplexNVD(ielem, "Amount"));
            if (eParent.getTagName().equals("Resistances"))
                resistRecords.add(GetComplexNVD(ielem, "Amount"));
        }
        mStats.setVulnerabilities(vulRecords);
        mStats.setResistances(resistRecords);
        
        return mStats;
    }
    
    ArrayList ReadMonsterPowers()
    {
        ArrayList Powers = new ArrayList(); 
        
        NodeList nlPowers = DndXml.getElementsByTagName("Powers");
        if (nlPowers.getLength() < 1)
            return Powers;
        Element ePowers = (Element)nlPowers.item(0);
        
        NodeList nlTraits = ePowers.getElementsByTagName("MonsterTrait");
        for (int iTrait = 0; iTrait < nlTraits.getLength(); iTrait++)
        {
            Element eTrait = (Element)nlTraits.item(iTrait);
            Power thisPower = new Power();

            int range = GetIntFinalValueFromChildTag(eTrait, "Range");
            thisPower.setRange(String.valueOf(range));
            if (range != 0)
                thisPower.setAttackRange("Aura");
            
            thisPower.setDetails(GetStringValueFromFirstLevelChildTag(eTrait, "Details"));
            
            thisPower.setName(GetStringValueFromFirstLevelChildTag(eTrait, "Name"));
            
            thisPower.setPowerType(GetStringValueFromFirstLevelChildTag(eTrait, "PowerType"));

            thisPower.setActionType(GetStringValueFromFirstLevelChildTag(eTrait, "Type"));

            thisPower.setIsBasic(Boolean.parseBoolean(GetStringValueFromFirstLevelChildTag(eTrait, "IsBasic")));

            thisPower.setKeywords(GetStringListFromChildNameTags(eTrait, "Keywords").toString());
            
            thisPower.setCategory("Utilities");
            
            Powers.add(thisPower);
        }
        
        NodeList nlMonsterPowers = ePowers.getElementsByTagName("MonsterPower");
        for (int iPower = 0; iPower < nlMonsterPowers.getLength(); iPower++)
        {
            Element ePower = (Element)nlMonsterPowers.item(iPower);
            Power thisPower = new Power();
            
            thisPower.setActionType(GetStringValueFromFirstLevelChildTag(ePower, "Action"));

            thisPower.setRequirements(GetStringValueFromFirstLevelChildTag(ePower, "Requirements"));

            thisPower.setFrequency(GetStringValueFromFirstLevelChildTag(ePower, "Usage"));
            
            if (thisPower.getFrequency().equals("Recharge")) {
                thisPower.setRecharge(GetStringValueFromFirstLevelChildTag(ePower, "UsageDetails"));
            } else {
                thisPower.setUsageDetails(GetStringValueFromFirstLevelChildTag(ePower, "UsageDetails"));
            }
                
            if (thisPower.getFrequency().isEmpty()) {
                thisPower.setFrequency("Encounter");
            }

            thisPower.setFlavor(GetStringValueFromFirstLevelChildTag(ePower, "FlavorText"));
            
            thisPower.setName(GetStringValueFromFirstLevelChildTag(ePower, "Name"));
                        
            thisPower.setAttackRange(GetStringValueFromFirstLevelChildTag(ePower, "Type"));
            
            thisPower.setTarget(GetStringValueFromChildTag(ePower, "Targets"));

            thisPower.setTrigger(GetStringValueFromFirstLevelChildTag(ePower, "Trigger"));
            if (!thisPower.getTrigger().isEmpty())
                thisPower.setHasTrigger(true);

            thisPower.setIsBasic(Boolean.parseBoolean(GetStringValueFromFirstLevelChildTag(ePower, "IsBasic")));

            thisPower.setKeywords(GetStringListFromChildNameTags(ePower, "Keywords").toString());

            AttackWeaponStats weaponStats = new AttackWeaponStats();
            
            NodeList nlMonsterAttack = ePower.getElementsByTagName("MonsterAttack");
            Element eMonsterAttackAttackNode = ePower;
            Boolean isAnAttack = false;
            for (int i_attacknode = 0; i_attacknode < nlMonsterAttack.getLength(); i_attacknode++)
            {
                Element eAttackNode = (Element)nlMonsterAttack.item(i_attacknode);
                String MonsterAttackName = GetStringValueFromFirstLevelChildTag(eAttackNode, "Name");
                if (MonsterAttackName.equals("Attack") || eAttackNode.getElementsByTagName("AttackBonuses").getLength() > 0)
                {
                    eMonsterAttackAttackNode = eAttackNode;
                    isAnAttack = true;
                }
            }
            
            if (isAnAttack)
            {
                NodeList nlAttackBonuses = eMonsterAttackAttackNode.getElementsByTagName("AttackBonuses");
                weaponStats.setAttackStat("Attack");
                weaponStats.setCritRange("20");
                weaponStats.setCritThreshold("20");
                if (nlAttackBonuses.getLength() > 0)
                {
                    Element eAttackBonusesElem = (Element)nlAttackBonuses.item(0);
                    NodeList nlDefenses = eAttackBonusesElem.getElementsByTagName("Defense");
                    if (nlDefenses.getLength() > 0)
                    {
                        Element eDefElem = (Element)nlDefenses.item(0);
                        thisPower.getAttackConfig().setAttack(GetStringValueFromChildNameTag(eDefElem, "ReferencedObject"));
                        weaponStats.setDefense(GetStringValueFromChildNameTag(eDefElem, "ReferencedObject", "DefenseName"));
                        thisPower.setCategory("Attacks");
                        thisPower.setIsAnAttack(true);
                        thisPower.setRange(GetStringValueFromChildNameTag(ePower, "MonsterAttack", "Range"));
                    }
                    weaponStats.setAttackBonus(GetIntFinalValueFromChildTag(eAttackBonusesElem, "MonsterPowerAttackNumber"));
                }
            }
            NodeList nlHit = ePower.getElementsByTagName("Hit");
            if (nlHit.getLength() > 0)
            {
                String damageExpression = "";
                String hitDescription = "";
                for (int i_hititem = 0; i_hititem < nlHit.getLength(); i_hititem++)
                {
                    Element eHit = (Element)nlHit.item(i_hititem);
                    String thisDamageExpression = GetStringValueFromChildNameTag(eHit, "Damage", "Expression");
                    if (damageExpression.isEmpty() && !thisDamageExpression.isEmpty())
                        damageExpression = thisDamageExpression;
                    hitDescription = hitDescription.concat(GetStringValueFromFirstLevelChildTag(eHit, "Description"));
                }
                weaponStats.setDamage(damageExpression);
                thisPower.getAttackConfig().setHit(hitDescription);
            }            
            NodeList nlMiss = ePower.getElementsByTagName("Miss");
            if (nlMiss.getLength() > 0)
            {
                String missDescription = "";
                for (int i_missitem = 0; i_missitem < nlMiss.getLength(); i_missitem++)
                {
                    Element eMiss = (Element)nlMiss.item(i_missitem);
                    missDescription = missDescription.concat(GetStringValueFromFirstLevelChildTag(eMiss, "Description"));
                }
                thisPower.getAttackConfig().setMiss(missDescription);
                if (missDescription.contains("half damage"))
                {
                    thisPower.getAttackConfig().setHalfDamageOnMiss(true);
                }
            }
            NodeList nlEffect = ePower.getElementsByTagName("Effect");
            if (nlEffect.getLength() > 0)
            {
                String effectDescription = "";
                for (int i_effectitem = 0; i_effectitem < nlEffect.getLength(); i_effectitem++)
                {
                    Element eEffect = (Element)nlEffect.item(i_effectitem);
                    effectDescription = effectDescription.concat(GetStringValueFromFirstLevelChildTag(eEffect, "Description"));
                }
                thisPower.setEffect(effectDescription);
            }            
            
            if (thisPower.getIsAnAttack())
                thisPower.getWeaponStats().add(weaponStats);

            Powers.add(thisPower);
        }        
        
        return Powers;
    }

}
