package poseidon.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

/**
 * This class is used to specify which devices we want to add to the DB, each set of data is
 * specified by a
 * @Column mark. It implements serializable because of it being sent across a stream.
 *
 * @author Erik Kellgren
 * @author Marcus Linné
 * @version 0.0.0
 */
@Entity
@Table(name = "devices")
public class DeviceReceiver implements Serializable {
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

    @Column(name = "device") private String device;

    @OneToMany(mappedBy = "device") private Set<DataReceiver> weatherData;

    /**
     * Protected constructor required by Spring
     */
    protected DeviceReceiver() {}

    /**
     * The constructor of this class initializes the variables of this class.
     *
     * @param device The device the data is received from.
     */
    public DeviceReceiver(String device) {
        this.device = device;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * The toString method used to sort the devices into the correct order.
     */
    @Override
    public String toString() {
        return String.format("devices[id=%d, device='%s']", id, device);
    }
}
