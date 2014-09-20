/**
 * @author Ricky Gorowsky
 */

package section;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.Icon;
import checkbox.HasIcon;
import model.WeekDay;



public class Section implements HasIcon, Serializable {
    private static final long serialVersionUID = 5930621082483602092L;
    
    private final Icon icon = new SectionIcon();
	private List<WeekDay> week;
	
	private Calendar beginCalendar;
	private Calendar endCalendar;
	
    public Section(List<WeekDay> newWeek) {
        beginCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        beginCalendar.set(0, 0, 0, getBeginHour(), 0, 0);
        endCalendar.set(0, 0, 0, getEndHour() + 1, 0, 0);
        
        week = newWeek;
    }
    
	
    public Section(int beginHour, int beginMinute, int endHour, int endMinute, List<WeekDay> newWeek) {
        beginCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        beginCalendar.set(0, 0, 0, beginHour, beginMinute, 0);
        endCalendar.set(0, 0, 0, endHour, endMinute, 0);

        week = newWeek;
    }
	
	public void setBeginTime(Date time) {beginCalendar.setTime(time);}
	public Date getBeginTime() {return beginCalendar.getTime();}
    
	public void setEndTime(Date time) {endCalendar.setTime(time);}
    public Date getEndTime() {return endCalendar.getTime();}
    
    public void addWeekDay(WeekDay day) {week.add(day);}
    public void removeWeekDay(WeekDay day) {week.remove(day);}
    public List<WeekDay> getWeek() {return week;}
	
    public int getBeginHour() {return calculate(new SimpleDateFormat("hh a"), beginCalendar.getTime());}
    public int getEndHour() {return calculate(new SimpleDateFormat("hh a"), endCalendar.getTime());}
    
    public int getBeginMinute() {return calculate(new SimpleDateFormat("mm"), beginCalendar.getTime());}
    public int getEndMinute() {return calculate(new SimpleDateFormat("mm"), endCalendar.getTime());}
    
    private int calculate(SimpleDateFormat formatter, Date time) {
        String timeStr = formatter.format(time);
        if(timeStr.contains("PM")) {
            int num = Integer.parseInt(timeStr.substring(0, 2));
            if(num == 12) return num;
            else return 12 + num;
        }
        else if(timeStr.contains("AM")) {
            int num = Integer.parseInt(timeStr.substring(0, 2));
            if(num == 12) return 12 + num;
            else return num;
        }
        else {
            // its just a minute
            return Integer.parseInt(timeStr.substring(0, 2));
        }
    }
    
    
	
	
	// checks any two sections for conflict, true if there is a conflict
	public boolean checkIfInConflictWith(Section other) {
		
		Date otherBegin = other.getBeginTime();
		Date otherEnd = other.getEndTime();
		List<WeekDay> otherWeek = other.getWeek();
		Date beginTime = beginCalendar.getTime(); 
		Date endTime = endCalendar.getTime(); 

        for (WeekDay day : week) {
            if (otherWeek.contains(day)) {
                if(    (beginTime.compareTo(otherBegin) <= 0 && endTime.compareTo(otherBegin) >= 0)
                    || (beginTime.compareTo(otherEnd) <= 0 && endTime.compareTo(otherEnd) >= 0)
                    || (otherBegin.compareTo(beginTime) <= 0 && otherEnd.compareTo(beginTime) >= 0)
                    || (otherBegin.compareTo(endTime) <= 0 && otherEnd.compareTo(endTime) >= 0)
                  )
                    return true;
            }
        }
        return false;
	}

	

    public String getScheduledTime() {
        
        String str = "";
        str += doFormat(beginCalendar.getTime());
        str += "-";
        str += doFormat(endCalendar.getTime());
        
        return str;
    }
	
	@Override
	public String toString() {
		return doFormat(beginCalendar.getTime()) + "-" + doFormat(endCalendar.getTime()) + "   " + convertWeekToString(week);
	}
	
    private String convertWeekToString(List<WeekDay> week) {
        String str = "";
        
        if(week.contains(WeekDay.Sunday)) str += "Su";
        if(week.contains(WeekDay.Monday)) str += "Mo";
        if(week.contains(WeekDay.Tuesday)) str += "Tu";
        if(week.contains(WeekDay.Wednesday)) str += "We";
        if(week.contains(WeekDay.Thursday)) str += "Th";
        if(week.contains(WeekDay.Friday)) str += "Fr";
        if(week.contains(WeekDay.Saturday)) str += "Sa";
        
        
        return str;
    }
    
    private String doFormat(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        return formatter.format(time);
    }


    @Override
    public Icon getIcon() {
        return icon;
    }



}