package model.dto;

import java.math.BigDecimal;

import model.Revenue;

public class PreparedRevenue {
	private Revenue revenue;
	private BigDecimal percent;
	
	public PreparedRevenue(Revenue revenue, BigDecimal percent) {
		super();
		this.revenue = revenue;
		this.percent = percent;
	}

	public Revenue getRevenue() {
		return revenue;
	}

	public void setRevenue(Revenue revenue) {
		this.revenue = revenue;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}
	
}
