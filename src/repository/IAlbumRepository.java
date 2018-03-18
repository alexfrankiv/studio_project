package repository;

import model.Album;

import java.sql.SQLException;
import java.util.List;

public interface IAlbumRepository {

    List<Album> all() throws SQLException;
    Album getBy(long id) throws SQLException;
    Album getBy(String name) throws SQLException;
    boolean insert(Album album) throws Exception;
    boolean update(Album album) throws SQLException;
//    boolean delete(Album MENU_ALBUM);
    //boolean save(Album MENU_ALBUM);
	
}
