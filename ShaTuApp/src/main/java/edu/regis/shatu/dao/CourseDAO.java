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
import edu.regis.shatu.err.InconsistentDBException;
import edu.regis.shatu.err.NonRecoverableException;
import edu.regis.shatu.err.ObjNotFoundException;
import edu.regis.shatu.err.XmlException;
import edu.regis.shatu.model.aol.Course;
import edu.regis.shatu.model.aol.Outcome;
import edu.regis.shatu.model.aol.ProblemOutcome;
import edu.regis.shatu.model.aol.Unit;
import edu.regis.shatu.svc.CourseSvc;
import edu.regis.shatu.svc.UnitSvc;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * An XML-based Data Access Object implementing CourseSvc behaviors.
 * 
 * @author rickb
 */
public class CourseDAO implements CourseSvc {
    /**
     * Instantiate this Course DAO with default values.
     */
    public CourseDAO() {     
    }    
    
    @Override
    public Course findById(int id) throws ObjNotFoundException, NonRecoverableException {
        String fileName = "/Course/Course_" + id + ".xml";
        
        try {
            XmlMgr xmlMgr = XmlMgr.instance();
            Element root = xmlMgr.findRoot(fileName);
        
            Course course = new Course(id);
        
            course.setTitle(XmlMgr.getAttribute(root, "title"));
            
            course.setDescription(XmlMgr.getChild(root, "Description").getTextContent());
         
            course.setUnits(extractUnits(root));

            extractOutcomes(root, course);
        
            return course;
          
       } catch (ObjNotFoundException e) {
            throw new ObjNotFoundException(String.valueOf(id));
        } catch (XmlException e) {
            throw new NonRecoverableException("CourseDAO_Err_1", e);
        }
    }
    
    /**
     * Extract child &lt;Outcome> elements from given XML DOM parent element 
     * adding each as a Outcome to the given Course.
     * 
     * @param parent an XML DOM Element containing one or more child 
     *             &lt;Outcome> node elements.
     * @param course the course to which extracted Outcomes are added
     * @throws ShaTuException a nonrecoverable exception also see getCause()
     */
    private void extractOutcomes(Element parent, Course course) throws NonRecoverableException {
        NodeList nodes = parent.getElementsByTagName("Outcome");
        
        for (int i = 0; i < nodes.getLength(); i++)
            extractOutcome((Element) nodes.item(i), course);
    }
    
    private void extractOutcome(Element element, Course course) throws NonRecoverableException {
        Outcome outcome = new Outcome(XmlMgr.getIntAttribute(element, "id"));
      
        String type = XmlMgr.getAttribute(element, "type");
        
        //outcome.setBloomLevel(BloomLevel.fromString(XmlMgr.getAttribute(element, "bloomLevel")));
        
        //ToDo: no longer using from string, use values() - Rickb
       // outcome.setPedagogy(PedagogyKind.fromString(XmlMgr.getAttribute(element, "pedagogy")));
        
        course.addOutcome(outcome);
    }
    
    private ArrayList<Unit> extractUnits(Element parent) throws NonRecoverableException {
        ArrayList<Unit> units = new ArrayList<>();
        
        int id = -1; // Declare here for better error reporting below
        
        try {
            NodeList nodes = parent.getElementsByTagName("Unit");
        
            UnitSvc unitSvc = new UnitDAO();
        
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                
                id = XmlMgr.getIntAttribute(element, "id");
    
                Unit unit = unitSvc.findById(id);
      
                unit.setSequenceId(XmlMgr.getIntAttribute(element, "sequence"));
      
                units.add(unit);            
            }
            
            // Sort Units by their sequence order
            /*
            for (int i = 0; i < units.size() - 1; i++) {
                int minIdx = i;
                int minSeq = units.get(i).getSequence();
                
                for (int j = i + 1; j < units.size(); j++)   
                    if (units.get(j).getSequence() < minSeq)
                        minIdx = j;
        
                
                if (i != minIdx) { // swap
                    Unit tmp = units.get(i);
                    units.set(i, units.get(minIdx));
                    units.set(minIdx, tmp);
                }
            }
            */
                
            return units;
            
        } catch (ObjNotFoundException ex) {
            InconsistentDBException err = new InconsistentDBException("No unit for id: " + String.valueOf(id));
            throw new NonRecoverableException("Inconsistent Db Ref", err);
        }
    }
    
    /**
     * Extract and create a problem outcome from the given element.
     * 
     * @param element an &lt;Outcome> with type "Problem"
     * @return
     * @throws XmlException 
     */
    private Outcome extractProblem(Element element) throws XmlException {
        int id = XmlMgr.getIntAttribute(element, "id");
        
        ProblemOutcome outcome = new ProblemOutcome(id);
        
        outcome.setSequenceId(XmlMgr.getIntAttribute(element, "sequenceId"));
        outcome.setProblemId(XmlMgr.getIntAttribute(element, "problemId"));
        
        return outcome;    
    }

}

