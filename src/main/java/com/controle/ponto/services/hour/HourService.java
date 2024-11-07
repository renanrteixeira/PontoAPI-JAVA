package com.controle.ponto.services.hour;

import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.dto.hour.HourResponseDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.enumerator.TypeHour;
import com.controle.ponto.domain.hour.Hour;
import com.controle.ponto.domain.typedate.TypeDate;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.repositories.employee.EmployeeRepository;
import com.controle.ponto.repositories.hour.HourRepository;
import com.controle.ponto.repositories.typedate.TypeDateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class HourService {

    @Autowired
    HourRepository hourRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TypeDateRepository typeDateRepository;

    public List<HourResponseDTO> findAll(){
        var hours = hourRepository.findAll();

        List<HourResponseDTO> hourList = new ArrayList<>();

        for (Hour hour : hours){
            HourResponseDTO newHour = new HourResponseDTO(hour);
            hourList.add(newHour);
        }

        return hourList;
    }

    public HourResponseDTO findById(@PathVariable String id){
        Optional<Hour> hour = getHourFindById(id);
        Hour hourFound = hour.get();

        return new HourResponseDTO(hourFound);
    }

    public List<HourResponseDTO> findByEmployee(@PathVariable String id){
        var hours = hourRepository.findByEmployeeId(id);

        List<HourResponseDTO> hourList = new ArrayList<>();

        for (Hour hour : hours){
            HourResponseDTO newHour = new HourResponseDTO(hour);
            hourList.add(newHour);
        }

        return hourList;
    }

    private Optional<Hour> getHourFindById(String id) {
        Optional<Hour> hour = hourRepository.findById(id);
        if (!hour.isPresent()){
            throw new NotFoundCustomException("Hora não encontrada!");
        }
        return hour;
    }

    public HourResponseDTO post(HourRequestDTO data){
        String date = formatDate(data.getDate());
        Optional<List<Hour>> hour = Optional.ofNullable(hourRepository.findByEmployeeIdDate(data.getEmployeeId(), date));
        if (!hour.get().isEmpty()) {
            throw new BadRequestCustomException("Hora já inserida!");
        }
        Optional<Employee> employee = getEmployeeFindById(data);
        Optional<TypeDate> typeDate = getTypeDateFindById(data);

        Hour newHour = ProcessHour(data, typeDate.get().getTime(), employee.get(), typeDate.get());

        hourRepository.save(newHour);

        return new HourResponseDTO(newHour);
    }

    private Optional<Employee> getEmployeeFindById(HourRequestDTO data) {
        Optional<Employee> employee = employeeRepository.findById(data.getEmployeeId());
        if (!employee.isPresent()){
            throw new NotFoundCustomException("Funcionário não encontrado!");
        }
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
        var durationMorning = calcularIntervaloPeriodo(data.getEnterMorging(), data.getExitMorging());
        var durationAfternoon = calcularIntervaloPeriodo(data.getEnterAfternoon(), data.getExitAfternoon());
        var durationOvertime = calcularIntervaloPeriodo(data.getEnterOvertime(), data.getExitOvertime());

        var totalDay = calcularSomaDia(durationMorning, durationAfternoon, durationOvertime);
        var result = calcularIntervaloPeriodo(totalDay, timeDay);
        var typeHour = TypeHour.Minus.getType();
        if (totalDay.isAfter(timeDay)){
            LocalTime hour = LocalTime.of(23,59);
            result = calcularIntervaloPeriodo(result, hour);
            result = result.plusMinutes(1);
            typeHour = TypeHour.Plus.getType();
        }

        Hour returnHour = new Hour(data, employee, typeDate, result);
        returnHour.setIsNegative(typeHour);
        return returnHour;
    }

    private String formatHour(Date hora, boolean negative){
        String pattern = "HH:mm:ss";
        if (negative){
            pattern = "-HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(hora);
    }

    private String formatDate(Date date){
        String pattern = "YYYY-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);
    }

    private static Date calendarGetTime(int hour, int minute) {
        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.HOUR, hour);
        c1.set(Calendar.MINUTE, minute);
        return c1.getTime();
    }

    private Optional<TypeDate> getTypeDateFindById(HourRequestDTO data) {
        Optional<TypeDate> typeDate = typeDateRepository.findById(data.getTypeDateId());
        if (!typeDate.isPresent()){
            throw new NotFoundCustomException("Tipo de hora não encontrada!");
        }
        return typeDate;
    }

    @Transactional
    public HourResponseDTO put(HourRequestDTO data){
        Optional<Hour> hourFound = getHourFindById(data.getId());
        if (!hourFound.isPresent()){
            throw new NotFoundCustomException("Hora não encontrada!");
        }
        Optional<Employee> employee = getEmployeeFindById(data);
        Optional<TypeDate> typeDate = getTypeDateFindById(data);

        Hour newHour = hourFound.get();

        Hour processHour = ProcessHour(data, typeDate.get().getTime(), employee.get(), typeDate.get());

        newHour.setEmployee(processHour.getEmployee());
        newHour.setDate(processHour.getDate());
        newHour.setIsNegative(processHour.getIsNegative());
        newHour.setTypeDate(processHour.getTypeDate());
        newHour.setEnterMorning(processHour.getEnterMorning());
        newHour.setExitMorning(processHour.getExitMorning());
        newHour.setEnterAfternoon(processHour.getEnterAfternoon());
        newHour.setExitAfternoon(processHour.getExitAfternoon());
        newHour.setEnterOvertime(processHour.getEnterOvertime());
        newHour.setExitOvertime(processHour.getExitOvertime());
        newHour.setBalance(processHour.getBalance());

        return new HourResponseDTO(newHour);
    }

    public void delete(String id){
        Optional<Hour> hourFound = getHourFindById(id);
        if (!hourFound.isPresent()){
            throw new NotFoundCustomException("Hora não encontrada!");
        }
        Hour newHour = hourFound.get();

        hourRepository.delete(newHour);
    }

}
