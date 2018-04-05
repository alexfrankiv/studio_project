package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

public class License extends Sale {
	private BigDecimal price;
	private int period;
	private boolean paid;
	
	public License(Date date, String client, long albumId, BigDecimal price, int period) {
		super(date, client, albumId, SaleType.LICENSE);
		this.price = price;
		this.period = period;
	}
	
	public License(int id, Date date, String client, long albumId, BigDecimal price, int period, boolean paid) {
		super(id, date, client, albumId, SaleType.LICENSE);
		this.price = price;
		this.period = period;
		this.paid = paid;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
	public BigDecimal getSum() {
		return price.multiply(new BigDecimal(period));
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getExpiry() {
		Calendar c = Calendar.getInstance();
		c.setTime(this.getDate());
		c.add(Calendar.MONTH, getPeriod());
		java.util.Date d = c.getTime();
		return (c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR));
	}

	@Override
	public String toString() {
		return getId() + ": " + getAlbum().toString() + " - " + getClient() + " - " + getDate() + " +" + getPeriod() + " mo.";
	}
	
}
