package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	// CONTRACTS
	void insert(Department obj);		// INSERT A NEW DEPARTMENT
	void update(Department obj);		// UPDATE AN DEPARTMENT
	void delete(Department obj);		// DELETE A DEPARTMENT
	Department findById(Integer id);	// FIND SOMEONE DEPARTMENT BASED ON ID
	List<Department> findAll();			// SEARCH ALL DEPARTMENTS
	
}
