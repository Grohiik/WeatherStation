package poseidon.view.v2;

import java.util.Date;
import poseidon.model.DataTypeModel;

/**
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
public class DataTypeView {
    private DataTypeModel dataType;

    public DataTypeView(DataTypeModel dataType) { this.dataType = dataType; }

    public String getName() { return dataType.getName(); }
    public String getUnit() { return dataType.getUnit(); }
    public int getCount() { return dataType.getCount(); }
    public Date getCreated_at() { return dataType.getCreatedAt(); }
}
