package repository.impl;

import app.Application;
import app.Constants;
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
        ps.setLong(2, id);
        ps.setLong(3, id);
        List<Album> result = listAlbumsFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }
    
    @Override
	public Album getBy(String name) throws SQLException {
    	Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getByNameQ);
        ps.setString(1, name);
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
    public boolean update(Album album) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement statement = c.prepareStatement(updateQ);
        statement.setString(1, album.getName());
        statement.setDate(2,album.getRecordDate());
        statement.setDouble(3, album.getFeeShare());
        statement.setDouble(4, album.getManagerFeeShare());
        statement.setLong(5, album.getManager().getId());
        statement.setLong(6, album.getId());
        int albumCode = statement.executeUpdate();
        boolean priceCode = true;
        if (album.getCurrentPrice() != null && album.getCurrentPrice() != Application.self.albumPriceRepository.getLastPriceFor(album)) {
            priceCode = Application.self.albumPriceRepository.save(album.getCurrentPrice(), album);
        }
        return priceCode && albumCode == Constants.DB_SUCCESS_EXECUTION_CODE;
    }

//    @Override
//    public boolean delete(Album MENU_ALBUM) {
//        return false;
//    }

    //MARK: SQL queries
    private static final String allQ = "SELECT * FROM album as gen inner join (SELECT id, (SELECT (SELECT COUNT(*) FROM sale WHERE album_id = album.id) / (SELECT COUNT(*) FROM sale)) AS rate FROM album) as rat on gen.id=rat.id ORDER BY record_date DESC;";
    private static final String getQ = "SELECT * from album inner join (SELECT ? as album_id, ((SELECT COUNT(*) FROM sale WHERE album_id = ?) / (SELECT COUNT(*) FROM sale)) AS rate) as rat ON album.id = rat.album_id WHERE id=?;";
    private static final String getByNameQ = "SELECT * FROM album_full WHERE name=?;";
    private static final String insertQ = "INSERT INTO album (name, record_date, fee_share, manager_fee_share, manager_id) VALUES (?,?,?,?,?);";
    private static final String updateQ = "UPDATE album SET name=?, record_date=?, fee_share=?, manager_fee_share=?, manager_id=? WHERE id = ?;";

    //MARK: mapping
    private Album albumFrom(ResultSet resultSet) throws SQLException {
        Album album = new Album();
        album.setId(resultSet.getLong("id"));
        album.setName(resultSet.getString("name"));
        album.setRecordDate(resultSet.getDate("record_date"));
        album.setFeeShare(resultSet.getDouble("fee_share"));
        album.setManagerFeeShare(resultSet.getDouble("manager_fee_share"));
        try {
            album.setRating(resultSet.getDouble("rate"));
        } catch (Exception e) {/*intentionally nothing*/}
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
