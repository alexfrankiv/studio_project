package repository;

import java.sql.SQLException;
import java.util.List;

import model.Sale;

public interface ISaleRepository {
	
	int add(Sale sale) throws SQLException;
	boolean remove(Sale sale) throws SQLException;
	boolean remove(int id) throws SQLException;
	boolean update(Sale sale) throws SQLException;
    List<Sale> getAll() throws SQLException;
    List<Sale> getLicenses() throws SQLException;
    List<Sale> getRecords() throws SQLException;
    Sale getById(int id) throws SQLException;

}
