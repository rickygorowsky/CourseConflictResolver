/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;

public class IndeterminateIcon implements Icon, Serializable {
    private static final long serialVersionUID = 9113407542205485561L;
    
    private final Color FOREGROUND = new Color(50,20,255,200); //TEST: UIManager.getColor("CheckBox.foreground");
    private final Icon icon = UIManager.getIcon("CheckBox.icon");
    private static final int a = 4;
    private static final int b = 2;
    @Override public void paintIcon(Component c, Graphics g, int x, int y) {
        if(!(c instanceof JCheckBox)) {
            return;
        }
        icon.paintIcon(c, g, x, y);
        int w = getIconWidth();
        int h = getIconHeight();
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(FOREGROUND);
        g2.translate(x, y);
        g2.fillRect(a, (h-b)/2, w-a-a, b);
        g2.translate(-x, -y);
    }
    @Override public int getIconWidth()  {
        return icon.getIconWidth();
    }
    @Override public int getIconHeight() {
        return icon.getIconHeight();
    }

}
