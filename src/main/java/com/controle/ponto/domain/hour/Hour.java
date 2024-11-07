package com.controle.ponto.domain.hour;

import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.typedate.TypeDate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Table(name = "hours")
@Entity(name = "hours")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Hour {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_Id")
    Employee employee;

    Date date;

    @Column(name = "type")
    int isNegative;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeDate_Id")
    TypeDate typeDate;

    @Column(name = "hour1")
    LocalTime enterMorning;

    @Column(name = "hour2")
    LocalTime exitMorning;

    @Column(name = "hour3")
    LocalTime enterAfternoon;

    @Column(name = "hour4")
    LocalTime exitAfternoon;

    @Column(name = "hour5")
    LocalTime enterOvertime;

    @Column(name = "hour6")
    LocalTime exitOvertime;

    LocalTime balance;

    public Hour(HourRequestDTO data, Employee employee, TypeDate typeDate, LocalTime balance){
        this.id = data.getId();
        this.date = data.getDate();
        this.setEmployee(employee);
        this.setTypeDate(typeDate);
        this.enterMorning = data.getEnterMorning();
        this.exitMorning = data.getExitMorning();
        this.enterAfternoon = data.getEnterAfternoon();
        this.exitAfternoon = data.getExitAfternoon();
        this.enterOvertime = data.getEnterOvertime();
        this.exitOvertime = data.getExitOvertime();
        this.balance = balance;
    }

}
