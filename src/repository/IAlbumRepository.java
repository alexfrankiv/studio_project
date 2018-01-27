package repository;

import model.Album;
import repository.impl.AlbumRepository;

import java.sql.SQLException;
import java.util.List;

public interface IAlbumRepository {

    List<Album> all() throws SQLException;
    Album getBy(long id) throws SQLException;
    boolean insert(Album album) throws Exception;
    boolean update(Album album);
//    boolean delete(Album MENU_ALBUM);
    //boolean save(Album MENU_ALBUM);
}
