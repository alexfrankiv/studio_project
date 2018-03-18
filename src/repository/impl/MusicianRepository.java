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
        PreparedStatement ps = c.prepareStatement(getByAlbumQ);
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
    private static final String allQ = "SELECT * FROM musician as gen inner join (SELECT id, (SELECT (SELECT COUNT(*) FROM sale LEFT JOIN album ON sale.album_id = album.id WHERE manager_id=musician.id) / (SELECT COUNT(*) FROM sale)) AS rate FROM musician) as rat on gen.id=rat.id;";
    private static final String getQ = "SELECT * FROM musician as gen inner join (SELECT id, (SELECT (SELECT COUNT(*) FROM sale LEFT JOIN album ON sale.album_id = album.id WHERE manager_id=musician.id) / (SELECT COUNT(*) FROM sale)) AS rate FROM musician) as rat on gen.id=rat.id WHERE gen.id=?;";
    private static final String getByAlbumQ = "SELECT * FROM musician as gen inner join (SELECT id, (SELECT (SELECT COUNT(*) FROM sale LEFT JOIN album ON sale.album_id = album.id WHERE manager_id=musician.id) / (SELECT COUNT(*) FROM sale)) AS rate FROM musician) as rat on gen.id=rat.id WHERE gen.id IN (SELECT manager_id FROM album WHERE id=?);";

    // MARK: mapping
    private Musician musicianFrom(ResultSet resultSet) throws SQLException {
        Musician musician = new Musician();
        musician.setId(resultSet.getLong("id"));
        musician.setName(resultSet.getString("name"));
        musician.setLastName(resultSet.getString("last_name"));
        musician.setPhone(resultSet.getString("phone"));
//        try {
            musician.setRating(resultSet.getDouble("rate"));
//        } catch (Exception e) {/*intentionally nothing*/}
        return musician;
    }

    private List<Musician> listMusiciansFrom(ResultSet resultSet) throws SQLException {
        List<Musician> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(musicianFrom(resultSet));
        }
        return list;
    }
}
