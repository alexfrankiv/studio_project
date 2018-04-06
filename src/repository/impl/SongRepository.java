package repository.impl;

import app.Application;
import app.Constants;
import app.DBConnector;
import model.Album;
import model.Musician;
import model.Song;
import repository.ISongRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 18.02.18.
 */
public class SongRepository implements ISongRepository {

    @Override
    public List<Song> all() throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        //System.out.println("Connected");


        PreparedStatement ps = c.prepareStatement(getAll);
        ResultSet result = ps.executeQuery();

        //System.out.println("Executed query");
        return listSongsFrom(result);
    }

    @Override
    public Song getBy(long id) throws SQLException {
        Connection c =  DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getById);
        ps.setLong(1,id);
        List<Song> result = listSongsFrom(ps.executeQuery());
        return (result.isEmpty()) ? null : result.get(0);
    }

    @Override
    public boolean insert(Song song) throws Exception {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(insert);
        ps.setString(1, song.getAuthor());
        ps.setString(2, song.getName());
        ps.setLong(3, song.getAlbum_id());
        int songCode = ps.executeUpdate();
        return songCode== Constants.DB_SUCCESS_EXECUTION_CODE;



    }

    @Override
    public boolean update(Song song) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(update);
        ps.setString(1,song.getAuthor());
        ps.setString(2,song.getName());
        ps.setLong(3,song.getAlbum_id());
        ps.setLong(4,song.getId());

        int songCode = ps.executeUpdate();
        return songCode== Constants.DB_SUCCESS_EXECUTION_CODE;

    }


    // SQL queries
    private static final String  getAll = "SELECT * FROM song;";
    private static final  String getMusicians  = "SELECT *  FROM musician JOIN musician_song ON musician.id = musician_song.musician_id WHERE song_id=?;";
    private static final String insert = "INSERT INTO song(author,name,album_id) VALUES(?,?,?);";
private static final String getById = "SELECT * FROM song WHERE id=?;";
    private static final String update="UPDATE song  SET author=?,name=?,album_id=? WHERE id=?; ";
    private List<Song> listSongsFrom(ResultSet resultSet) throws SQLException {
        List<Song> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(songFrom(resultSet));
        }
        return list;
    }

    public List<Musician> getSongMusicians(long id) throws SQLException {
        Connection c = DBConnector.shared.getConnect();
        PreparedStatement ps = c.prepareStatement(getMusicians);
        ps.setLong(1,id);
        List<Musician> musList = new ArrayList<>();
        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()){
            musList.add(musicianFrom(resultSet));
        }
        return musList;




    }
    private Musician musicianFrom(ResultSet resultSet) throws SQLException {
        Musician musician = new Musician();
        musician.setId(resultSet.getLong("id"));
        musician.setName(resultSet.getString("name"));
        musician.setLastName(resultSet.getString("last_name"));
        musician.setPhone(resultSet.getString("phone"));
//        try {
        musician.setRating(resultSet.getDouble("fee_share"));
//        } catch (Exception e) {/*intentionally nothing*/}
        return musician;
    }

    private Song songFrom(ResultSet resultSet) throws SQLException {
        Song song = new Song();
        song.setId(resultSet.getLong("id"));
        song.setAuthor(resultSet.getString("author"));
        song.setName(resultSet.getString("name"));
        song.setAlbum_id(resultSet.getLong("album_id"));
        //System.out.println("Create song");


        return song;
    }

}
