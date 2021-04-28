package poseidon.model;

/**
 * This class represents what the data table in the DB contains
 * and which parameters that are included.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @version 0.2.0
 */
public class DataUI {

    private String value;
    private String created;


    /**
     * Constructor for DataUI
     *
     * @param
     */
    public DataUI(String value, String created) {
        this.value = value;
        this.created = created;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * The toString method used to sort the data in it's correct order for the DB.
     */
    @Override
    public String toString() {
        return String.format("data_stored[value='%s', created='%s']", value, created);
    }
}
