/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.awt.EventQueue;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JCheckBox;


public class TriStateCheckBox extends JCheckBox implements Serializable {
    private static final long serialVersionUID = 1902498586848768070L;
    
    private Icon currentIcon;
    @Override public void updateUI() {
        currentIcon = getIcon();
        setIcon(null);
        super.updateUI();
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                if(currentIcon!=null) {
                    setIcon(new IndeterminateIcon());
                }
                setOpaque(false);
            }
        });
    }

}
