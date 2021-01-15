package model.dao.impl;

import java.util.List;

import db.DB;
import db.DbException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import model.dao.SellerDao;
import model.entities.Seller;
import model.entities.Department;

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
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(query);	// MAKE QUERY
			st.setInt(1, id);					// INSERT PARAMETERS FOR QUERY
			rs = st.executeQuery();				// EXECUTING QUERY
			
			// VERIFYING THE CONCLUSION RESULT
			if(rs.next()) {
				// INITIALIZING INSTANCES OF OBJECTS IN MEMORY
				// DEPARTMENT
				Department dep = instatiateDepartment(rs);
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

	private Department instatiateDepartment(ResultSet rs) throws SQLException {
		 return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		return new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);

	}
	
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
