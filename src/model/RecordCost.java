package model;

import java.math.BigDecimal;
import java.sql.Date;

public class RecordCost extends Flow {

	public RecordCost(int id, Date date, BigDecimal total, int sale) {
		super(id, date, total, sale, FlowType.RECORD_COST);
	}
	
	public RecordCost(Date date, BigDecimal total, int sale) {
		super(date, total, sale, FlowType.RECORD_COST);
	}

	public RecordCost(Record rec, int id, BigDecimal price) {
		super(rec.getDate(), price, id, FlowType.RECORD_COST);
	}

}
