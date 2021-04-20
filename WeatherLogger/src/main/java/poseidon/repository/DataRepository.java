package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DataReceiver;

/**
 * Interface containing lists used when searing for data in the DB.
 *
 * @author Erik Kellgren
 * @version 0.1.0
 */
@Repository
public interface DataRepository extends JpaRepository<DataReceiver, Long> {
    List<DataReceiver> findAll();
    List<DataReceiver> findByDevice(String device);
}
