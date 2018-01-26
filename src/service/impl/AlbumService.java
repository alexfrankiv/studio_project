package service.impl;

import app.Application;
import model.Album;
import service.IAlbumService;

import java.sql.SQLException;
import java.util.List;

public class AlbumService implements IAlbumService {

    @Override
    public List<Album> all() {
        List<Album> res = null;
        try {
            res = Application.self.albumRepository.all();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Album getBy(long id) {
        Album res = null;
        try {
           res =  Application.self.albumRepository.getBy(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean insert(Album album) {
        return Application.self.albumRepository.insert(album);
    }

    @Override
    public boolean update(Album album) {
        Application.self.albumPriceRepository.save(album.getCurrentPrice(), album);
        return Application.self.albumRepository.update(album);
    }

//    @Override
//    public boolean delete(Album album) {
//        return Application.self.albumRepository.delete(album);
//    }
}
