package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DeviceReceiver;

/**
 * Interface containing lists used when searching for devices in the DB.
 *
 * @author Erik Kellgren
 * @version 0.0.0
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceReceiver, Long> {
    List<DeviceReceiver> findByDevice(String device);
    List<DeviceReceiver> findAll();
}
