/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

public class CheckBoxNodeEditor extends TriStateCheckBox implements TreeCellEditor, Serializable {
    private static final long serialVersionUID = -2293303229885788480L;
    
    private final DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    private final JPanel panel = new JPanel(new BorderLayout());
    private Object data = null;
    public CheckBoxNodeEditor() {
        super();
        this.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                stopCellEditing();
            }
        });
        panel.setFocusable(false);
        panel.setRequestFocusEnabled(false);
        panel.setOpaque(false);
        panel.add(this, BorderLayout.WEST);
        this.setOpaque(false);
    }
    @Override public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        JLabel l = (JLabel)renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);
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
                setSelected(node.status==Status.SELECTED);
                data = node.data;
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
    @Override public Object getCellEditorValue() {
        return new CheckBoxNode(data, isSelected()?Status.SELECTED:Status.DESELECTED);
    }
    @Override public boolean isCellEditable(EventObject e) {
        if(e instanceof MouseEvent && e.getSource() instanceof JTree) {
            MouseEvent me = (MouseEvent)e;
            JTree tree = (JTree)e.getSource();
            TreePath path = tree.getPathForLocation(me.getX(), me.getY());
            Rectangle r = tree.getPathBounds(path);
            if(r==null) { return false; }
            Dimension d = getPreferredSize();
            r.setSize(new Dimension(d.width, r.height));
            if(r.contains(me.getX(), me.getY())) {
                if(data==null && System.getProperty("java.version").startsWith("1.7.0")) {
                    System.out.println("XXX: Java 7, only on first run\n"+getBounds());
                    setBounds(new Rectangle(0,0,d.width,r.height));
                }
                return true;
            }
        }
        return false;
    }
    @Override public void updateUI() {
        super.updateUI();
        setName("Tree.cellEditor");
        if(panel!=null) {
            panel.updateUI();
        }
    }

    //Copid from AbstractCellEditor
//     protected EventListenerList listenerList = new EventListenerList();
//     transient protected ChangeEvent changeEvent = null;
    @Override public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    @Override public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }
    @Override public void cancelCellEditing() {
        fireEditingCanceled();
    }
    @Override public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }
    @Override public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }
    public CellEditorListener[] getCellEditorListeners() {
        return listenerList.getListeners(CellEditorListener.class);
    }
    protected void fireEditingStopped() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for(int i = listeners.length-2; i>=0; i-=2) {
            if(listeners[i]==CellEditorListener.class) {
                // Lazily create the event:
                if(changeEvent == null) { changeEvent = new ChangeEvent(this); }
                ((CellEditorListener)listeners[i+1]).editingStopped(changeEvent);
            }
        }
    }
    protected void fireEditingCanceled() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for(int i = listeners.length-2; i>=0; i-=2) {
            if(listeners[i]==CellEditorListener.class) {
                // Lazily create the event:
                if(changeEvent == null) { changeEvent = new ChangeEvent(this); }
                ((CellEditorListener)listeners[i+1]).editingCanceled(changeEvent);
            }
        }
    }
}