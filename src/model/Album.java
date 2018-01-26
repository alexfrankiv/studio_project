package model;

import app.Application;

import java.sql.Date;

public class Album {

    private long id;
    private String name;
    private Date recordDate;
    private double feeShare;
    private double managerFeeShare;

    private Double currentPrice;
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
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", recordDate=" + recordDate +
                ", feeShare=" + feeShare +
                ", managerFeeShare=" + managerFeeShare +
                ", currentPrice=" + currentPrice +
                ", rating=" + rating +
                ", manager=" + manager +
                '}';
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

    public Double getCurrentPrice() {
        if (currentPrice == null) {
            currentPrice = Application.self.albumPriceRepository.getLastPriceFor(this);
        }
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getRating() {
        if (rating == null) {
            // TODO: retrieve from DB
        }
        return rating;
    }

    public void setRating(Double rating) {

        this.rating = rating;
    }

    public Musician getManager() {
        if (manager == null) {
            // TODO: retrieve from DB
        }
        return manager;
    }

    public void setManager(Musician manager) {
        this.manager = manager;
    }
}
