package repository.impl;

import app.Constants;
import app.DBConnector;
import com.sun.org.apache.regexp.internal.RE;
import model.Musician;
import model.dto.MusicianSong;
import model.Song;
import repository.IMusicianSongRepository;
import service.IMusicianSongService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22.02.18.
 */
public class MusicianSongRepository implements IMusicianSongRepository {

    @Override
    public List<MusicianSong> getMusicians(long id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getMusicianShare);
        ps.setLong(1, id);
        List<MusicianSong> result = listMusicianSongFrom(ps.executeQuery());
        return result;




    }
    private List<MusicianSong> listSongsFrom(ResultSet resultSet) throws SQLException {
        List<MusicianSong> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(musicianSongFrom(resultSet));
        }
        return list;
    }

    @Override
    public boolean insert(MusicianSong musicianSong) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(insert);
        ps.setLong(1,musicianSong.getMusician_id());
        ps.setLong(2,musicianSong.getSong_id());
        ps.setDouble(3,musicianSong.getFee_share());
        int songCode = ps.executeUpdate();
        return songCode== Constants.DB_SUCCESS_EXECUTION_CODE;


    }

    @Override
    public double getFee(long mid, long sid) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(Fee);
        ps.setLong(1,mid);
        ps.setLong(2,sid);
        double fee = fee(ps.executeQuery());


        return fee;
    }

    @Override
    public boolean update(MusicianSong song) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(update);
        ps.setDouble(1, song.getFee_share());
        ps.setLong(2, song.getMusician_id());
        ps.setLong(3, song.getSong_id());


        int songCode = ps.executeUpdate();
        return songCode == Constants.DB_SUCCESS_EXECUTION_CODE;
    }

    private double fee(ResultSet resultSet) throws  SQLException{
        double res=0;
        while(resultSet.next()){
            res= resultSet.getDouble("fee_share");
        }
        return res;

    }

private final static String Fee = "SELECT fee_share FROM musician_song WHERE musician_id=? AND song_id =?;";
    private final static String getMusicianShare = "SELECT * FROM musician_song WHERE song_id =?;";
private final static String insert = "INSERT  INTO musician_song(musician_id,song_id,fee_share) VALUES(?,?,?);";
   private final  static String update =  "UPDATE musician_song SET fee_share=? WHERE musician_id=? AND song_id=?;";
    private List<MusicianSong> listMusicianSongFrom(ResultSet resultSet) throws SQLException {
        List<MusicianSong> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(musicianSongFrom(resultSet));
        }
        return list;

    }
    private MusicianSong musicianSongFrom(ResultSet resultSet) throws SQLException {
        MusicianSong musicianSong = new MusicianSong();
        musicianSong.setMusician_id(resultSet.getLong("musician_id"));
        musicianSong.setSong_id(resultSet.getLong("song_id"));
        musicianSong.setFee_share(resultSet.getDouble("fee_share"));

        return musicianSong;
    }

}