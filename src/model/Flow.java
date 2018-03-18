package model;

import app.Application;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public abstract class Flow {
	protected int id;
	protected Date date;
	protected BigDecimal total;
	protected int saleId;
	protected Sale sale;
	protected FlowType type;
	
	protected Flow(int id, Date date, BigDecimal total, int saleId, FlowType type) {
		this.id = id;
		this.date = date;
		this.total = total;
		this.saleId = saleId;
		this.type = type;
	}
	
	protected Flow(Date date, BigDecimal total, int sale_id, FlowType type) {
		this.id = -1;
		this.date = date;
		this.total = total;
		this.saleId = sale_id;
		this.type = type;
	}

	public int getId() {
		return id;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public Sale getSale() {
		if (sale == null) {
			try {
				sale = Application.self.saleRepository.getById(saleId);
			} catch (SQLException e) {e.printStackTrace(); }
		}
		return sale;
	}

	public int getSaleId() {
		return saleId;
	}

	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}
	
	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public FlowType getType() {
		return type;
	}

	public void setType(FlowType type) {
		this.type = type;
	}

	public enum FlowType {
		LICENSE_PAYMENT(1),
		RECORD_COST(2),
		MUSICIAN_REVENUE(3);

		private int val;

		private FlowType(int val){
			this.val = val;
		}

		public int getValue() {
			return val;
		}

	}
}
