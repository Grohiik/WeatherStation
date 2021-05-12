package poseidon.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * This class is used to specify which data excluding which device we want to add to the DB, each
 * set of data is specified by a @Column mark. It implements serializable because of it being sent
 * across a stream.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @author Pratchaya Khansomboon
 * @version 0.1.0
 */
@Entity
@Table(name = "data_storeds")
public class DataModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value", columnDefinition = "TEXT")
    private String value;

    @Column(name = "time", columnDefinition = "TEXT")
    private String time;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private DataTypeModel type;

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
    protected DataModel() {}

    /**
     * Default Constructor for DataReceiver. Initializes all the variables of this class, including
     * the data type originating from DataTypeReceiver.
     *
     * @param value     The value as such of the data received.
     * @param created   The time the data was created.
     * @param type      The type of data it is.
     */
    public DataModel(String value, String time, DataTypeModel type) {
        this.value = value;
        this.time = time;
        this.type = type;
    }

    /**
     * Constructor for DataReceiver, initializes the variables belonging to this class.
     *
     * @param value The value as such of the data received.
     * @param time The time the data was created.
     */
    public DataModel(String value, String time) {
        this.value = value;
        this.time = time;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public long getDataTypeId() { return type.getId(); }
    public String getTime() { return this.time; }

    /**
     * The toString method used to sort the data from the db into the correct order.
     */
    @Override
    public String toString() {
        return String.format("DataReceiver[id=%d, value='%s', time='%s']", id, value, time);
    }
}
