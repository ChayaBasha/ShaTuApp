/*
 * SHATU: SHA-256 Tutor
 * 
 *  (C) Johanna & Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.shatu.dao;

import edu.regis.shatu.util.XmlMgr;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.err.XmlException;
import edu.regis.shatu.model.aol.Fact;
import edu.regis.shatu.model.aol.KnowledgeComponent;
import edu.regis.shatu.model.aol.Outcome;
import edu.regis.shatu.model.aol.Unit;
import edu.regis.shatu.svc.UnitSvc;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * An XML-based Data Access Object implementing MouduleSvc behaviors.
 * 
 * @author rickb
 */
public class UnitDAO implements UnitSvc {
    /**
     * Instantiate this Unit DAO with default values.
     */
    public UnitDAO() {     
    }
 
    @Override
    public Unit findById(int id) throws ObjNotFoundException, NonRecoverableException {
        String fileName = "/Units/Unit_" + id + ".xml";
        
        try {
            Element root = XmlMgr.instance().findRoot(fileName);
        
            Unit unit = extractUnit(root);
        
            return unit;
          
        } catch (ObjNotFoundException e) {
            throw new ObjNotFoundException(String.valueOf(id));
        } catch (XmlException e) {
            throw new NonRecoverableException("UnitDAO_Err_1", e);
        }
    }
    
    /**
     * Extracts a unit from the root or a &lt;SubUnit> element
     * 
     * @param element &lt;Unit> or &lt;SubUnit>
     * @return 
     */
    private Unit extractUnit(Element element) throws NonRecoverableException {
        Unit unit = new Unit(XmlMgr.getIntAttribute(element, "id"));
        
        unit.setTitle(XmlMgr.getAttribute(element, "title"));
        
        unit.setDescription(XmlMgr.contentText(element, "Description"));
    
        // ToDo: no longer using fromString use values() - Rickb
        //unit.setPedagogy(PedagogyKind.fromString(XmlMgr.getAttribute(element, "pedagogy")));
                
        //unit.setOutcomes(extractOutcomes(element));
        
   
            
        return unit;
    }
    
    private ArrayList<Outcome> extractOutcomes(Element parent) {
        ArrayList<Outcome> outcomes = new ArrayList<>();
        
        for (Element child : XmlMgr.getChildren(parent, "Outcome")) {
            String type = XmlMgr.getAttribute(child, "type");
            
            Outcome outcome = new Outcome(XmlMgr.getIntAttribute(child, "id"));
            
            //outcome.setKnowledgeComponents(extractKnowledgeComponents(child));
            
            outcomes.add(outcome);
        }
        
        return outcomes;
    }
    
     
    
    /*
    private ArrayList<Task> extractTasks(Element parent) {
        ArrayList<Task> tasks = new ArrayList<>();
        
        for (Element child : XmlMgr.getChildren(parent, "Task"))
            tasks.add(extractTask(child));
        
        // Sort tasks by sequence order
        for (int i = 0; i < tasks.size() - 1; i++) {
            int minIdx = i;
            int minSeq = tasks.get(i).getSequenceId();
                
            for (int j = i + 1; j < tasks.size(); j++)   
                if (tasks.get(j).getSequenceId() < minSeq)
                    minIdx = j;
        
                
            if (i != minIdx) { // swap
                Task tmp = tasks.get(i);
                tasks.set(i, tasks.get(minIdx));
                tasks.set(minIdx, tmp);
            }
        }
            
        return tasks;
    }
     
    private Task extractTask(Element element) {
        Task task = new Task(XmlMgr.getIntAttribute(element, "id"));
        
        task.setSteps(extractSteps(element));
        
        return task;
    }
    
    
    private ArrayList<Step> extractSteps(Element parent) {
        ArrayList<Step> steps = new ArrayList<>();
        
        
        for (Element child : XmlMgr.getChildren(parent, "Step")) {
            Step step = new Step(XmlMgr.getIntAttribute(child, "id"));
         
            step.setSequenceId(XmlMgr.getIntAttribute(child, "sequenceId"));
            
            switch (XmlMgr.getAttribute(child, "type")) {
            case "confirm":
                step.setAction(StepAction.CONFIRM);
                
                Element promptNode = XmlMgr.getChild(child, "Prompt");
                
                step.setPopupMsg(promptNode.getTextContent());
                
                break;
                
            default: 
                return null; // TODO throw error
            }
            
          
            steps.add(step);
        }
        
        return steps;
    }
    */
    
    /**
     * Extract and create a knowledge component outcome from the given element.
     * 
     * Per VanLehn, a Knowledge Components is a task domain concept, principle, 
     * fact, etc. An fragment of the persistent, domain-specific information 
     * that should be used to accomplish tasks.
     * 
     * @param element an &lt;Outcome>
     * @return a Knowledge Component
     * @throws XmlException 
     */
    private ArrayList<KnowledgeComponent> extractKnowledgeComponents(Element parent){
        ArrayList<KnowledgeComponent> components = new ArrayList<>();
        
        NodeList nodes = parent.getElementsByTagName("KnowledgeComponent");
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Element element = (Element) nodes.item(i);
            // TODO:
            int id = XmlMgr.getIntAttribute(element, "id");
            String title = XmlMgr.getOptAttribute(element, "title");
        
            switch(XmlMgr.getAttribute(element, "type")) {
                case "fact":
                    Fact fact = new Fact(id);
                    fact.setTitle(title);
                    fact.setDescription(XmlMgr.contentText(element, "Description"));
                    components.add(fact);
                
                default:
                    return null; // TODO: Tmp
            }
        }
        
        return components;
    }
}

