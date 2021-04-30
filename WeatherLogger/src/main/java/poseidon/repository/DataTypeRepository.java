package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DataTypeReceiver;

/**
 * Interface for the DataTypes
 *
 * @author Marcus Linné
 * @version 0.1.0
 */
@Repository
public interface DataTypeRepository extends JpaRepository<DataTypeReceiver, Long> {
    List<DataTypeReceiver> findAll();
    // List<DataTypeReceiver> findByDevice(String device);
    List<DataTypeReceiver> findAllByDevice_id(long id);
    DataTypeReceiver findByType(String type);
    List<DataTypeReceiver> findAllByType(String type);
}