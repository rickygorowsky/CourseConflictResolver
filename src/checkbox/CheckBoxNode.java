/**
 * @author Ricky Gorowsky
 */

package checkbox;

import java.io.Serializable;

public class CheckBoxNode implements Serializable {
    private static final long serialVersionUID = 6576441424957843443L;
    
    public final Status status;
    public final Object data;

    public CheckBoxNode(Object data) {
        status = Status.INDETERMINATE;
        this.data = data;
    }
    public CheckBoxNode(Object data, Status status) {
        this.status = status;
        this.data = data;
    }
    @Override public String toString() {
        return data.toString();
    }

}
