package service.impl;

import app.Application;
import app.Strings;
import model.Album;
import model.Musician;
import service.IAlbumService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlbumService implements IAlbumService {

    @Override
    public List<Album> all() {
        List<Album> res = null;
        try {
            res = Application.self.albumRepository.all();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Album getBy(long id) {
        Album res = null;
        try {
           res =  Application.self.albumRepository.getBy(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public Album getBy(String name) throws SQLException {
    	Album res = null;
        try {
           res =  Application.self.albumRepository.getBy(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
	}
    
    @Override
    public boolean insert(Album album) {
        boolean exitCode;
        try{
           exitCode = Application.self.albumRepository.insert(album);
        } catch (Exception e) {
            throw new IllegalStateException(Strings.DIALOG_ILLEGAL_PRICE_CHANGE_ERROR);
        }
        return exitCode;
    }

    @Override
    public boolean update(Album album) throws SQLException {
		return Application.self.albumRepository.update(album);
    }

//    @Override
//    public boolean delete(Album MENU_ALBUM) {
//        return Application.self.albumRepository.delete(MENU_ALBUM);
//    }
    
	public List<String> getNames() throws SQLException {
		List<Album> all = all();
		List<String> res = new ArrayList<String>();
		for (Album a : all)
			res.add(a.getName());
		return res;
	}
	
	public List<String> getNames(List<Album> albums) {
		List<String> res = new ArrayList<String>();
		for (Album a : albums) {
			res.add(a.getName());
		}
		return res;
	}
	
	public String[] getYears() throws SQLException {
		List<Album> all = all();
		Set<String> res = new HashSet<String>();
		for (Album a : all)
			res.add(a.getRecordYear() + "");
		String[] years = res.toArray(new String[res.size()]);
		return years;
	}
	
	public String getNameYear(Album a) throws SQLException {
		return a.getName() + " [" + a.getRecordYear() + ']';
	}
	
	public List<Album> getByArtist(int id) throws SQLException {
		List<Album> all = all();
		List<Album> res = new ArrayList<Album>();
		for (Album a : all)
			if (a.getManager().getId() == id) {
				res.add(a);
			}
		return res;
	}
	
	public List<Album> getByMusician(Musician musician) throws SQLException {
		List<Album> all = all();
		List<Album> res = new ArrayList<Album>();
		for (Album a : all)
			if (a.getManager().equals(musician)) {
				res.add(a);
			}
		return res;
	}
	
	public List<Album> getByYear(String year) throws SQLException {
		List<Album> all = all();
		List<Album> res = new ArrayList<Album>();
		for (Album a : all)
			if ((a.getRecordYear() + "").equals(year))
				res.add(a);
		return res;
	}
	
	public List<Album> getByMusicianAndYear(Musician musician, String year) throws SQLException {
		List<Album> all = all();
		List<Album> res = new ArrayList<Album>();
		for (Album a : all)
			if (a.getManager().equals(musician) && (a.getRecordYear() + "").equals(year))
				res.add(a);
		return res;
	}
	
}
