package repository.impl;

import app.Application;
import app.DBConnector;
import model.Flow;
import model.License;
import model.Record;
import model.Sale;
import repository.ISaleRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository implements ISaleRepository {

    
	@Override
	public int add(Sale sale) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(addSale, Statement.RETURN_GENERATED_KEYS);
		s.setString(1, sale.getClient());
		s.setDate(2, sale.getDate());
		s.setInt(3, sale.getAlbumId());
		int success = s.executeUpdate();
		if (success != 1) return -1;
		ResultSet rs = s.getGeneratedKeys();
	    rs.next();
	    int sale_id = rs.getInt(1);
		if (sale.getType() == Sale.SaleType.LICENSE) {
			s = c.prepareStatement(addLicense);
	        s.setInt(1,sale_id);
	        s.setBigDecimal(2, ((License) sale).getPrice());
	        s.setInt(3, ((License) sale).getPeriod());
	        success = s.executeUpdate();
	        if (success == 1) return sale_id;
	        else return -1;
		}
		s = c.prepareStatement(addRecord);
		
		BigDecimal album_price = Application.self.albumPriceRepository.getLastPriceFor(sale.getAlbum());
		int qty = ((Record) sale).getQty();
		int type = (Flow.FlowType.RECORD_COST).getValue();
		
		
        s.setInt(1, sale_id);
        s.setInt(2, qty);
        success = s.executeUpdate();
        /*
        if (success != 1) return false;
        s = c.prepareStatement(addRevenue);
        s.setDate(1, sale.getDate());
        s.setBigDecimal(2, album_price.multiply(new BigDecimal(qty)));
        s.setInt(3, sale_id);
        s.setInt(4, type);
        success = s.executeUpdate();
        */
        if (success == 1) return sale_id;
        else return -1;
        
	}

	@Override
	public boolean remove(Sale sale) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(removeSale);
		s.setInt(1, sale.getId());
		int success = s.executeUpdate();
        return (success == 1);
	}
	
	@Override
	public boolean remove(int id) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(removeSale);
		s.setInt(1, id);
		int success = s.executeUpdate();
        return (success == 1);
	}

	@Override
	public boolean update(Sale sale) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(updateSale);
        s.setString(1, sale.getClient());
        s.setDate(2, sale.getDate());
        s.setInt(3, sale.getAlbumId());
        s.setInt(4, sale.getId());
        int success = s.executeUpdate();
        if (success != 1) return false;
        
		if (sale.getType() == Sale.SaleType.LICENSE) {
			s = c.prepareStatement(updateLicense);
	        s.setBigDecimal(1, ((License)sale).getPrice());
	        s.setInt(2, ((License)sale).getPeriod());
	        s.setBoolean(3, ((License)sale).isPaid());
	        s.setInt(4, sale.getId());
	        success = s.executeUpdate();
	        return (success == 1);
		}
		
		s = c.prepareStatement(updateRecord);
        s.setInt(1, ((Record)sale).getQty());
        s.setInt(2, sale.getId());
        success = s.executeUpdate();
		return (success == 1);
	}

	@Override
	public List<Sale> getAll() throws SQLException {
		List<Sale> res = getLicenses();
		res.addAll(getRecords());
		return res;
	}

	@Override
	public Sale getById(int id) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(getById);
		s.setInt(1, id);
		ResultSet rs = s.executeQuery();
		rs.next();
		//Printer.print(rs);
		if (getType(rs) == Sale.SaleType.LICENSE) return toLicense(rs);
		return toRecord(rs);
	}
	
	@Override
	public List<Sale> getLicenses() throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(getLicenses);
		ResultSet rs = s.executeQuery();
        return toLicenseList(rs);
	}
	
	@Override
	public List<Sale> getRecords() throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(getRecords);
		ResultSet rs = s.executeQuery();
        return toRecordList(rs);
	}

//	private List<Sale> toList(ResultSet rs) throws SQLException {}

	private Sale toLicense(ResultSet rs) throws SQLException {
        return new License(rs.getInt("id"), rs.getDate("date"), rs.getString("client"),
				rs.getInt("album_id"), rs.getBigDecimal("price"), rs.getInt("period"), rs.getBoolean("paid"));
    }
	
	private Sale toRecord(ResultSet rs) throws SQLException {
		return new Record(rs.getInt("id"), rs.getDate("date"), rs.getString("client"),
				rs.getInt("album_id"), rs.getInt("amount"));
    }

	private Sale.SaleType getType(ResultSet rs) throws SQLException {
		int o = rs.getInt("amount");
		System.out.println(o);
		if (o == 0) return Sale.SaleType.LICENSE;
		return Sale.SaleType.RECORD;
    }
	
    private List<Sale> toLicenseList(ResultSet rs) throws SQLException {
        List<Sale> list = new ArrayList<Sale>();
        while (rs.next()) list.add(toLicense(rs));
        return list;
    }
    
    private List<Sale> toRecordList(ResultSet rs) throws SQLException {
        List<Sale> list = new ArrayList<Sale>();
        while (rs.next()) list.add(toRecord(rs));
        return list;
    }
	
	//QUERIES
	//private static final String getAll = "SELECT * FROM sale;";
	private static final String getLicenses =
			"SELECT id, client, date, album_id, price, period, paid FROM sale JOIN license ON sale.id=license.sale_id;";
	private static final String getRecords =
			"SELECT id, client, date, album_id, amount FROM sale JOIN record ON sale.id=record.sale_id;";
	private static final String getById =
			"SELECT id, client, date, album_id, price, period, paid, amount FROM sale LEFT JOIN license ON sale.id=license.sale_id LEFT JOIN record on sale.id=record.sale_id WHERE id=?;";
    
    private static final String addSale =
    		"INSERT INTO sale (client, date, album_id) VALUES (?,?,?);";
    private static final String addLicense =
    		"INSERT INTO license (sale_id, price, period) VALUES (?,?,?);";
    private static final String addRecord =
    		"INSERT INTO record (sale_id, amount) VALUES (?,?);";
    private static final String addRevenue =
    		"INSERT INTO cash_flow (date, total, sale_id, type) VALUES (?,?,?,?);";
    
    private static final String updateSale =
    		"UPDATE sale SET client=?, date=?, album_id=? WHERE id = ?;";
    private static final String updateLicense =
    		"UPDATE license SET price=?, period=?, paid=? WHERE sale_id = ?;";
    private static final String updateRecord =
    		"UPDATE record SET amount=? WHERE sale_id = ?;";
    private static final String updateRevenue =
    		"UPDATE cash_flow SET date=?, total=?, sale_id=?, type=? WHERE id = ?;";
    
    private static final String removeSale =
    		"DELETE FROM sale WHERE id = ?;";

}
