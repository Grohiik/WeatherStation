package poseidon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * This class is used to specify which devices we want to add to the DB, each set of data is
 * specified by a @Column mark. It implements serializable because of it being sent across a stream.
 *
 * @author Erik Kellgren
 * @author Marcus Linné
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
@Entity
@Table(name = "devices")
public class DeviceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "device_name", columnDefinition = "TEXT", unique = true)
    private String deviceName;
    @OneToMany(mappedBy = "device")
    private Set<DataTypeModel> dataTypes;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp with time zone")
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp with time zone")
    private Date updatedAt;

    @Column(name = "deleted_at", columnDefinition = "timestamp with time zone")
    private Date deletedAt;

    /**
     * Protected constructor required by Spring.
     */
    protected DeviceModel() {}

    /**
     * The constructor of this class initializes the variables of this class.
     *
     * @param device The device the data is received from.
     * @param description The description of the device the data is received from.
     */
    public DeviceModel(String device) { this.deviceName = device; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public Date getCreatedAt() { return this.createdAt; }
    public Date getUpdatedAt() { return this.updatedAt; }
    public Date getDeletedAt() { return this.deletedAt; }

    /**
     * The toString method used to sort the devices into the correct order.
     */
    @Override
    public String toString() {
        return String.format("devices[id=%d, device_name='%s', created_at='%s']", id, deviceName,
                             createdAt.toString());
    }
}
