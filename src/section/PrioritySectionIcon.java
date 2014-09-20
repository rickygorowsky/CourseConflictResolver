/**
 * @author Ricky Gorowsky
 */

package section;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.UIManager;

public class PrioritySectionIcon implements Icon, Serializable {
    private static final long serialVersionUID = 8974039661760818743L;
    
    private final Icon icon = UIManager.getIcon("CheckBox.icon");
    @Override public void paintIcon(Component c, Graphics g, int x, int y) {
        
        int w = getIconWidth();
        int h = getIconHeight();
        Graphics2D g2 = (Graphics2D)g;
        g2.translate(x, y);
        g2.setPaint(Color.BLACK);
        g2.drawString("P", w / 2 - 2, h - 2);
        g2.translate(-x, -y);
        
    }
    @Override public int getIconWidth()  {
        return icon.getIconWidth();
    }
    @Override public int getIconHeight() {
        return icon.getIconHeight();
    }

}
