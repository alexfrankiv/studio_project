package repository;

import model.Album;
import model.Musician;
import model.Song;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 18.02.18.
 */
public interface ISongRepository {
    List<Song> all() throws SQLException;
    Song getBy(long id) throws SQLException;
    boolean insert(Song song) throws Exception;
    boolean update(Song song) throws SQLException;
    List<Musician> getSongMusicians(long id) throws SQLException;

}
