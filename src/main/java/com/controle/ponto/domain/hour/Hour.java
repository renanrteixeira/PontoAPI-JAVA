package com.controle.ponto.domain.hour;

import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.enumerator.TypeHour;
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

    int type;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeDate_Id")
    TypeDate typeDate;

    LocalTime hour1;

    LocalTime hour2;

    LocalTime hour3;

    LocalTime hour4;

    LocalTime hour5;

    LocalTime hour6;

    LocalTime balance;

    public Hour(HourRequestDTO data, Employee employee, TypeDate typeDate, LocalTime balance){
        this.id = data.getId();
        this.date = data.getDate();
        this.setEmployee(employee);
        this.setTypeDate(typeDate);
        this.hour1 = data.getHour1();
        this.hour2 = data.getHour2();
        this.hour3 = data.getHour3();
        this.hour4 = data.getHour4();
        this.hour5 = data.getHour5();
        this.hour6 = data.getHour6();
        this.balance = balance;
    }

}
