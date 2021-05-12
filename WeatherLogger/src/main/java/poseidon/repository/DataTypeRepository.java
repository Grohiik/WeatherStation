package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DataTypeModel;

/**
 * Interface for the DataTypes
 *
 * @author Marcus Linné
 * @version 0.2.0
 */
@Repository
public interface DataTypeRepository extends JpaRepository<DataTypeModel, Long> {
    List<DataTypeModel> findAll();
    List<DataTypeModel> findAllByDevice_id(int id);
    DataTypeModel findByUnit(String unit);
    DataTypeModel findByName(String name);
    DataTypeModel findByNameAndDevice_id(String name, int deviceId);
    List<DataTypeModel> findAllByName(String name);
}