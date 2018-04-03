package model;

import java.sql.Date;

public class Record extends Sale {
	private int qty;

	public Record(Date date, String client, long albumId, int qty) {
		super(date, client, albumId, SaleType.RECORD);
		this.qty = qty;
	}
	
	public Record(int id, Date date, String client, long albumId, int qty) {
		super(id, date, client, albumId, SaleType.RECORD);
		this.qty = qty;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
}
