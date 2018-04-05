package service;

import model.*;
import model.dto.PreparedRevenue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ISaleService {

    Sale getById(int id) throws SQLException;
    boolean updateSale(Sale sale) throws SQLException;
    boolean removeSale(Sale sale) throws SQLException;
    boolean removeSale(int id) throws SQLException;
    List<PreparedRevenue> newRecordSale(Record rec) throws SQLException;
    void newLicenseSale(License l) throws SQLException;
    List<PreparedRevenue> newLicensePayment(LicensePayment lp, boolean finalPayment) throws SQLException;
    void addLicenseRevenue(List<PreparedRevenue> pr) throws SQLException;
    String licenseRevenuesAsString(List<PreparedRevenue> prep);
    String getLicenseInfo(License l) throws SQLException;
    int getLicenseIdByInfo(String info);
    List<Sale> getLicenses() throws SQLException;
    List<String> getLicenseInfos() throws SQLException;
    List getData() throws SQLException;
    List getLicenseData(boolean hide) throws SQLException;
    BigDecimal getSaleRevenue(Sale sale) throws SQLException;
    List filterByArtist(String name, List<ArrayList<String>> in);
    List filterByAlbum(Album album, List<ArrayList<String>> in);
    List filterByType(String type, List<ArrayList<String>> in);
    List filterByStart(Date date, List<ArrayList<String>> in) throws ParseException;
    List filterByEnd(Date date, List<ArrayList<String>> in) throws ParseException;
    int[] getCurrentPayMonth(License license) throws SQLException;
    int getMonthsLeft(License license) throws SQLException;
    void resetRes();

}
