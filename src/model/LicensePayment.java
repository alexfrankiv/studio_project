package model;

import java.math.BigDecimal;
import java.sql.Date;

public class LicensePayment extends Flow {
	//private License license;
	private int month;
	private int year;
	
	public LicensePayment(int id, Date date, BigDecimal total, int sale, int month, int year) {
		super(id, date, total, sale, FlowType.LICENSE_PAYMENT);
		//this.license = license;
		this.month = month;
		this.year = year;
	}
	
	public LicensePayment(Date date, BigDecimal total, int sale, int month, int year) {
		super(date, total, sale, FlowType.LICENSE_PAYMENT);
		//this.license = license;
		this.month = month;
		this.year = year;
	}
/*
	public License getLicense() {
		return license;
	}
	
	public void setLicense(License license) {
		this.license = license;
	}
	*/
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
}
