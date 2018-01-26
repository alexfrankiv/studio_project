package repository.impl;

import model.Album;
import repository.IAlbumPriceRepository;

import java.sql.Date;

public class AlbumPriceRepository implements IAlbumPriceRepository {

    //TODO: implement
    @Override
    public Double getLastPriceFor(Album album) {
        return null;
    }

    @Override
    public Double getPriceFor(Album album, Date date) {
        return null;
    }

    @Override
    public boolean save(Double price, Album album) {
        return false;
    }
}
