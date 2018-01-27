package repository.impl;

import app.Constants;
import app.DBConnector;
import model.Album;
import repository.IAlbumPriceRepository;

import java.sql.*;
import java.util.Calendar;

public class AlbumPriceRepository implements IAlbumPriceRepository {

    //TODO: implement
    @Override
    public Double getLastPriceFor(Album album) {
        return null;
    }

    // TODO: to be implemented soon
    @Override
    public Double getPriceFor(Album album, Date date) {
        return null;
    }

    @Override
    public boolean save(Double price, Album album) throws Exception {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement statement = c.prepareStatement(saveQ);
        statement.setLong(1, album.getId());
        statement.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
        statement.setDouble(3,price);
        int code = statement.executeUpdate();
        return code == Constants.DB_SUCCESS_EXECUTION_CODE;
    }

    // MARK: SQL queries
    private static final String getLastQ = "SELECT price FROM album_price WHERE album_id=?;";
    private static final String saveQ = "INSERT INTO album_price (album_id, date, price) VALUES (?, ?, ?);";
}
