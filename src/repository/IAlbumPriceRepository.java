package repository;

import model.Album;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public interface IAlbumPriceRepository {

    BigDecimal getLastPriceFor(Album album) throws SQLException;
    BigDecimal getPriceFor(Album album, Date date);
    boolean save(BigDecimal price, Album album) throws SQLException;
}
