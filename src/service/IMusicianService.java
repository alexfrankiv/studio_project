package service;

import model.Album;
import model.Musician;

import java.util.List;

public interface IMusicianService {

    List<Musician> all();
    Musician getBy(long id);
    Musician getBy(Album album);
    boolean insert(Musician musician);
    boolean update(Musician musician);
}
