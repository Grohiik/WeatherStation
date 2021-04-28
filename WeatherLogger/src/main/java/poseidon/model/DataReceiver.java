package poseidon.model;

import java.io.Serial;
import java.io.Serializable;
import javax.persistence.*;

/**
 * This class is used to specify which data excluding which device we want to add to the DB, each
 * set of data is specified by a @Column mark. It implements serializable because of it being sent
 * across a stream.
 *
 * @author Marcus Linné
 * @author Erik Kellgren
 * @version 0.1.0
 */
@Entity
@Table(name = "DATA_STORED")
public class DataReceiver implements Serializable {
    @Serial private static final long serialVersionUID = -2343243243242432341L;
    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

    @Column(name = "value") private String value;

    @Column(name = "created") private String created;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private DataTypeReceiver type;

    /**
     * Protected constructor required by Spring.
     */
    protected DataReceiver() {}

    public DataReceiver(String value, String created, DataTypeReceiver type) {
        this.value = value;
        this.created = created;
        this.type = type;
    }

    public DataReceiver(String value, String created) {
        this.value = value;
        this.created = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getDataTypeId() {
        return type.getId();
    }

    /**
     * The toString method used to sort the data from the db into the correct order.
     */
    @Override
    public String toString() {
        return String.format("DataReceiver[id=%d, value='%s', created='%s']", id, value, created);
    }
}
