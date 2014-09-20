/**
 * @author Ricky Gorowsky
 */

package schedual;

import java.util.LinkedList;
import java.util.List;
import course.SelectedClass;
import model.WeekDay;
import section.PrioritySection;
import section.Section;


public class Schedule {

	
	private List<SelectedClass> scheduledClasses;

	public Schedule() {
		scheduledClasses = new LinkedList<SelectedClass>();
	}

	public List<SelectedClass> getScheduledClasses() {return scheduledClasses;}
	public void add(SelectedClass newClass) {scheduledClasses.add(newClass);}
	public void remove(SelectedClass badClass) {scheduledClasses.remove(badClass);}

	


	
	
	public boolean hasConflict() {

		// gather all sections
		List<Section> allSections = gatherAllSections();

		for(Section section: allSections) {
	        // can't compare schedules who have no days scheduled
		    if(section.getWeek().size() == 0) {
		        return true;
		    }
		}

		// check each section against every other one
		for (int i = 0; i < allSections.size(); i++) {
			for (int j = 0; j < allSections.size(); j++) {
				if (i == j)
					continue;

				Section x = allSections.get(i);
				Section y = allSections.get(j);

				if (x.checkIfInConflictWith(y)) {
					return true;
				}
			}
		}
		return false;
	}
		
		
	
	private List<Section> gatherAllSections() {
		List<Section> allSections = new LinkedList<Section>();
		for (SelectedClass selectedClass : scheduledClasses) {
			for (PrioritySection ps : selectedClass.getPrioritySections()) {
				allSections.add(ps);
			}
			allSections.add(selectedClass.getSection());
		}
		return allSections;
	}
		


		
		

		
		

    public String printSchedule() {
        String str = "";

        for (SelectedClass thisClass : scheduledClasses) {
            str += thisClass.getClassName() + " \n";
            for (PrioritySection ps : thisClass.getPrioritySections()) {
                str += "Priority Section:\n";
                str += printWeek(ps.getWeek());
                str += "\n";
                str += ps.getScheduledTime();
                str += "\n\n";
            }

            Section section = thisClass.getSection();
            str += "Section:\n";
            str += printWeek(section.getWeek());
            str += "\n";
            str += section.getScheduledTime();
            str += "\n\n";
        }
        str += printPicture();

        return str;
    }
		
		

    private String printWeek(List<WeekDay> week) {

        String str = "";

        if(week.contains(WeekDay.Sunday)) str += "Su ";
        if(week.contains(WeekDay.Monday)) str += "Mo ";
        if(week.contains(WeekDay.Tuesday)) str += "Tu ";
        if(week.contains(WeekDay.Wednesday)) str += "We ";
        if(week.contains(WeekDay.Thursday)) str += "Th ";
        if(week.contains(WeekDay.Friday)) str += "Fr ";
        if(week.contains(WeekDay.Saturday)) str += "Sa ";
        
        return str;
    }


    public String printPicture() {

        String str = "";
        int HIEGHT = 13;
        int WEEK_LENGTH = 7;
        int BIAS = 7;

        // initialize fullWeek to spaces
        char[][] fullWeek = new char[HIEGHT][WEEK_LENGTH];
        for(int i = 0; i < fullWeek.length; i++) {
            for(int j = 0; j < fullWeek[0].length; j++) {
                fullWeek[i][j] = ' ';
            }
        }
        
        List<Section> allSections = gatherAllSections();

        for (Section section : allSections) {
            for (WeekDay day : section.getWeek()) {

                // get begin and end hour in military time
                int beginHour = section.getBeginHour();
                int endHour = section.getEndHour();

                for (int i = beginHour - BIAS; i <= endHour - BIAS; i++) {
                    if (i < 0) continue;
                    if (i >= HIEGHT) continue;
                    fullWeek[i][day.ordinal()] = 'X';
                }
            }
        }
			
			
        // we have our picture, now print it out
        str += " \t |Su |Mo |Tu |We |Th |Fr |Sa |\n";
        for (int i = 0; i < fullWeek.length; i++) {

            int start = (i + BIAS) % 12 == 0 ? 12 : (i + BIAS) % 12;
            int end = (i + BIAS + 1) % 12 == 0 ? 12 : (i + BIAS + 1) % 12;

            String zero = "";
            String zero2 = "";
            if (start < 10) zero = " ";
            if (end < 10) zero2 = " ";

            str += zero + start + " - " + zero2 + end + "  | ";
            for (int j = 0; j < fullWeek[0].length; j++) {
                str += fullWeek[i][j] + " | ";
            }
            str += "\n";
        }
        str += "\n";

        return str;
    }

		
		
	@Override
	public String toString() {
		return scheduledClasses.toString();
	}

} // end of class Schedule
