package repository.impl;

import app.Application;
import app.Constants;
import app.DBConnector;
import model.Album;
import repository.IAlbumRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlbumRepository implements IAlbumRepository {

    //MARK: behaviour
    @Override
    public List<Album> all() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(allQ);
        ResultSet result = ps.executeQuery();
        return listAlbumsFrom(result);
    }

    @Override
    public Album getBy(long id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getQ);
        ps.setLong(1, id);
        List<Album> result = listAlbumsFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    @Override
    public boolean insert(Album album) throws Exception {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement statement = c.prepareStatement(insertQ);
        statement.setString(1, album.getName());
        statement.setDate(2,album.getRecordDate());
        statement.setDouble(3, album.getFeeShare());
        statement.setDouble(4, album.getManagerFeeShare());
        statement.setLong(5, album.getManager().getId());
        int albumCode = statement.executeUpdate();
        boolean priceCode = true;
        if (album.getCurrentPrice() != null) {
           priceCode = Application.self.albumPriceRepository.save(album.getCurrentPrice(), album);
        }
        return albumCode == Constants.DB_SUCCESS_EXECUTION_CODE && priceCode;
    }

    @Override
    public boolean update(Album album) {
        boolean exitCode = false;
        try {
            exitCode = Application.self.albumPriceRepository.save(album.getCurrentPrice(), album);
        } catch (Exception e) {}
        return exitCode;
    }

//    @Override
//    public boolean delete(Album album) {
//        return false;
//    }

    //MARK: SQL queries
    private static final String allQ = "SELECT * FROM album;";
    private static final String getQ = "SELECT * FROM album WHERE id=?;";
    private static final String insertQ = "INSERT INTO album (name, record_date, fee_share, manager_fee_share, manager_id) VALUES (?,?,?,?,?);";

    //MARK: mapping
    private Album albumFrom(ResultSet resultSet) throws SQLException {
        Album album = new Album();
        album.setId(resultSet.getLong("id"));
        album.setName(resultSet.getString("name"));
        album.setRecordDate(resultSet.getDate("record_date"));
        album.setFeeShare(resultSet.getDouble("fee_share"));
        album.setManagerFeeShare(resultSet.getDouble("manager_fee_share"));
        return album;
    }

    private List<Album> listAlbumsFrom(ResultSet resultSet) throws SQLException {
        List<Album> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(albumFrom(resultSet));
        }
        return list;
    }
}
