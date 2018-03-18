package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Revenue extends Flow {
	private int musicianID;
	//private BigDecimal percent;

	public Revenue(int id, Date date, BigDecimal total, int sale, int musicianID) {
		super(id, date, total, sale, FlowType.MUSICIAN_REVENUE);
		this.musicianID = musicianID;
	}
	
	public Revenue(Date date, BigDecimal total, int sale, int musicianID) {
		super(date, total, sale, FlowType.MUSICIAN_REVENUE);
		this.musicianID = musicianID;
		//this.percent = percent;
	}

	public int getMusicianID() {
		return musicianID;
	}

	public void setMusicianID(int musicianID) {
		this.musicianID = musicianID;
	}

}
