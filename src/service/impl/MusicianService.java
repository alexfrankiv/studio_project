package service.impl;

import app.Application;
import model.Album;
import model.Musician;
import service.IMusicianService;

import java.sql.SQLException;
import java.util.List;

public class MusicianService implements IMusicianService {

    @Override
    public List<Musician> all() {
        List<Musician> res = null;
        try {
            res = Application.self.musicianRepository.all();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;    }

    @Override
    public Musician getBy(long id) {
        Musician res = null;
        try {
            res =  Application.self.musicianRepository.getBy(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Musician getBy(Album album) {
        Musician res = null;
        try {
            res =  Application.self.musicianRepository.getBy(album);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean insert(Musician musician) {
        return Application.self.musicianRepository.insert(musician);
    }

    @Override
    public boolean update(Musician musician) {
        return Application.self.musicianRepository.update(musician);
    }
}
