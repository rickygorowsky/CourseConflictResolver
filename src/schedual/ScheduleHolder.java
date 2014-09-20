/**
 * @author Ricky Gorowsky
 */

package schedual;

import java.util.LinkedList;
import java.util.List;
import course.Course;
import course.SelectedClass;
import section.Section;



public class ScheduleHolder {

	private List<Schedule> allSchedules;
	
	public List<Schedule> getAllSchedules() {return allSchedules;}
	
	
	public String findAll(List<Course> allCourses) {
		allSchedules = recurse(allCourses, new Schedule());
		return printAllSchedules();
	}
	
	private List<Schedule> recurse(List<Course> allCourses, Schedule schedule) {
		
		if(allCourses.size() == 0) {
			List<Schedule> temp = new LinkedList<Schedule>(); // temp is a wrapper
			 // only add schedules that don't have conflicts and have non-zero size
			if(schedule.hasConflict() == false && schedule.getScheduledClasses().size() != 0) {
				temp.add(schedule);
			}
			return temp;
		}
		Course thisCourse = allCourses.remove(0);
		
		List<Schedule> allSchedules = new LinkedList<Schedule>();
		
		for (Section section : thisCourse.getSections()) {
		    // select a candidate class
			SelectedClass currentClass = new SelectedClass(thisCourse.getCourseName(), thisCourse.getPrioritySections(), section);
			schedule.add(currentClass);

			// work on copys so we don't mess up the variables
			Schedule copyS = copy(schedule);
			List<Course> copyC = copy(allCourses);
			allSchedules.addAll(recurse(copyC, copyS));
			
			// remove class, try the next one
			schedule.remove(currentClass);
		}
		
		return allSchedules;
	}
	
	
	
	private Schedule copy(Schedule toBeCopied) {
		Schedule newSchedule = new Schedule();
		for(SelectedClass newClass: toBeCopied.getScheduledClasses()) {
			newSchedule.add(newClass);
		}
		return newSchedule;
	}
	
	private List<Course> copy(List<Course> toBeCopied) {
		List<Course> newList = new LinkedList<Course>();
		for(Course course: toBeCopied) {
			newList.add(course);
		}
		return newList;
	}
	
	
	
	
	
    public String printAllSchedules() {
        String str = "";
        if(allSchedules.size() == 0) {
            str += "No schedules found";
            return str;
        }
        for(int i = 0; i < allSchedules.size(); i++) {
            str += "Schedule " + (i + 1) + "**************************\n";
            str += allSchedules.get(i).printSchedule();
        }
        return str;
    }
	
}
