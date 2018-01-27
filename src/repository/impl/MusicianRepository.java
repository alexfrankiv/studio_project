package repository.impl;

import app.DBConnector;
import model.Album;
import model.Musician;
import repository.IMusicianRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicianRepository implements IMusicianRepository {

    // MARK: behaviour
    @Override
    public List<Musician> all() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(allQ);
        ResultSet result = ps.executeQuery();
        return listMusiciansFrom(result);
    }

    @Override
    public Musician getBy(long id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getQ);
        ps.setLong(1, id);
        List<Musician> result = listMusiciansFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    @Override
    public Musician getBy(Album album) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getByAlbumAlbumQ);
        ps.setLong(1, album.getId());
        List<Musician> result = listMusiciansFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    // TODO: to be implemented soon
    @Override
    public boolean insert(Musician musician) {
        return false;
    }

    // TODO: to be implemented soon
    @Override
    public boolean update(Musician musician) {
        return false;
    }

    // MARK: SQL queries
    private static final String allQ = "SELECT * FROM musician;";
    private static final String getQ = "SELECT * FROM musician WHERE id=?;";
    private static final String getByAlbumAlbumQ = "SELECT * FROM musician WHERE id IN (SELECT manager_id FROM album WHERE id=?);";

    // MARK: mapping
    private Musician musicianFrom(ResultSet resultSet) throws SQLException {
        Musician album = new Musician();
        album.setId(resultSet.getLong("id"));
        album.setName(resultSet.getString("name"));
        album.setLastName(resultSet.getString("last_name"));
        album.setPhone(resultSet.getString("phone"));
        return album;
    }

    private List<Musician> listMusiciansFrom(ResultSet resultSet) throws SQLException {
        List<Musician> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(musicianFrom(resultSet));
        }
        return list;
    }
}
