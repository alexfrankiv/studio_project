package service.impl;

import model.Musician;
import model.Song;
import repository.impl.SongRepository;
import service.ISongService;

import service.IAlbumService;

import app.Application;
import app.Strings;
import model.Album;
import service.IAlbumService;

import java.sql.SQLException;
import java.util.List;

public class SongService implements ISongService {

    @Override
    public List<Song> all() {
        List<Song> res = null;
        SongRepository sr = new SongRepository();
        try {

            res = sr.all();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Song getBy(long id) {
        Song res = null;
        try {
            res = Application.self.songRepository.getBy(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean insert(Song song) {
        boolean exitCode;
        try {
            exitCode = Application.self.songRepository.insert(song);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("+++++++");
            throw new IllegalStateException(Strings.DIALOG_FAILED_INSERTIONS_SONG);
        }
        return exitCode;
    }

    @Override
    public boolean update(Song song) throws SQLException {
        boolean success = Application.self.songRepository.update(song);

        return success;
    }
    @Override
    public List<Musician> getSongMusicians(long id) {

        List<Musician>res = null;
        SongRepository sr = new SongRepository();
        try {

            res = sr.getSongMusicians(id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }
}