package repository;

import model.Musician;
import model.dto.MusicianSong;
import model.Song;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 22.02.18.
 */
public interface IMusicianSongRepository {

    List<MusicianSong> getMusicians(long id) throws SQLException;
    boolean insert(MusicianSong musicianSong) throws SQLException;
    double getFee(long mid,long sid) throws  SQLException;
    boolean update(MusicianSong song) throws SQLException;

 }
