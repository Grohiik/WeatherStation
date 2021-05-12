package poseidon.view.v1;

import poseidon.model.DataModel;

/**
 * This class represents what the data table in the DB contains
 * and which parameters that are included.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @author Pratchaya Khansomboon
 * @version 0.2.0
 */
public class DataUI {
    private DataModel data;
    public DataUI(DataModel data) { this.data = data; }

    public String getValue() { return data.getValue(); }
    public String getCreated() { return data.getTime(); }
}
