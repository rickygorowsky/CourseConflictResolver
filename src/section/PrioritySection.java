/**
 * @author Ricky Gorowsky
 */

package section;

import java.io.Serializable;
import java.util.List;
import javax.swing.Icon;
import checkbox.HasIcon;
import model.WeekDay;




public class PrioritySection extends Section implements HasIcon, Serializable {
    private static final long serialVersionUID = 6477861885721580519L;
    
    private final Icon icon = new PrioritySectionIcon();
	public PrioritySection(List<WeekDay> newWeek) {
		super(newWeek);
	}

    public PrioritySection(int beginHour, int beginMinute, int endHour, int endMinute, List<WeekDay> newWeek) {
        super(beginHour, beginMinute, endHour, endMinute, newWeek);
    }

    @Override
    public Icon getIcon() {
        return icon;
    }
}
