/**
 * @author Ricky Gorowsky
 */

package course;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Icon;
import checkbox.HasIcon;
import section.PrioritySection;
import section.Section;


public class Course implements HasIcon, Serializable {
    private static final long serialVersionUID = -3546702305412343239L;
    
    private final Icon icon = new CourseIcon();
	private String courseName;
	
	private List<PrioritySection> prioritySections;	  
	private List<Section> sections;  
	
	
	public Course() {
	    doConstruction("", new LinkedList<PrioritySection>(), new LinkedList<Section>());
	}
	
	public Course(String newName) {
		doConstruction(newName, new LinkedList<PrioritySection>(), new LinkedList<Section>());
	}
	
	public Course(String newName, List<PrioritySection> newPSections, List<Section> newSections) {
		doConstruction(newName, newPSections, newSections);
	}
	
	private void doConstruction(String newName, List<PrioritySection> newPSections, List<Section> newSections) {
		courseName = newName;
		prioritySections = newPSections;
		sections = newSections;
	}
	
	public void setName(String newName) {courseName = newName;}
	
	public void addPrioritySection(PrioritySection newPriority) {prioritySections.add(newPriority);}
	public void addSection(Section newSection) {sections.add(newSection);}
	
	public void removePrioritySection(PrioritySection badPriority) {prioritySections.remove(badPriority);}
	public void removeSection(Section badSection) {sections.remove(badSection);}
	
	public List<PrioritySection> getPrioritySections() {return prioritySections;}
	public List<Section> getSections() {return sections;}
	public String getCourseName() {return courseName;}
	

	
	@Override
	public String toString() {
		return getCourseName();
	}

    @Override
    public Icon getIcon() {
        return icon;
    }

} // end of class Course