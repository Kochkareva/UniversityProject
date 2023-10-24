package com.ulstu.University.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ulstu.University.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
