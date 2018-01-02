package se.beatit.hsh.raspberry.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.beatit.hsh.raspberry.entities.Measurement;

import java.util.Date;

/**
 * Created by stefan on 12/31/17.
 */

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

    @Query(value = "select m from Measurement m where m.fromDate > :fromDate and m.toDate < :toDate order by m.outTemp desc")
    Page<Measurement> findMaxOutTemp(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query(value = "select m from Measurement m where m.fromDate > :fromDate and m.toDate < :toDate order by m.outTemp")
    Page<Measurement> findMinOutTemp(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query(value = "select m from Measurement m where m.fromDate > :fromDate and m.toDate < :toDate order by m.avarageWatt desc")
    Page<Measurement> findMaxWattUsage(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query(value = "select m from Measurement m where m.fromDate > :fromDate and m.toDate < :toDate order by m.avarageWatt")
    Page<Measurement> findMinWattUsage(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, Pageable pageable);

    @Query(value = "select SUM(m.wattHoursUsed) from Measurement m where m.fromDate > :fromDate and m.toDate < :toDate")
    Long findWattHoursUsed(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT AVG(m.outTemp) from Measurement m  where m.fromDate > :fromDate and m.toDate < :toDate")
    Float getAvarageOutTemp(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
