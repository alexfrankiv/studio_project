package service.impl;

import app.Application;
import app.Strings;
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
        boolean exitCode;
        try{
           exitCode = Application.self.albumRepository.insert(album);
        } catch (Exception e) {
            throw new IllegalStateException(Strings.DIALOG_ILLEGAL_PRICE_CHANGE_ERROR);
        }
        return exitCode;
    }

    @Override
    public boolean update(Album album) throws SQLException {
        boolean success = Application.self.albumRepository.update(album);
        try {
            Application.self.albumPriceRepository.save(album.getCurrentPrice(), album);
        } catch (Exception e) {
            throw new IllegalStateException(Strings.DIALOG_ILLEGAL_PRICE_CHANGE_ERROR);
        }
        return success;
    }

//    @Override
//    public boolean delete(Album MENU_ALBUM) {
//        return Application.self.albumRepository.delete(MENU_ALBUM);
//    }
}
