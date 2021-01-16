package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;
import model.entities.Department;


public class Program {

	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Seller seller = sellerDao.findById(3);
		System.out.println("=== TEST 1: seller findById ===");
		System.out.println(seller);
		
		System.out.println("\n=== TEST 2: seller findDepartment ===");
		List<Seller> sellers = sellerDao.findByDepartment(new Department(2, null));
		for(Seller obj: sellers)
			System.out.println(obj);
	}

}
