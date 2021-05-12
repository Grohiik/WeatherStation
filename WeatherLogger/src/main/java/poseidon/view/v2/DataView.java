package poseidon.view.v2;

import poseidon.model.DataModel;

/**
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
public class DataView {
    private DataModel data;
    public DataView(DataModel data) { this.data = data; }

    public String getValue() { return data.getValue(); }
    public String getTime() { return data.getTime(); }
}
