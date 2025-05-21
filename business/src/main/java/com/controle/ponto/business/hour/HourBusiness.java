package com.controle.ponto.business.hour;

import com.controle.ponto.domain.entity.company.Company;
import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.dto.hour.HourResponseDTO;
import com.controle.ponto.domain.entity.employee.Employee;
import com.controle.ponto.domain.enumerator.TypeHour;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.entity.hour.Hour;
import com.controle.ponto.domain.mappers.hour.HourMapper;
import com.controle.ponto.domain.entity.role.Role;
import com.controle.ponto.domain.entity.typedate.TypeDate;
import com.controle.ponto.persistence.company.CompanyRepository;
import com.controle.ponto.persistence.employee.EmployeeRepository;
import com.controle.ponto.persistence.hour.HourRepository;
import com.controle.ponto.persistence.role.RoleRepository;
import com.controle.ponto.persistence.typedate.TypeDateRepository;
import com.controle.ponto.resources.utils.Date;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.flywaydb.core.internal.util.CollectionsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class HourBusiness {

    @Autowired
    private HourRepository hourRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TypeDateRepository typeDateRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<HourResponseDTO> findAll(){
        var hours = hourRepository.findAll();

        List<HourResponseDTO> hourList = new ArrayList<>();

        for (Hour hour : hours){
            SetHour(hour);
            HourResponseDTO newHour = HourMapper.INSTANCE.toResponseDTO(hour);
            hourList.add(newHour);
        }

        return hourList;
    }

    public HourResponseDTO findById(String id){
        Hour hour = getHourFindById(id);
        SetHour(hour);
        return HourMapper.INSTANCE.toResponseDTO(hour);
    }

    private void SetHour(Hour target) {
        Optional<Employee> employee = employeeRepository.findById(target.getEmployee().getId());
        Optional<TypeDate> typeDate = typeDateRepository.findById(target.getTypeDate().getId());
        Employee newEmployee = employee.get();
        Optional<Role> role = roleRepository.findById(newEmployee.getRole().getId());
        Optional<Company> company = companyRepository.findById(newEmployee.getCompany().getId());
        newEmployee.setRole(role.get());
        newEmployee.setCompany(company.get());
        target.setEmployee(newEmployee);
        target.setTypeDate(typeDate.get());
    }

    public List<HourResponseDTO> findByEmployee(String id){
        var hours = hourRepository.findByEmployeeId(id);

        List<HourResponseDTO> hourList = new ArrayList<>();

        for (Hour hour : hours){
            SetHour(hour);
            HourResponseDTO newHour = HourMapper.INSTANCE.toResponseDTO(hour);
            hourList.add(newHour);
        }

        return hourList;
    }

    private Hour getHourFindById(String id) {
        Hour hour = hourRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomException("Hora não encontrada!"));

        return hour;
    }

    public HourResponseDTO post(HourRequestDTO data){
        String date = Date.formatDate(data.getDate(), "YYYY-MM-dd");
        VerifyExistsEmployeeInDate(data, date);
        Employee employee = getEmployeeFindById(data);
        TypeDate typeDate = getTypeDateFindById(data);

        Hour newHour = ProcessHour(data, typeDate.getTime(), employee, typeDate);

        hourRepository.save(newHour);

        return HourMapper.INSTANCE.toResponseDTO(newHour);
    }

    private void VerifyExistsEmployeeInDate(HourRequestDTO data, String date) {
        List<Hour> hours = hourRepository.findByEmployeeIdDate(data.getEmployeeId(), date);
        if (hours != null && !hours.isEmpty()) {
            throw new BadRequestCustomException("Hora já inserida!");
        }
    }

    private Employee getEmployeeFindById(HourRequestDTO data) {
        Employee employee = employeeRepository.findById(data.getEmployeeId())
                .orElseThrow(() -> new NotFoundCustomException("Funcionário não encontrado!"));

        return employee;
    }

    private LocalTime calcularIntervaloPeriodo(LocalTime enter, LocalTime exit){
        return exit.minusHours(enter.getHour()).minusMinutes(enter.getMinute());
    }

    private LocalTime calcularSomaDia(LocalTime morning, LocalTime afternoon, LocalTime overtime){
        var result = morning.plusHours(afternoon.getHour()).plusMinutes(afternoon.getMinute());

        return result.plusHours(overtime.getHour()).plusMinutes(overtime.getMinute());
    }

    private Hour ProcessHour(HourRequestDTO data, LocalTime timeDay, Employee employee, TypeDate typeDate){
        var durationMorning = calcularIntervaloPeriodo(data.getEnterMorning(), data.getExitMorning());
        var durationAfternoon = calcularIntervaloPeriodo(data.getEnterAfternoon(), data.getExitAfternoon());
        var durationOvertime = calcularIntervaloPeriodo(data.getEnterOvertime(), data.getExitOvertime());

        var totalDay = calcularSomaDia(durationMorning, durationAfternoon, durationOvertime);
        var result = calcularIntervaloPeriodo(totalDay, timeDay);

        var workedMoreThenExpetected = totalDay.isAfter(timeDay);
        var isZeroDeviation = result.equals(LocalTime.of(0, 0));

        var typeHour = getTypeHour(isZeroDeviation, workedMoreThenExpetected);

        if (workedMoreThenExpetected){
            LocalTime fullDay = LocalTime.of(23,59);
            result = calcularIntervaloPeriodo(result, fullDay).plusMinutes(1);
        }

        Hour returnHour = new Hour(data, employee, typeDate, result);
        returnHour.setIsNegative(typeHour);

        return returnHour;
    }

    private static int getTypeHour(boolean isZeroDeviation, boolean workedMoreThenExpetected) {
        var typeHour = TypeHour.Minus.getType();
        if (isZeroDeviation || workedMoreThenExpetected){
            typeHour = TypeHour.Plus.getType();
        }

        return typeHour;
    }

    private TypeDate getTypeDateFindById(HourRequestDTO data) {
        TypeDate typeDate = typeDateRepository.findById(data.getTypeDateId())
                .orElseThrow(() -> new NotFoundCustomException("Tipo de data não encontrada!"));

        return typeDate;
    }

    private void updateHour(Hour target, Hour source) {
        target.setEmployee(source.getEmployee());
        target.setDate(source.getDate());
        target.setIsNegative(source.getIsNegative());
        target.setTypeDate(source.getTypeDate());
        target.setEnterMorning(source.getEnterMorning());
        target.setExitMorning(source.getExitMorning());
        target.setEnterAfternoon(source.getEnterAfternoon());
        target.setExitAfternoon(source.getExitAfternoon());
        target.setEnterOvertime(source.getEnterOvertime());
        target.setExitOvertime(source.getExitOvertime());
        target.setBalance(source.getBalance());
    }

    @Transactional
    public HourResponseDTO put(HourRequestDTO data){
        Hour hourFound = getHourFindById(data.getId());
        Employee employee = getEmployeeFindById(data);
        TypeDate typeDate = getTypeDateFindById(data);

        Hour processHour = ProcessHour(data, typeDate.getTime(), employee, typeDate);

        updateHour(hourFound, processHour);

        return HourMapper.INSTANCE.toResponseDTO(hourFound);
    }

    public void delete(String id){
        Hour hour = getHourFindById(id);

        hourRepository.delete(hour);
    }
}
