/**
 * @author Ricky Gorowsky
 */

package course;

import java.util.List;
import section.PrioritySection;
import section.Section;


public class SelectedClass {
	
	private List<PrioritySection> prioritySections;	  
	private Section section;  
	private String className;
	
	
	public SelectedClass(String newName, List<PrioritySection> newPrioritySections, Section newSection) {
		className = newName;
		prioritySections = newPrioritySections;
		section =  newSection;
	}
	

	public List<PrioritySection> getPrioritySections() {return prioritySections;}
	public Section getSection() {return section;}
	public String getClassName() {return className;}
	
	@Override
	public String toString() {
		String str = "";
		for(PrioritySection ps: prioritySections) {
			str += "PS" + ps.toString();
		}
		str += " S" + section.toString();		
		return str;
	}



}
