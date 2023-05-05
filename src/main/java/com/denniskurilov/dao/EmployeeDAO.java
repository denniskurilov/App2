package com.denniskurilov.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.denniskurilov.models.Employee;

@Component
public class EmployeeDAO {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public EmployeeDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Employee> getAll() {
		return jdbcTemplate.query(
				"SELECT id, first_name firstname, last_name lastname, age, email FROM employee ORDER BY id",
				new BeanPropertyRowMapper<>(Employee.class));
	}

	public Employee get(long id) {
		return jdbcTemplate
				.query("SELECT id, first_name firstname, last_name lastname, age, email FROM employee WHERE id = ?",
						new Object[] { id }, new BeanPropertyRowMapper<>(Employee.class))
				.stream().findAny().orElse(null);
	}

	public void save(Employee employee) {
		jdbcTemplate.update("INSERT INTO employee(first_name, last_name, age, email) VALUES(?, ?, ?, ?)",
				employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getEmail());
	}

	public void update(Employee employee) {
		jdbcTemplate.update("UPDATE employee SET first_name = ?, last_name = ?, age = ?, email = ? WHERE id = ?",
				employee.getFirstName(), employee.getLastName(), employee.getAge(), employee.getEmail(),
				employee.getId());
	}

	public void delete(long id) {
		jdbcTemplate.update("DELETE FROM employee WHERE id = ?", id);
	}
}
