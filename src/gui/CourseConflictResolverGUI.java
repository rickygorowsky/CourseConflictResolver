/**
 * @author Ricky Gorowsky
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import checkbox.CheckBoxNode;
import checkbox.MyCheckboxTree;
import course.Course;
import schedual.ScheduleHolder;
import section.Section;

public class CourseConflictResolverGUI extends JFrame {
    private static final long serialVersionUID = 1936344638087515701L;
    
    private CourseConflictResolverGUI frame;
	private MyCheckboxTree myCheckboxTree;
	
	private JTextArea schedualDisplayArea;
    
    private JButton addCourseButton;
    private JButton addSectionButton;
    private JButton addPrioritySectionButton;
    private JButton removeButton;
//    private JButton undoButton; // TODO maybe implement later
    private JButton findSchedualsButton;
    
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveMenuItem;
    private JMenuItem loadMenuItem;
    private JMenu helpMenu;
    private JMenuItem howToUseMenuItem;
    private JMenuItem svspMenuItem;
    private JMenuItem aboutMenuItem;

    private AddCoursePanel addCoursePanel;
    
	public CourseConflictResolverGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        frame = this;
	    myCheckboxTree = new MyCheckboxTree();
	    this.setLocation(50, 50);
	    this.setSize(900, 600);
		constructComponents();
		setUpListeners();
		addComponentsToFrame();

	}

	
	
	private void addComponentsToFrame() {
		this.setLayout(new BorderLayout());

		Dimension buttonPanelDim = new Dimension(300, 30);
		Dimension treeAndCoursePanelDim = new Dimension(500, this.getHeight() - 100);
		Dimension boxScrollPaneComponentDim = new Dimension(300, treeAndCoursePanelDim.height);
		Dimension addCoursePanelDim = new Dimension(200, treeAndCoursePanelDim.height);
		Dimension schedualScrollPaneDim = new Dimension(10, this.getHeight());
		
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		helpMenu.add(howToUseMenuItem);
		helpMenu.add(svspMenuItem);
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);
	        
		
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(buttonPanelDim);
        buttonPanel.setLayout(new FlowLayout());
        
            buttonPanel.add(addCourseButton);
            buttonPanel.add(addSectionButton);
            buttonPanel.add(addPrioritySectionButton);
            buttonPanel.add(removeButton);
//            buttonPanel.add(undoButton);
            buttonPanel.add(findSchedualsButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
            

        JPanel treeAndCoursePanel = new JPanel();
        treeAndCoursePanel.setLayout(new FlowLayout());
        treeAndCoursePanel.setSize(treeAndCoursePanelDim);
        
            Component boxScrollPaneComponent = new JScrollPane(myCheckboxTree);
            boxScrollPaneComponent.setPreferredSize(boxScrollPaneComponentDim);
                
            addCoursePanel.setPreferredSize(addCoursePanelDim);
        
            treeAndCoursePanel.add(boxScrollPaneComponent);
            treeAndCoursePanel.add(addCoursePanel);
            
        add(treeAndCoursePanel, BorderLayout.WEST);
            
        
        Component schedualScrollPane = new JScrollPane(schedualDisplayArea);
        schedualScrollPane.setPreferredSize(schedualScrollPaneDim);
            
		add(schedualScrollPane, BorderLayout.CENTER);
	}



	
	
	
	private void constructComponents() {

		this.setTitle("Course Conflict Resolver");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		myCheckboxTree.setRootVisible(false);
		myCheckboxTree.setEditable(true);
		myCheckboxTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		myCheckboxTree.setShowsRootHandles(true);
        
		addCoursePanel = new AddCoursePanel(myCheckboxTree);
		
		schedualDisplayArea = new JTextArea();
		schedualDisplayArea.setFont(new Font("Courier New", Font.PLAIN, 14));
		schedualDisplayArea.setEditable(false);
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		saveMenuItem = new JMenuItem("Save");
		loadMenuItem = new JMenuItem("Load");
		helpMenu = new JMenu("Help");
		howToUseMenuItem = new JMenuItem("How To Use");
		svspMenuItem = new JMenuItem("Sections vs Priority Sections");
		aboutMenuItem = new JMenuItem("About Course Conflict Resolver");
		
        addCourseButton = new JButton("Add Course");
        addSectionButton = new JButton("Add Section");
        addPrioritySectionButton = new JButton("Add Priority Section");
        removeButton = new JButton("Remove");
//        undoButton = new JButton("Undo");
        findSchedualsButton = new JButton("Find Scheduals");
	}

	
	private void setUpListeners() {
	    
		addCourseButton.addActionListener(new AddCourseButtonListener());
        addSectionButton.addActionListener(new AddSectionButtonListener());
        addPrioritySectionButton.addActionListener(new AddPrioritySectionButtonListener());
        removeButton.addActionListener(new RemoveButtonListener());
//        undoButton.addActionListener(new UndoButtonListener());
        findSchedualsButton.addActionListener(new FindSchedualsButtonListener());
        myCheckboxTree.addTreeSelectionListener(new LoadSectionDataListener());
        saveMenuItem.addActionListener(new SaveMenuItemListener());
        loadMenuItem.addActionListener(new LoadMenuItemListener());
        TextMenuItemListener textMenuItemListener = new TextMenuItemListener();
        howToUseMenuItem.addActionListener(textMenuItemListener);
        svspMenuItem.addActionListener(textMenuItemListener);
        aboutMenuItem.addActionListener(textMenuItemListener);
	}
	  
	
	
    public class AddCourseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            myCheckboxTree.addCourseNode();
        }
    }
    public class AddSectionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            myCheckboxTree.addSectionNode();
        }
    }
    public class AddPrioritySectionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            myCheckboxTree.addPrioritySectionNode();
        }
    }
    public class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            myCheckboxTree.removeSelectedNodes();
        }
    }
    public class FindSchedualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ScheduleHolder scheduleHolder = new ScheduleHolder();
            List<Course> courses = myCheckboxTree.getAllSelectedCourses();
            schedualDisplayArea.setText(scheduleHolder.findAll(courses));
        }
    }
    private class LoadSectionDataListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
            CheckBoxNode node = (CheckBoxNode) treeNode.getUserObject();
            if(node.data instanceof Course) {
                addCoursePanel.loadCourseData((Course)node.data); 
            }
            else if(node.data instanceof Section) {
                Section section = (Section) node.data;
                treeNode = (DefaultMutableTreeNode) e.getPath().getParentPath().getLastPathComponent();
                node = (CheckBoxNode) treeNode.getUserObject();
                Course course = (Course) node.data;
                addCoursePanel.loadCourseAndSectionData(course, section);
            }
            else {
                //addCoursePanel.clearData(); TODO
            }
        }   
    }
    
    private class SaveMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showSaveDialog(null);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileOutputStream fout = null;
                try {
                    String ext = "";
                    if(file.toString().contains(".schdl") == false) {
                        ext = ".schdl";
                    }
                    fout = new FileOutputStream(file + ext);
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(myCheckboxTree);
                    oos.flush();
                    oos.close();
                    fout.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error, IO Exception\n" + e.getMessage() + "\n" + e.getStackTrace());
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("attachment cancelled");
            }
        }
    }
    private class LoadMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(null);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                FileInputStream fin;
                try {
                    fin = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    myCheckboxTree = (MyCheckboxTree) ois.readObject();
                    frame.getContentPane().removeAll();
                    constructComponents();
                    setUpListeners();
                    addComponentsToFrame();
                    frame.revalidate();
                    frame.repaint();
                    ois.close();
                    fin.close();
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(frame, "Error, file invalid");
                    e.printStackTrace();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error, IO Exception");
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(frame, "Error, file must have a .schdl extension");
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("attachment cancelled");
            }
        }
    }
    private class TextMenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputFile = "";
            if(e.getSource() == howToUseMenuItem) inputFile = "HowToUse.txt";
            else if(e.getSource() == svspMenuItem) inputFile = "SectionsVsPrioritySections.txt";
            else if(e.getSource() == aboutMenuItem) inputFile = "About.txt";
            
            String message = null;
            try(BufferedReader br = new BufferedReader(new FileReader("help/" + inputFile))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                message = sb.toString();
            }
            catch (IOException e1) {
                JOptionPane.showMessageDialog(frame, "Error, IO Exception while trying to display help file");
                e1.printStackTrace();
            }
            
            JOptionPane.showMessageDialog(frame, message);
        }
    }
//  public class UndoButtonListener implements ActionListener {
//  @Override
//  public void actionPerformed(ActionEvent e) {
//      myCheckboxTree.undoLastCommand();
//  }
//}
}




