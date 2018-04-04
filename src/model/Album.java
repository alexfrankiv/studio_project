package model;

import app.Application;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Album {

    private long id;
    private String name;
    private Date recordDate;
    private double feeShare;
    private double managerFeeShare;

    private BigDecimal currentPrice;
    private Double rating;

    private Musician manager;

    public Album(long id, String name, Date recordDate, double feeShare, double managerFeeShare) {
        this.id = id;
        this.name = name;
        this.recordDate = recordDate;
        this.feeShare = feeShare;
        this.managerFeeShare = managerFeeShare;
    }

    public Album(){}

    @Override
    public String toString() {
        if (this == null) return "-";
        return name + " [" + new SimpleDateFormat("yyyy").format(recordDate) + ']';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
    
    public int getRecordYear( ) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(recordDate);
    	return c.get(Calendar.YEAR);
    }

    public double getFeeShare() {
        return feeShare;
    }

    public void setFeeShare(double feeShare) {
        this.feeShare = feeShare;
    }

    public double getManagerFeeShare() {
        return managerFeeShare;
    }

    public void setManagerFeeShare(double managerFeeShare) {
        this.managerFeeShare = managerFeeShare;
    }

    public BigDecimal getCurrentPrice() {
        if (currentPrice == null) {
            try {
                currentPrice = Application.self.albumPriceRepository.getLastPriceFor(this);
            } catch (SQLException e) {e.printStackTrace();}
        }
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {

        this.rating = rating;
    }

    public Musician getManager() {
        if (manager == null) {
            manager = Application.self.musicianService.getBy(this);
        }
        return manager;
    }

    public void setManager(Musician manager) {
        this.manager = manager;
    }
}
