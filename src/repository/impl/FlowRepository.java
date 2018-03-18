package repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.DBConnector;
import model.Flow;
import model.LicensePayment;
import model.RecordCost;
import model.Revenue;
import model.dto.PreparedRevenue;
import repository.IFlowRepository;

public class FlowRepository implements IFlowRepository {

	@Override
	public boolean add(Flow flow) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = null;
        ResultSet rs;
		switch (flow.getType()) {
		case LICENSE_PAYMENT:
			s = c.prepareStatement(addLPayment);
			//date, total, sale_id, year, month, type
			s.setDate(1,flow.getDate());
			s.setBigDecimal(2, flow.getTotal());
			s.setInt(3, flow.getSaleId());
			s.setInt(4, ((LicensePayment)flow).getYear());
			s.setInt(5, ((LicensePayment)flow).getMonth());
			s.setInt(6, (Flow.FlowType.LICENSE_PAYMENT).getValue());
			break;
		case MUSICIAN_REVENUE:
			s = c.prepareStatement(addMRevenue);
			//date, total, sale_id, musician_id, type
			s.setDate(1,flow.getDate());
			s.setBigDecimal(2, flow.getTotal());
			s.setInt(3, flow.getSaleId());
			s.setInt(4, ((Revenue)flow).getMusicianID());
			s.setInt(5, (Flow.FlowType.MUSICIAN_REVENUE).getValue());
			break;
		case RECORD_COST:
			s = c.prepareStatement(addRCost);
			//date, total, sale_id, type
			s.setDate(1,flow.getDate());
			s.setBigDecimal(2, flow.getTotal());
			s.setInt(3, flow.getSaleId());
			s.setInt(4, (Flow.FlowType.RECORD_COST).getValue());
			break;
		}
		int success = s.executeUpdate();
		return (success == 1);
	}
	
	@Override
	public boolean addRevenue(PreparedRevenue pr) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement(addMRevenue);
        //date, total, sale_id, type
        BigDecimal total = pr.getRevenue().getTotal().multiply(pr.getPercent());
		s.setDate(1, pr.getRevenue().getDate());
		s.setBigDecimal(2, total);
		s.setInt(3, pr.getRevenue().getSaleId());
		s.setInt(4, pr.getRevenue().getMusicianID());
		s.setInt(5, (Flow.FlowType.MUSICIAN_REVENUE).getValue());
		int success = s.executeUpdate();
		return (success == 1);
	}

	@Override
	public boolean remove(Flow flow) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
		PreparedStatement s = c.prepareStatement(removeFlow);
		s.setInt(1, flow.getId());
		int success = s.executeUpdate();
        return (success == 1);		
	}

	@Override
	public boolean update(Flow flow) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = null;
        ResultSet rs;
		switch (flow.getType()) {
		case LICENSE_PAYMENT:
			s = c.prepareStatement(
					"UPDATE cash_flow SET date=?, total=?, sale_id=?, year=?, month=?, type=? WHERE id = ?;");
			//date, total, sale_id, year, month, type
			s.setDate(1,flow.getDate());
			s.setBigDecimal(2, flow.getTotal());
			s.setInt(3, flow.getSaleId());
			s.setInt(4, ((LicensePayment)flow).getYear());
			s.setInt(5, ((LicensePayment)flow).getMonth());
			s.setInt(6, (Flow.FlowType.LICENSE_PAYMENT).getValue());
			s.setInt(7,flow.getId());
			break;
		case MUSICIAN_REVENUE:
			s = c.prepareStatement(
					"UPDATE cash_flow SET date=?, total=?, sale_id=?, musician_id=?, type=? WHERE id = ?;"); //musician_id=?, 
			//date, total, sale_id, musician_id, type
			s.setDate(1,flow.getDate());
			s.setBigDecimal(2, flow.getTotal());
			s.setInt(3, flow.getSaleId());
			s.setInt(4, ((Revenue)flow).getMusicianID());
			s.setInt(5, (Flow.FlowType.MUSICIAN_REVENUE).getValue());
			s.setInt(6,flow.getId());
			break;
		case RECORD_COST:
			s = c.prepareStatement(
					"UPDATE cash_flow SET date=?, total=?, sale_id=?, type=? WHERE id = ?;");
			//date, total, sale_id, type
			s.setDate(1,flow.getDate());
			s.setBigDecimal(2, flow.getTotal());
			s.setInt(3, flow.getSaleId());
			s.setInt(4, (Flow.FlowType.RECORD_COST).getValue());
			s.setInt(5,flow.getId());
			break;
		}
		int success = s.executeUpdate();
		return (success == 1);
	}

	@Override
	public List<Flow> getAll() throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement("SELECT * FROM cash_flow;");
        ResultSet rs = s.executeQuery();
        return toList(rs);
	}
	
	@Override
	public BigDecimal getSumBySaleId(int saleId) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement("SELECT IFNULL(Sum(total),0) AS sum FROM cash_flow WHERE sale_id=?;");
        s.setInt(1, saleId);
        ResultSet rs = s.executeQuery();
        rs.next();
        return rs.getBigDecimal("sum");
	}

	@Override
	public List<Flow> getAllBySaleId(int saleId) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement("SELECT * FROM cash_flow WHERE sale_id=?;");
        s.setInt(1, saleId);
        ResultSet rs = s.executeQuery();
        return toList(rs);
	}

	@Override
	public Flow getById(int id) throws SQLException {
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement("SELECT * FROM cash_flow WHERE id=?;");
        s.setInt(1, id);
        ResultSet rs = s.executeQuery();
        rs.next();
        return toObject(rs);
	}
	
	private Flow toObject(ResultSet rs) throws SQLException {
		if (rs.getInt("type") == (Flow.FlowType.RECORD_COST).getValue())
        	return new RecordCost(rs.getInt("id"), rs.getDate("date"),
        			rs.getBigDecimal("total"), rs.getInt("sale_id"));
        else if (rs.getInt("type") == (Flow.FlowType.LICENSE_PAYMENT).getValue())
        	return new LicensePayment(rs.getInt("id"), rs.getDate("date"),
        			rs.getBigDecimal("total"), rs.getInt("sale_id"), rs.getInt("month"), rs.getInt("year"));
		return new Revenue(rs.getInt("id"), rs.getDate("date"),
    			rs.getBigDecimal("total"), rs.getInt("sale_id"),
    			rs.getInt("musician_id"));
    }
	
	public List<PreparedRevenue> getAlbumRevenues(Flow flow, int album_id) throws SQLException {
		List<PreparedRevenue> list = new ArrayList<>();
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement("SELECT * FROM musician_album WHERE album_id=?;");
        s.setInt(1, album_id);
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
        	Revenue rev = new Revenue(flow.getDate(), flow.getTotal(), flow.getSaleId(),
        			rs.getInt("musician_id"));
        	list.add(new PreparedRevenue(rev, rs.getBigDecimal("per_album")));
        }
        return list;
	}
	
    private List<Flow> toList(ResultSet rs) throws SQLException {
        List<Flow> list = new ArrayList<Flow>();
        while (rs.next()) list.add(toObject(rs));
        return list;
    }
	
	//QUERIES
    private static final String getPercents =
    		"SELECT * FROM studio.musician_album  WHERE album_id=?;";
	private static final String removeFlow =
    		"DELETE FROM cash_flow WHERE id = ?;";
	private static final String addLPayment =
			"INSERT INTO cash_flow (date, total, sale_id, year, month, type) VALUES (?,?,?,?,?,?);";
	private static final String addRCost =
			"INSERT INTO cash_flow (date, total, sale_id, type) VALUES (?,?,?,?);";
	private static final String addMRevenue =
			"INSERT INTO cash_flow (date, total, sale_id, musician_id, type) VALUES (?,?,?,?,?);";
	@Override
	public BigDecimal paidAMonth(int saleId, int month) throws SQLException {
		List<PreparedRevenue> list = new ArrayList<>();
		Connection c = DBConnector.shared.getConnect();
        PreparedStatement s = c.prepareStatement("SELECT sale_id, Sum(total) AS month_total FROM studio.cash_flow WHERE type=1 AND sale_id=? AND month=? GROUP BY sale_id;");
        s.setInt(1, saleId);
        s.setInt(2, month);
        ResultSet rs = s.executeQuery();
		
		return null;
	}
	
}
