package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	// CONTRACTS
	void insert(Seller obj); // INSERT A NEW SELLER
	void update(Seller obj); // UPDATE AN SELLER
	void delete(Seller obj); // DELETE A SELLER
	Seller findById(Integer id); // FIND SOMEONE SELLER BASED ON ID
	List<Seller> findAll(); // SEARCH ALL SELLERS
}
