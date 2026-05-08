package com.controle.ponto.business.employee;

import com.controle.ponto.domain.entity.company.Company;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.entity.employee.Employee;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.mappers.employee.EmployeeMapper;
import com.controle.ponto.domain.entity.role.Role;
import com.controle.ponto.persistence.company.CompanyRepository;
import com.controle.ponto.persistence.employee.EmployeeRepository;
import com.controle.ponto.persistence.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeBusiness {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeBusiness.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<EmployeeResponseDTO> findAll(String companyId){
        logger.info("Buscando todos os funcionários para empresa {}", companyId);
        var employees = employeeRepository.findAllWithRoleAndCompanyByCompanyId(companyId);

        List<EmployeeResponseDTO> listEmployee = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO empl = EmployeeMapper.INSTANCE.toResponseDTO(employee);
            listEmployee.add(empl);
        }

        logger.info("Encontrados {} funcionários para empresa {}", listEmployee.size(), companyId);
        return listEmployee;
    }

    public org.springframework.data.domain.Page<EmployeeResponseDTO> findAllPaginated(String companyId, org.springframework.data.domain.Pageable pageable){
        logger.info("Buscando funcionários paginados para empresa {} com paginação {}", companyId, pageable);
        var employeesPage = employeeRepository.findAllByCompanyId(companyId, pageable);
        var result = employeesPage.map(EmployeeMapper.INSTANCE::toResponseDTO);
        logger.info("Retornando página com {} funcionários de {} total para empresa {}",
                result.getNumberOfElements(), result.getTotalElements(), companyId);
        return result;
    }

    public EmployeeResponseDTO findById(String id, String companyId){
        logger.info("Buscando funcionário {} para empresa {}", id, companyId);
        Optional<Employee> employee = employeeRepository.findByIdWithRoleAndCompanyByCompanyId(id, companyId);
        if (employee.isEmpty()){
            logger.warn("Funcionário {} não encontrado ou não pertence à empresa {}", id, companyId);
            throw new BadRequestCustomException("Funcionário não cadastrado ou não pertence à empresa!");
        }
        Employee foundEmployee = employee.get();
        logger.info("Funcionário {} encontrado para empresa {}", id, companyId);

        return EmployeeMapper.INSTANCE.toResponseDTO(foundEmployee);
    }

    public EmployeeResponseDTO post(EmployeeRequestDTO data, String companyId){
        logger.info("Iniciando criação de funcionário {} para empresa {}", data.getName(), companyId);
        VerifyEmployeeFindByName(data);
        Optional<Role> role = getOptionalRole(data);
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()){
            logger.error("Empresa {} não encontrada ao criar funcionário", companyId);
            throw new NotFoundCustomException("Empresa não cadastrada!");
        }

        Company companyFound = company.get();
        Role roleFound = role.get();
        Employee newEmployee = new Employee(data, roleFound, companyFound);

        employeeRepository.save(newEmployee);
        logger.info("Funcionário {} criado com sucesso para empresa {}", newEmployee.getId(), companyId);

        return EmployeeMapper.INSTANCE.toResponseDTO(newEmployee);
    }

    private void VerifyEmployeeFindByName(EmployeeRequestDTO data) {
        logger.debug("Verificando se funcionário com nome {} já existe", data.getName());
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByName(data.getName()));
        if (employee.isPresent()){
            logger.warn("Tentativa de criar funcionário com nome já existente: {}", data.getName());
            throw new BadRequestCustomException("Funcionário já cadatrado!");
        }
        logger.debug("Nome {} disponível para novo funcionário", data.getName());
    }

    @Transactional
    public EmployeeResponseDTO put(EmployeeRequestDTO data, String companyId){
        logger.info("Iniciando atualização de funcionário {} para empresa {}", data.getId(), companyId);
        Optional<Employee> employee = employeeRepository.findByIdAndCompanyId(data.getId(), companyId);
        if (employee.isEmpty()){
            logger.warn("Funcionário {} não encontrado ou não pertence à empresa {} para atualização", data.getId(), companyId);
            throw new BadRequestCustomException("Funcionário não cadastrado ou não pertence à empresa!");
        }
        Optional<Role> role = getOptionalRole(data);
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isEmpty()){
            logger.error("Empresa {} não encontrada ao atualizar funcionário", companyId);
            throw new NotFoundCustomException("Empresa não cadastrada!");
        }

        Company companyFound = company.get();
        Role roleFound = role.get();
        Employee newEmployee = employee.get();
        SetDadosUpdateEmployee(newEmployee, data, companyFound, roleFound);
        logger.info("Funcionário {} atualizado com sucesso para empresa {}", newEmployee.getId(), companyId);

        return EmployeeMapper.INSTANCE.toResponseDTO(newEmployee);
    }

    private Optional<Role> getOptionalRole(EmployeeRequestDTO data) {
        logger.debug("Buscando função {}", data.getRoleId());
        Optional<Role> role = roleRepository.findById(data.getRoleId());
        if (!role.isPresent()){
            logger.error("Função {} não encontrada", data.getRoleId());
            throw new NotFoundCustomException("Função não cadastrada!");
        }
        logger.debug("Função {} encontrada", data.getRoleId());
        return role;
    }

    private Optional<Company> getOptionalCompany(EmployeeRequestDTO data) {
        logger.debug("Buscando empresa {}", data.getCompanyId());
        Optional<Company> company = companyRepository.findById(data.getCompanyId());
        if (!company.isPresent()){
            logger.error("Empresa {} não encontrada", data.getCompanyId());
            throw new NotFoundCustomException("Empresa não cadastrada!");
        }
        logger.debug("Empresa {} encontrada", data.getCompanyId());
        return company;
    }

    private void SetDadosUpdateEmployee(Employee target, EmployeeRequestDTO source, Company company, Role role){
        logger.debug("Atualizando dados do funcionário {}: nome={}, empresa={}, função={}",
                target.getId(), source.getName(), company.getName(), role.getName());
        target.setAdmission(source.getAdmission());
        target.setName(source.getName());
        target.setCompany(company);
        target.setGender(source.getGender());
        target.setStatus(source.getStatus());
        target.setRole(role);
    }

}
