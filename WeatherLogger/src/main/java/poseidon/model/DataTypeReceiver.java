package poseidon.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;

/**
 * This class is used to arrange different type of data the devices can contain
 *
 * @author Marcus Linné
 * @version 0.1.0
 */

@Entity
@Table(name = "DATA_TYPES")
public class DataTypeReceiver implements Serializable {
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

    @Column(name = "type") private String type;

    @OneToMany(mappedBy = "type") private Set<DataReceiver> datas;

    @Column(name = "name") private String name;

    // TODO better name for count
    @Column(name = "count") private long count;

    // TODO Date?
    //@Column(name = "created") private Date

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceReceiver device;

    protected DataTypeReceiver() {}

//    public DataTypeReceiver(String type) {
//        this.type = type;
//    }

    public DataTypeReceiver(String type, String name, long count, DeviceReceiver device) {
        this.type = type;
        this.name = name;
        this.count = count;
        this.device = device;
    }

    public DataTypeReceiver(String type, String name, long count) {
        this.type = type;
        this.name = name;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public long getDeviceId() {
        return device.getId();
    }

    /**
     * The toString method used to sort the data from the db into the correct order.
     */
    @Override
    public String toString() {
        return String.format("DataTypeReceiver[id=%d, type='%s', name='%s', count='%d']", id, type,
                             name, count);
    }
}