/**
 * @author Ricky Gorowsky
 */

package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.WeekDay;
import org.junit.Before;
import org.junit.Test;
import course.Course;
import schedual.Schedule;
import schedual.ScheduleHolder;
import section.PrioritySection;
import section.Section;



public class Tests {
	
	private List<WeekDay> mowefr;
	private List<WeekDay> mowe;
	private List<WeekDay> tuth;
	private List<WeekDay> mo;
	private List<WeekDay> tu;
	private List<WeekDay> we;
	private List<WeekDay> th;
	private List<WeekDay> fr;
	private List<WeekDay> sa;
	private List<WeekDay> su;

	
	@Before
	public void setUpWeeks() {
		mowe = new LinkedList<WeekDay>();
		mowe.add(WeekDay.Monday);
		mowe.add(WeekDay.Wednesday);
		
		mowefr = new LinkedList<WeekDay>();
		mowefr.add(WeekDay.Monday);
		mowefr.add(WeekDay.Wednesday);
		mowefr.add(WeekDay.Friday);
		
		tuth = new LinkedList<WeekDay>();
		tuth.add(WeekDay.Tuesday);
		tuth.add(WeekDay.Thursday);
		
		mo = new LinkedList<WeekDay>();
		mo.add(WeekDay.Monday);
		tu = new LinkedList<WeekDay>();
		tu.add(WeekDay.Tuesday);
		we = new LinkedList<WeekDay>();
		we.add(WeekDay.Wednesday);	
		th = new LinkedList<WeekDay>();
		th.add(WeekDay.Thursday);
		fr = new LinkedList<WeekDay>();
		fr.add(WeekDay.Friday);
		sa = new LinkedList<WeekDay>();
		sa.add(WeekDay.Saturday);
		su = new LinkedList<WeekDay>();
		su.add(WeekDay.Sunday);
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void test1Class() {
		ScheduleHolder scheduleHolder = new ScheduleHolder();
		
		List<Course> allCourses = doSetup1();
		scheduleHolder.findAll(allCourses);
		System.out.println(scheduleHolder);
		
		
		List<Schedule> allSchedules = scheduleHolder.getAllSchedules();
		assertTrue(allSchedules.size() == 2);
	}

	
	
	
	

	
	
	private List<Course> doSetup1() {
	
		Course csc335 = new Course("CSC 335");
		csc335.addPrioritySection(new PrioritySection(13, 00, 14, 15, th));
		csc335.addSection(new Section(14, 30, 17, 50, th)); 
		csc335.addSection(new Section(13, 0, 14, 15, mowefr));
		csc335.addSection(new Section(12, 0, 13, 15, tuth)); // wont work
	
		List<Course> allCourses = new ArrayList<Course>();
		allCourses.add(csc335);
		
		return allCourses;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void test3Classes() {
		ScheduleHolder scheduleHolder = new ScheduleHolder();
		
		
		List<Course> allCourses = doSetup2();
		scheduleHolder.findAll(allCourses);
		System.out.println(scheduleHolder);
		
		
		List<Schedule> allSchedules = scheduleHolder.getAllSchedules();
		System.out.println(allSchedules.size());
		assertTrue(allSchedules.size() == 5);
	}
	
	private List<Course> doSetup2() {

		
		Course mat221 = new Course("Mat 221");
		mat221.addSection(new Section(10, 30, 11, 30, mowefr));
		mat221.addSection(new Section(15, 0, 16, 15, tuth));
		
		Course phys241 = new Course("Phys 241");
		phys241.addPrioritySection(new PrioritySection(13, 00, 13, 50, mowe));
		phys241.addSection(new Section(14, 0, 14, 50, th));
		phys241.addSection(new Section(15, 0, 15, 50, fr));

		Course csc335 = new Course("CSC 335");
		csc335.addPrioritySection(new PrioritySection(13, 00, 14, 15, tuth));
		csc335.addSection(new Section(14, 30, 15, 50, mo));
		csc335.addSection(new Section(18, 0, 18, 50, fr));
		csc335.addSection(new Section(15, 30, 16, 50, th));
	
	
		
		List<Course> allCourses = new ArrayList<Course>();
		allCourses.add(csc335);
		allCourses.add(phys241);
		allCourses.add(mat221);
		
		
		return allCourses;
	}
	
	
	
	
	
	
	
	@Test
	public void test4ClassesNoConflicts() {
		ScheduleHolder scheduleHolder = new ScheduleHolder();
		
		
		List<Course> allCourses = doSetup3();
		scheduleHolder.findAll(allCourses);
		System.out.println(scheduleHolder);
		
		
		List<Schedule> allSchedules = scheduleHolder.getAllSchedules();
		System.out.println(allSchedules.size());
		assertTrue(allSchedules.size() == 24);
	}
	
	
	private List<Course> doSetup3() {
		
		Course mat221 = new Course("Mat 221");
		mat221.addSection(new Section(7, 30, 8, 30, mowefr));
		mat221.addSection(new Section(9, 0, 9, 50, mowefr));
		
		Course phys241 = new Course("Phys 241");
		phys241.addPrioritySection(new PrioritySection(10, 00, 10, 50, mowefr));
		phys241.addSection(new Section(11, 0, 12, 15, fr));
		phys241.addSection(new Section(11, 0, 12, 15, mo));

		Course csc335 = new Course("CSC 335");
		csc335.addPrioritySection(new PrioritySection(8, 00, 8, 50, tuth));
		csc335.addSection(new Section(14, 30, 15, 50, tuth));
		csc335.addSection(new Section(11, 0, 12, 15, we));
		csc335.addSection(new Section(13, 0, 13, 50, fr));
	
		Course econ200 = new Course("econ 200");
		econ200.addSection(new Section(9, 30, 11, 15, tuth));
		econ200.addSection(new Section(10, 0, 10, 50, tuth));

		List<Course> allCourses = new ArrayList<Course>();
		allCourses.add(csc335);
		allCourses.add(phys241);
		allCourses.add(mat221);
		allCourses.add(econ200);
		
		
		return allCourses;
	}
	
	
	
	
	@Test
	public void testImpossibleConflict() {
		ScheduleHolder scheduleHolder = new ScheduleHolder();
		
		
		List<Course> allCourses = doSetup4();
		scheduleHolder.findAll(allCourses);
		System.out.println(scheduleHolder);
		
		
		List<Schedule> allSchedules = scheduleHolder.getAllSchedules();
		System.out.println(allSchedules.size());
		assertTrue(allSchedules.size() == 0);
	}
	
	
	
	private List<Course> doSetup4() {

		Course mat221 = new Course("Mat 221");
		mat221.addSection(new Section(10, 0, 12, 30, mo));
		
		Course phys241 = new Course("Phys 241");
		phys241.addPrioritySection(new PrioritySection(11, 00, 11, 50, mo));
		phys241.addSection(new Section(14, 0, 14, 50, fr));

		List<Course> allCourses = new ArrayList<Course>();

		allCourses.add(phys241);
		allCourses.add(mat221);
		
		
		return allCourses;
	}
	
	
	
	
	
	
	
	
	@Test
	public void testRandomClasses() {
		ScheduleHolder scheduleHolder = new ScheduleHolder();
		
		
		List<Course> allCourses = doSetup5();
		scheduleHolder.findAll(allCourses);
		System.out.println(scheduleHolder);
		
		
		List<Schedule> allSchedules = scheduleHolder.getAllSchedules();
		System.out.println(allSchedules.size());
		assertTrue(allSchedules.size() == 11); // TODO do full check
	}
	
	
	
	
	private List<Course> doSetup5() {

		Course mat221 = new Course("Mat 221");
		mat221.addSection(new Section(10, 0, 12, 30, mowe));
		mat221.addSection(new Section(15, 0, 16, 0, tuth));
		
		Course phys241 = new Course("Phys 241");
		phys241.addPrioritySection(new PrioritySection(11, 00, 11, 50, tuth));
		phys241.addSection(new Section(14, 0, 14, 50, th));
		phys241.addSection(new Section(15, 0, 15, 50, fr));

		Course csc335 = new Course("CSC 335");
		csc335.addPrioritySection(new PrioritySection(13, 00, 14, 15, mowefr));
		csc335.addSection(new Section(14, 30, 17, 50, fr));
		csc335.addSection(new Section(18, 0, 18, 50, fr));
		csc335.addSection(new Section(19, 0, 19, 50, mo));
	
		Course econ200 = new Course("econ 200");
		econ200.addSection(new Section(18, 0, 18, 50, mowefr));
		econ200.addSection(new Section(10, 0, 11, 15, mowe));

		List<Course> allCourses = new ArrayList<Course>();

		allCourses.add(csc335);
		allCourses.add(phys241);
		allCourses.add(mat221);
		allCourses.add(econ200);
		
		
		return allCourses;
	}
}
