package service.impl;

import app.Application;
import model.dto.MusicianSong;
import service.IMusicianSongService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 22.02.18.
 */
public class MusicianSongService implements IMusicianSongService {

    @Override
    public List<MusicianSong> getById(long id) throws SQLException {
        return Application.self.musicianSongRepository.getMusicians(id);

    }

    @Override
    public boolean insert(MusicianSong musicianSong) throws SQLException {
        return  Application.self.musicianSongRepository.insert(musicianSong);
    }

    @Override
    public double getFee(long mid, long sid) throws  SQLException{
        return Application.self.musicianSongRepository.getFee(mid,sid);
    }

    @Override
    public boolean update(MusicianSong song) throws SQLException {
        return Application.self.musicianSongRepository.update(song);
    }
}
