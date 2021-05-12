package poseidon.view.v1;

import poseidon.model.DataTypeModel;

/**
 * This class represents what the DataType table in the DB contains
 * and which parameters that are included.
 *
 * @author Erik Kellgren
 * @author Pratchaya Khansomboon
 * @version 0.2.0
 */
public class DataTypeUI {
    private DataTypeModel dataType;

    public DataTypeUI(DataTypeModel dataType) { this.dataType = dataType; }

    public String getName() { return dataType.getName(); }
    public String getUnit() { return dataType.getUnit(); }
    public String getType() { return "number"; };
    public int getCount() { return dataType.getCount(); }
}
