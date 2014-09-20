/**
 * @author Ricky Gorowsky
 */

package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import checkbox.MyCheckboxTree;
import course.Course;
import section.Section;
import model.WeekDay;



public class AddCoursePanel extends JPanel {
    private static final long serialVersionUID = -6134146002218082664L;
    
    private Section currentSection;
    private Course currentCourse;
    
    private MyCheckboxTree myCheckboxTree;
    
	private JTextField courseNameField;
	private JLabel courseNameLabel;
	
	private JSpinner sectionBeginTimeSpinner;
	private JLabel sectionBeginTimeLabel;
	
	private JSpinner sectionEndTimeSpinner;	
    private JLabel sectionEndTimeLabel;
	
    private List<JCheckBox> weekButtonBoxes;
    
    
    
    
	public AddCoursePanel(MyCheckboxTree myCheckboxTree) {

	    this.myCheckboxTree = myCheckboxTree;
		constructComponents();
		setUpListeners();
		addComponentsToFrame();
	}

	
	
	private void addComponentsToFrame() {
	    setLayout(new FlowLayout());
		
		
		JPanel inputFieldsPanel = new JPanel();
		inputFieldsPanel.setLayout(new GridLayout(3, 2));
		inputFieldsPanel.setPreferredSize(new Dimension(200, 60));
		
		    inputFieldsPanel.add(courseNameLabel);
		    inputFieldsPanel.add(courseNameField);
		    inputFieldsPanel.add(sectionBeginTimeLabel);
		    inputFieldsPanel.add(sectionBeginTimeSpinner);
		    inputFieldsPanel.add(sectionEndTimeLabel);
		    inputFieldsPanel.add(sectionEndTimeSpinner);
		
		add(inputFieldsPanel);
		
		JPanel boxPanel = new JPanel();
		GridLayout newLayout = new GridLayout(7, 1);
		boxPanel.setLayout(newLayout);
		boxPanel.setPreferredSize(new Dimension(200, 200));
		
		    for(JCheckBox box: weekButtonBoxes) {
		        boxPanel.add(box);
		    }
		
		add(boxPanel);
	}



	
	
	
	private void constructComponents() {
		

		courseNameField = new JTextField();
		courseNameField.setEditable(true);
		courseNameField.setEnabled(false);
		courseNameLabel = new JLabel("Course Name");
		

		sectionBeginTimeLabel = new JLabel("Begin Time");
		SpinnerDateModel beginSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
		sectionBeginTimeSpinner = new JSpinner(beginSpinnerModel);
        sectionBeginTimeSpinner.setEnabled(false);
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(sectionBeginTimeSpinner, "hh:mm a");
		dateEditor.getTextField().setEditable(true);
		dateEditor.getTextField().setText("");
		sectionBeginTimeSpinner.setEditor(dateEditor);
		
        sectionEndTimeLabel = new JLabel("End Time");
        SpinnerDateModel endSpinnerModel = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        sectionEndTimeSpinner = new JSpinner(endSpinnerModel);
        sectionEndTimeSpinner.setEnabled(false);
        dateEditor = new JSpinner.DateEditor(sectionEndTimeSpinner, "hh:mm a");
        dateEditor.getTextField().setEditable(true);
        dateEditor.getTextField().setText("");
        sectionEndTimeSpinner.setEditor(dateEditor);
        
        weekButtonBoxes = new LinkedList<JCheckBox>();
        weekButtonBoxes.add(new JCheckBox("Sunday"));
        weekButtonBoxes.add(new JCheckBox("Monday"));
        weekButtonBoxes.add(new JCheckBox("Tuesday"));
        weekButtonBoxes.add(new JCheckBox("Wednesday"));
        weekButtonBoxes.add(new JCheckBox("Thursday"));
        weekButtonBoxes.add(new JCheckBox("Friday"));
        weekButtonBoxes.add(new JCheckBox("Saturday"));
        
        
        for(JCheckBox box: weekButtonBoxes) {
            box.setEnabled(false);
        }
    }

	private void setUpListeners() {
	    courseNameField.getDocument().addDocumentListener(new CourseNameListener());
	    sectionBeginTimeSpinner.addChangeListener(new SectionBeginTimeListener());
	    sectionEndTimeSpinner.addChangeListener(new SectionEndTimeListener());
		WeekListener weekListener = new WeekListener();
		for(JCheckBox box: weekButtonBoxes) {
		    box.addActionListener(weekListener);
		}
	}
	  
	public class CourseNameListener implements DocumentListener {
        @Override public void changedUpdate(DocumentEvent e) {update();}
        @Override public void insertUpdate(DocumentEvent e) {update();}
        @Override public void removeUpdate(DocumentEvent e) {update();}
        

        private void update() {
            if(currentCourse == null) return;
            currentCourse.setName(courseNameField.getText());
            myCheckboxTree.setCurrentNodeUpdated();
        }
	}
	
	public class SectionBeginTimeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(currentSection == null) return;
            SpinnerModel dateModel = sectionBeginTimeSpinner.getModel();
            currentSection.setBeginTime(((SpinnerDateModel)dateModel).getDate());
            myCheckboxTree.setCurrentNodeUpdated();
        }
    }

    public class SectionEndTimeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(currentSection == null) return;
            SpinnerModel dateModel = sectionEndTimeSpinner.getModel();
            currentSection.setEndTime(((SpinnerDateModel) dateModel).getDate());
            myCheckboxTree.setCurrentNodeUpdated();
        }
    }
	
	   public class WeekListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                WeekDay[] days = WeekDay.values();
                for(int i = 0; i < weekButtonBoxes.size(); i++) {
                    if(event.getSource() == weekButtonBoxes.get(i)) {
                        AbstractButton abstractButton = (AbstractButton) event.getSource();
                        boolean selected = abstractButton.getModel().isSelected();
                        
                        if(selected) currentSection.addWeekDay(days[i]);
                        else currentSection.removeWeekDay(days[i]);
                    }
                }
                myCheckboxTree.setCurrentNodeUpdated();
            }
	    }
	
	
	public void loadCourseData(Course course) {
	    currentCourse = course;
	    currentSection = null;
	    
	    courseNameField.setEnabled(true);
	    courseNameField.setText(course.toString());
	    
	    for(JCheckBox box: weekButtonBoxes) {
	        box.setEnabled(false);
	    }

	    sectionBeginTimeSpinner.setEnabled(false);
	    sectionEndTimeSpinner.setEnabled(false);
	}
    
    public void loadCourseAndSectionData(Course course, Section section) {
        currentCourse = course;
        courseNameField.setText(course.toString());
        currentSection = section;
        
        WeekDay[] days = WeekDay.values();
        for(int i = 0; i < weekButtonBoxes.size(); i++) {
            weekButtonBoxes.get(i).setEnabled(true);
            weekButtonBoxes.get(i).setSelected(currentSection.getWeek().contains(days[i]));
        }
        
        sectionBeginTimeSpinner.setEnabled(true);
        sectionEndTimeSpinner.setEnabled(true);
        sectionBeginTimeSpinner.setValue(currentSection.getBeginTime());
        sectionEndTimeSpinner.setValue(currentSection.getEndTime());
    }
}



