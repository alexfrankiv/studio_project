package repository;

import model.Album;
import model.Musician;

import java.sql.SQLException;
import java.util.List;

public interface IMusicianRepository {

    List<Musician> all() throws SQLException;
    Musician getBy(long id) throws SQLException;
    Musician getBy(Album album) throws SQLException;
    Musician getBy(String name, String lastName) throws SQLException;
    boolean insert(Musician musician);
    boolean update(Musician musician);
}
