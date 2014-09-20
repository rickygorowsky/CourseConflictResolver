/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import course.Course;
import section.PrioritySection;
import section.Section;
import model.WeekDay;



public class MyCheckboxTree extends JTree implements Serializable {
    private static final long serialVersionUID = 4009820339199442581L;


    public MyCheckboxTree() {
        super(new DefaultTreeModel(new DefaultMutableTreeNode("ROOT")));
        doConstruction();
    }
    
    private void doConstruction() {
        
        Enumeration<DefaultMutableTreeNode> e = getRoot().breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            Object o = node.getUserObject();
            node.setUserObject(new CheckBoxNode(o, Status.DESELECTED));
        }
        super.getModel().addTreeModelListener(new CheckBoxStatusUpdateListener());
    }

    @Override public void updateUI() {
        setCellRenderer(null);
        setCellEditor(null);
        super.updateUI();
        setCellRenderer(new CheckBoxNodeRenderer());
        setCellEditor(new CheckBoxNodeEditor());
    }
    
    public DefaultMutableTreeNode getRoot() {return (DefaultMutableTreeNode) super.getModel().getRoot();}
    public DefaultTreeModel getModel() {return (DefaultTreeModel) super.getModel();} 
    
    
    public DefaultMutableTreeNode addCourseNode() {
        return addNode(getRoot(), new Course("New Course"), getRoot().getChildCount());
    }
    
    public DefaultMutableTreeNode addSectionNode() {
        DefaultMutableTreeNode courseNode;
        TreePath parentPath = this.getSelectionPath();
        
        if (parentPath == null || parentPath.getPathCount() <= 1) {
            return null; // can't add section to root
        } else {
            courseNode = (DefaultMutableTreeNode) parentPath.getPathComponent(1);
        }
        
        Section section = new Section(new LinkedList<WeekDay>());
        return addNode(courseNode, section, courseNode.getChildCount());
    }
    
    public DefaultMutableTreeNode addPrioritySectionNode() {
        DefaultMutableTreeNode courseNode;
        TreePath parentPath = this.getSelectionPath();
        
        if (parentPath == null || parentPath.getPathCount() <= 1) {
            return null; // can't add section to root
        } else {
            courseNode = (DefaultMutableTreeNode) parentPath.getPathComponent(1);
        }

        PrioritySection prioritySection = new PrioritySection(new LinkedList<WeekDay>());
        return addNode(courseNode, prioritySection, 0);
    }
    
    
    private DefaultMutableTreeNode addNode(DefaultMutableTreeNode parentNode, Object newChild, int index) {
        CheckBoxNode newCheckBoxNode = new CheckBoxNode(newChild, Status.DESELECTED);
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(newCheckBoxNode);
        
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        getModel().insertNodeInto(childNode, parentNode, index);

        //Make sure the user can see the lovely new node.
        this.scrollPathToVisible(new TreePath(childNode.getPath()));
        return childNode;
    }
    
    
    
    public List<DefaultMutableTreeNode> removeSelectedNodes() {
        if(this.getSelectionPaths() == null) {return new LinkedList<DefaultMutableTreeNode>();}
        
        TreePath[] currentSelections = this.getSelectionPaths();
        List<DefaultMutableTreeNode> nodeList = new LinkedList<DefaultMutableTreeNode>();
        DefaultMutableTreeNode currentNode = null;
        
        for(int i = 0; i < currentSelections.length; i++) {
            currentNode = (DefaultMutableTreeNode) (currentSelections[i].getLastPathComponent());
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                getModel().removeNodeFromParent(currentNode);
                nodeList.add(currentNode);
            }
        }
        return nodeList;
    }
    
    
    

    
    

    public void setCurrentNodeUpdated() {
        if(this.getSelectionPath() == null) return;
        
        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
        getModel().nodeChanged(treeNode);
    }

    public List<Course> getAllSelectedCourses() {
        List<Course> selectedCourses = new LinkedList<Course>();
        Enumeration<DefaultMutableTreeNode> e = getRoot().children();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            CheckBoxNode o = (CheckBoxNode) node.getUserObject();
            if(o.status == Status.DESELECTED) continue; // we don't add deselected nodes
            
            if(o.data instanceof Course) {
                Course treeCourse = (Course) o.data;
                Course currentCourse = new Course(treeCourse.getCourseName());
                selectedCourses.add(currentCourse);
                addSectionsToCourse(currentCourse, node.children());
            }
        }
        
        return selectedCourses;
    }
    


    private void addSectionsToCourse(Course currentCourse, Enumeration<DefaultMutableTreeNode> sections) {
        while(sections.hasMoreElements()) {
            DefaultMutableTreeNode node = sections.nextElement();
            CheckBoxNode o = (CheckBoxNode) node.getUserObject();
            if(o.status == Status.DESELECTED) continue; // we don't add deselected nodes
            
            Section currentSection = (Section) o.data;
            int beginHour = currentSection.getBeginHour();
            int beginMinute = currentSection.getBeginMinute();
            int endHour = currentSection.getEndHour();
            int endMinute = currentSection.getEndMinute();
            List<WeekDay> week = currentSection.getWeek(); 
            
            if(currentSection instanceof PrioritySection) {
                PrioritySection newSection = new PrioritySection(beginHour, beginMinute, endHour, endMinute, week);
                currentCourse.addPrioritySection(newSection);
            }
            else {
                Section newSection = new Section(beginHour, beginMinute, endHour, endMinute, week);
                currentCourse.addSection(newSection);
            }
        }
    }

    // TODO maybe implement later
//    public void undoLastCommand() {
//        System.out.println("Undoing last command");
//        
//    }
//    
//    public void selectAllNodes() {
//        
//     }
//     
//     public void clearAllNodes() {
//        
//     }
//     
}
