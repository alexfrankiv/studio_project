package service;

import model.Album;
import model.Musician;
import model.Song;

import java.sql.SQLException;
import java.util.List;


public interface ISongService {
    List<Song> all();
    Song getBy(long id);
    boolean insert(Song song);
    boolean update(Song song) throws SQLException;
    List<Musician> getSongMusicians(long id);

}