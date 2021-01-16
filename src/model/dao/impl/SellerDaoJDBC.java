package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	// DEPENDENCY
	private Connection conn;
	
	// CONSTRUCTOR TO FORCE THE INJECTION DEPENDENCY
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	/*METHOD:		findById(Integer id)	
	 *OBJECTIVE:	find a seller based on id */
	@Override
	public Seller findById(Integer id) {
		// QUERY
		String query = "SELECT seller.*, department.Name as DepName " +
						"FROM seller INNER JOIN department " +
						"ON seller.DepartmentId = department.Id " +
						"WHERE seller.Id = ?";
		// VARIABLE TO PREPARE A QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE QUERY
			st.setInt(1, id);					// INSERT PARAMETERS OF QUERY
			rs = st.executeQuery();				// EXECUTING QUERY
			
			// VERIFYING THE RESULT OF QUERY
			if(rs.next()) {
				// INITIALIZING INSTANCES OF OBJECTS IN MEMORY
				// DEPARTMENT
				Department dep = instantiateDepartment(rs);
				// SELLER
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {	// CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return null;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		 return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		return new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);

	}
	
	@Override
	public List<Seller> findAll() {
		// LIST OF SELLERS
		List<Seller> sellers = new ArrayList<>();
		// COLLECTION TO SAVE UNIQUES REGISTERS OF DEPARTMENT
		Map<Integer, Department> map = new HashMap<>();
		// QUERY
		String query =
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"ORDER BY Name";
		// VARIABLE TO PREPARE QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE A QUERY
			rs = st.executeQuery();				// EXECUTING QUERY AND SAVE RESULTS

			// VERIFYING RESULTS OF QUERY
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				// CHECKING IF THE DEPARTMENT ALREADY EXISTS OR NOT 
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				// CREATE A SELLER
				Seller obj = instantiateSeller(rs, dep);
				
				// ADD SELLERS INTO SELLER'S COLECTION
				sellers.add(obj);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {	// CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		return sellers;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		// LIST OF SELLERS
		List<Seller> sellers = new ArrayList<>();
		// COLLECTION TO SAVE REGISTERS OF DEPARTMENT
		Map<Integer, Department> map = new HashMap<>();
		// QUERY
		String query =
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"WHERE DepartmentId = ? " +
				"ORDER BY Name";
		// VARIABLE TO PREPARE QUERY
		PreparedStatement st = null;
		// VARIABLE TO SAVE RESULT QUERY
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE A QUERY
			st.setInt(1, department.getId());	// INSERTING PARAMETERS OF QUERY
			rs = st.executeQuery();				// EXECUTING QUERY AND SAVE RESULTS

			// VERYFING RESULTS OF QUERY
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				// CREATE A SELLER
				Seller obj = instantiateSeller(rs, dep);
				
				// ADD SELLERS INTO SELLER'S COLECTION
				sellers.add(obj);
			}
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
		} finally {	// CLOSING RESOURCES
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
		return sellers;
	}

}
