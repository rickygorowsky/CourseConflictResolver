/**
 * @author Ricky Gorowsky
 */

package course;

import java.util.ArrayList;
import java.util.List;
import section.PrioritySection;
import section.Section;


public class CourseHolder {

    private List<Course> allCourses;
    
    public CourseHolder() {
        allCourses = new ArrayList<Course>();
    }
    
    public List<Course> getAllCourses() {return allCourses;}
    
    
    
    
     
  public void addCourse(Course newCourse) {
      allCourses.add(newCourse);
  }


public void addPrioritySection(Course theCourse, PrioritySection newPrioritySection) {
      theCourse.addPrioritySection(newPrioritySection);
  }


  public void addSection(Course theCourse, Section newSection) {
      theCourse.addSection(newSection);
  }
    
    

  public void removeCourse(Course badCourse) {
      allCourses.remove(badCourse);        
  }
  
    

    
    
    
    
    
    
    public void removePrioritySection(PrioritySection badPrioritySection) {
        for(Course course: allCourses) {
            if(course.getPrioritySections().contains(badPrioritySection)) {
                course.removePrioritySection(badPrioritySection);
            }
        }
    }
    
    public void removeSection(Section badSection) {
        for(Course course: allCourses) {
            if(course.getPrioritySections().contains(badSection)) {
                course.removeSection(badSection);
            }
        }
    }
    
    
    
 
    

    
}
