/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxNodeRenderer extends TriStateCheckBox implements TreeCellRenderer, Serializable {
    private static final long serialVersionUID = 1869795196032612825L;
    
    private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    private final JPanel panel = new JPanel(new BorderLayout());
    public CheckBoxNodeRenderer() {
        super();
        String uiName = getUI().getClass().getName();
        if(uiName.contains("Synth") && System.getProperty("java.version").startsWith("1.7.0")) {
            System.out.println("XXX: FocusBorder bug?, JDK 1.7.0, Nimbus start LnF");
            renderer.setBackgroundSelectionColor(new Color(0,0,0,0));
        }
        panel.setFocusable(false);
        panel.setRequestFocusEnabled(false);
        panel.setOpaque(false);
        panel.add(this, BorderLayout.WEST);
        this.setOpaque(false);
    }
    @Override public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel l = (JLabel)renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        l.setFont(tree.getFont());
        setIconForClasses(value, l);
        if(value instanceof DefaultMutableTreeNode) {
            this.setEnabled(tree.isEnabled());
            this.setFont(tree.getFont());
            Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
            if(userObject instanceof CheckBoxNode) {
                CheckBoxNode node = (CheckBoxNode)userObject;
                if(node.status==Status.INDETERMINATE) {
                    setIcon(new IndeterminateIcon());
                }else{
                    setIcon(null);
                }
                l.setText(node.data.toString());
                setSelected(node.status == Status.SELECTED);
            }
            //panel.add(this, BorderLayout.WEST);
            panel.add(l);
            return panel;
        }
        return l;
    }
    private void setIconForClasses(Object value, JLabel l) {
        Object userObject = ((DefaultMutableTreeNode)value).getUserObject();
        if(userObject instanceof CheckBoxNode == false) return;
        
        Object data = ((CheckBoxNode) userObject).data;
        if(data instanceof HasIcon) {
            l.setIcon(((HasIcon) data).getIcon());
        }
    }
    @Override public void updateUI() {
        super.updateUI();
        if(panel!=null) {
            //panel.removeAll(); //??? Change to Nimbus LnF, JDK 1.6.0
            panel.updateUI();
            //panel.add(this, BorderLayout.WEST);
        }
        setName("Tree.cellRenderer");
        //???#1: JDK 1.6.0 bug??? @see 1.7.0 DefaultTreeCellRenderer#updateUI()
        //if(System.getProperty("java.version").startsWith("1.6.0")) {
        //    renderer = new DefaultTreeCellRenderer();
        //}
    }
}