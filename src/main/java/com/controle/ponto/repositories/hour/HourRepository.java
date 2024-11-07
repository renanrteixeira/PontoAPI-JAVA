package com.controle.ponto.repositories.hour;

import com.controle.ponto.domain.hour.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HourRepository extends JpaRepository<Hour, String> {
    List<Hour> findByEmployeeId(String EmployeeId);
    List<Hour> findByDate(Date date);
    @Query(value = "SELECT * FROM hours WHERE employee_Id = ?1 and date = ?2", nativeQuery = true)
    List<Hour> findByEmployeeIdDate(String EmployeeId, String date);
//    @Query(value = "SELECT h FROM hours h WHERE h.employee.id = :EMPLOYEE and h.date = :DATE")
//    List<Hour> findByEmployeeIdDate(@Param("EMPLOYEE") String EmployeeId, @Param("DATE") String date);
}
