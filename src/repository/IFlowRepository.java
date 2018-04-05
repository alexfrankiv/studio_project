package repository;

import model.Flow;
import model.dto.PreparedRevenue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface IFlowRepository {
	
	int add(Flow flow) throws SQLException;
	boolean addRevenue(PreparedRevenue pr) throws SQLException;
    boolean remove(Flow flow) throws SQLException;
    boolean update(Flow flow) throws SQLException;
    List<Flow> getAll() throws SQLException;
    BigDecimal getSumBySaleId(int saleId) throws SQLException;
    List<Flow> getAllBySaleId(int saleId) throws SQLException;
    Flow getById(int id) throws SQLException;
    List<PreparedRevenue> getAlbumRevenues(Flow flow, int album_id) throws SQLException;
    BigDecimal paidAMonth(int saleId, int month) throws SQLException;

}
