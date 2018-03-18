package repository.impl;

import app.Constants;
import app.DBConnector;
import model.Album;
import repository.IAlbumPriceRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;

public class AlbumPriceRepository implements IAlbumPriceRepository {

    @Override
    public BigDecimal getLastPriceFor(Album album) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getLastQ);
        ps.setLong(1, album.getId());
        ResultSet resultSet = ps.executeQuery();
        return (resultSet.next()) ? resultSet.getBigDecimal("price") : null;
    }

    // TODO: to be implemented soon
    @Override
    public BigDecimal getPriceFor(Album album, Date date) {
        return null;
    }

    @Override
    public boolean save(BigDecimal price, Album album) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement statement = c.prepareStatement(saveQ);
        statement.setLong(1, album.getId());
        statement.setDate(2, new Date(Calendar.getInstance().getTimeInMillis()));
        statement.setBigDecimal(3,price);
        int code = statement.executeUpdate();
        return code == Constants.DB_SUCCESS_EXECUTION_CODE;
    }

    // MARK: SQL queries
    private static final String getLastQ = "SELECT price FROM album_price WHERE album_id=? ORDER BY date DESC;";
    private static final String saveQ = "INSERT INTO album_price (album_id, date, price) VALUES (?, ?, ?);";
}
