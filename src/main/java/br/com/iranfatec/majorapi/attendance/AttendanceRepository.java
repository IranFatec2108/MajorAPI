package br.com.iranfatec.majorapi.attendance;

import br.com.iranfatec.majorapi.service.ProvidedService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {


    @Query("SELECT a FROM Attendance a WHERE a.valid = TRUE ORDER BY a.dateTime DESC")
    List<Attendance> findAllValidOrderByDateTimeDesc();

    @Query("SELECT a FROM Attendance a WHERE a.valid = FALSE ORDER BY a.dateTime DESC")
    List<Attendance> findAllInvalidOrderByDateTimeDesc();
}
