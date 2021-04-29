package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import poseidon.model.DataTypeReceiver;
import poseidon.model.DeviceReceiver;

/**
 * Interface for the DataTypes
 *
 * @author Marcus Linné
 * @version 0.1.0
 */
public interface DataTypeRepository extends JpaRepository<DataTypeReceiver, Long> {
    List<DataTypeReceiver> findAll();
    // List<DataTypeReceiver> findByDevice(String device);
    List<DataTypeReceiver> findAllByDevice_id(long id);
    DataTypeReceiver findByType(String type);
}