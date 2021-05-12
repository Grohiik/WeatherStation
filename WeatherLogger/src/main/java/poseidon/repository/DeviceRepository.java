package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DeviceModel;

/**
 * Interface containing lists used when searching for devices in the DB.
 *
 * @author Erik Kellgren
 * @version 0.1.0
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceModel, Long> {
    List<DeviceModel> findAll();
    DeviceModel findByDeviceName(String deviceName);
}
