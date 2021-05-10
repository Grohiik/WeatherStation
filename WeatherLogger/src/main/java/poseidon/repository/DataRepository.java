package poseidon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poseidon.model.DataModel;

/**
 * Interface containing lists used when searing for data in the DB.
 *
 * @author Erik Kellgren
 * @version 0.1.0
 */
@Repository
public interface DataRepository extends JpaRepository<DataModel, Long> {
    List<DataModel> findAll();
    List<DataModel> findByType_id(int id);
    List<DataModel> findByType_idAndTimeGreaterThanEqual(int id, String startDate);
    List<DataModel> findByType_idAndTimeGreaterThanEqualAndTimeLessThanEqual(int id,
                                                                             String startDate,
                                                                             String endDate);
    List<DataModel> findByType_idOrderByTimeDesc(int id);
    List<DataModel> findByType_idOrderByTimeAsc(int id);
    List<DataModel> findTop1ByType_idOrderByTimeDesc(int id);
}
