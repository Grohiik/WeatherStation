package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DataReceiver;

/*
 * This class is intended for testing and edu
 *
 * @author Erik Kellgren
 */
@Repository
public interface DataRepository extends JpaRepository<DataReceiver, Long> {
    List<DataReceiver> findByDevice(String device);
    List<DataReceiver> findAll();
}
