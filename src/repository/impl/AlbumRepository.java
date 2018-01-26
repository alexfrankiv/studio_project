package repository.impl;

import app.DBConnector;
import model.Album;
import repository.IAlbumRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean insert(Album album) {
//        Connection
        return false;
    }

    @Override
    public boolean update(Album album) {
        return false;
    }

//    @Override
//    public boolean delete(Album album) {
//        return false;
//    }

    //MARK: SQL queries
    private static final String allQ = "SELECT * FROM album;";
    private static final String getQ = "SELECT * FROM album WHERE id=?;";

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