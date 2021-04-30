package poseidon.model;

/**
 * This class represents what the DataType table in the DB contains
 * and which parameters that are included.
 *
 * @author Erik Kellgren
 * @version 0.1.0
 */

public class DataTypeUI {
    private String name;
    private String type;
    private String unit;
    private long count;

    public DataTypeUI(String name, String type, String unit, long count) {
        this.count = count;
        this.name = name;
        this.unit = unit;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    /**
     * The toString method used to sort the data in it's correct order for the DB.
     */
    @Override
    public String toString() {
        return String.format("data_types[name='%s', unit='%s', type='%s', count='%d']", name, unit,
                             type, count);
    }
}
