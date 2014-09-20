/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.io.Serializable;
import java.util.Enumeration;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public class CheckBoxStatusUpdateListener implements TreeModelListener, Serializable {
    private static final long serialVersionUID = 2454420641007246221L;
    
    private boolean adjusting = false;
    @Override public void treeNodesChanged(TreeModelEvent e) {
        if(adjusting) { return; }
        adjusting = true;
        TreePath parent = e.getTreePath();
        Object[] children = e.getChildren();
        DefaultTreeModel model = (DefaultTreeModel)e.getSource();

        DefaultMutableTreeNode node;
        CheckBoxNode c; // = (CheckBoxNode)node.getUserObject();
        if(children!=null && children.length==1) {
            node = (DefaultMutableTreeNode)children[0];
            c = (CheckBoxNode)node.getUserObject();
            DefaultMutableTreeNode n = (DefaultMutableTreeNode)parent.getLastPathComponent();
            while(n!=null) {
                updateParentUserObject(n);
                DefaultMutableTreeNode tmp = (DefaultMutableTreeNode)n.getParent();
                if(tmp==null) {
                    break;
                }else{
                    n = tmp;
                }
            }
            model.nodeChanged(n);
        }else{
            node = (DefaultMutableTreeNode)model.getRoot();
            c = (CheckBoxNode)node.getUserObject();
        }
        updateAllChildrenUserObject(node, c.status);
        model.nodeChanged(node);
        adjusting = false;
    }
    private void updateParentUserObject(DefaultMutableTreeNode parent) {
        Object data = ((CheckBoxNode)parent.getUserObject()).data;
        int selectedCount = 0;
        int indeterminateCount = 0;
        Enumeration<DefaultMutableTreeNode> children = parent.children();
        while(children.hasMoreElements()) {
            DefaultMutableTreeNode node = children.nextElement();
            CheckBoxNode check = (CheckBoxNode)node.getUserObject();
            if(check.status==Status.INDETERMINATE) {
                indeterminateCount++;
                break;
            }
            if(check.status==Status.SELECTED) { selectedCount++; }
        }
        if(indeterminateCount>0) {
            parent.setUserObject(new CheckBoxNode(data));
        }else if(selectedCount==0) {
            parent.setUserObject(new CheckBoxNode(data, Status.DESELECTED));
        }else if(selectedCount==parent.getChildCount()) {
            parent.setUserObject(new CheckBoxNode(data, Status.SELECTED));
        }else{
            parent.setUserObject(new CheckBoxNode(data));
        }
    }
    private void updateAllChildrenUserObject(DefaultMutableTreeNode root, Status status) {
        Enumeration<DefaultMutableTreeNode> e = root.breadthFirstEnumeration();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if(root==node) {
                continue;
            }
            CheckBoxNode check = (CheckBoxNode)node.getUserObject();
            node.setUserObject(new CheckBoxNode(check.data, status));
        }
    }
    @Override public void treeNodesInserted(TreeModelEvent e)    { /* not needed */ }
    @Override public void treeNodesRemoved(TreeModelEvent e)     { /* not needed */ }
    @Override public void treeStructureChanged(TreeModelEvent e) { /* not needed */ }

}
