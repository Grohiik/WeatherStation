package poseidon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * This class is used to specify which data types the devices can contain when added to the DB, each
 * set of data is specified by a @Column mark. It implements serializable because of it being sent
 * across a stream.
 *
 * @author Marcus Linné
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
@Entity
@Table(name = "data_types")
public class DataTypeModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "type")
    private Set<DataModel> datas;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Column(name = "count")
    private int count;
    @Column(name = "unit", columnDefinition = "TEXT")
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private DeviceModel device;

    @CreatedDate
    @Column(name = "created_at", columnDefinition = "timestamp with time zone")
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp with time zone")
    private Date updatedAt;
    @Column(name = "deleted_at", columnDefinition = "timestamp with time zone")
    private Date deletedAt;

    protected DataTypeModel() {}

    public DataTypeModel(String name, int count, String unit, DeviceModel device) {
        this.name = name;
        this.count = count;
        this.unit = unit;
        this.device = device;
    }

    public DataTypeModel(String name, int count, String unit) {
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public Date getCreatedAt() { return createdAt; }

    /**
     * The toString method used to sort the data from the db into the correct order.
     */
    @Override
    public String toString() {
        return String.format("DataTypeReceiver[id=%d, name='%s', count='%d', unit='%s']", id, name,
                             count, unit);
    }
}