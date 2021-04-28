package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import poseidon.model.DataTypeReceiver;

/**
 * Interface for the DataTypes
 *
 * @author Marcus Linné
 * @version 0.0.0
 */
public interface DataTypeRepository extends JpaRepository<DataTypeReceiver, Long> {
    List<DataTypeReceiver> findAll();
    //List<DataTypeReceiver> findByDevice(String device);
}