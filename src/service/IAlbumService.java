package service;

import model.Album;
import model.Musician;

import java.sql.SQLException;
import java.util.List;

public interface IAlbumService {

    List<Album> all();
    Album getBy(long id);
    Album getBy(String name) throws SQLException;
    boolean insert(Album album);
    boolean update(Album album) throws SQLException;
//    boolean delete(Album MENU_ALBUM);
    //boolean save(Album MENU_ALBUM);
	List<String> getNames() throws SQLException;
	List<String> getNames(List<Album> albums);
	String[] getYears() throws SQLException;
	String getNameYear(Album a) throws SQLException;
	List<Album> getByArtist(int id) throws SQLException;
	List<Album> getByMusician(Musician musician) throws SQLException;
	List<Album> getByYear(String year) throws SQLException;
	List<Album> getByMusicianAndYear(Musician musician, String year) throws SQLException;
}
