package service;

import model.Album;

import java.sql.SQLException;
import java.util.List;

public interface IAlbumService {

    List<Album> all();
    Album getBy(long id);
    boolean insert(Album album);
    boolean update(Album album);
//    boolean delete(Album album);
    //boolean save(Album album);
}
