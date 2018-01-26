package repository;

import model.Album;

import java.sql.Date;

public interface IAlbumPriceRepository {

    Double getLastPriceFor(Album album);
    Double getPriceFor(Album album, Date date);
    boolean save(Double price, Album album);
}
