package com.controle.ponto.persistence.hour;

import com.controle.ponto.domain.entity.hour.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HourRepository extends JpaRepository<Hour, String> {
    List<Hour> findByEmployeeId(String EmployeeId);
    @Query(value = "SELECT id, employee_Id, date, type, type_date_id, hour1, hour2, hour3, hour4, hour5, hour6, balance " +
            "FROM hours WHERE employee_Id = ?1 and date = ?2", nativeQuery = true)
    List<Hour> findByEmployeeIdDate(String EmployeeId, String date);
//    @Query(value = "SELECT h FROM hours h WHERE h.employee.id = :EMPLOYEE and h.date = :DATE")
//    List<Hour> findByEmployeeIdDate(@Param("EMPLOYEE") String EmployeeId, @Param("DATE") String date);
}
