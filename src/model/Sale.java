package model;

import app.Application;

import java.sql.Date;
import java.sql.SQLException;

public abstract class Sale {
	protected int id;
	protected Date date;
	protected String client;
	protected int albumId;
	protected Album album;
	protected SaleType type;
	
	protected Sale(Date date, String client, int albumId, SaleType type) {
		this.id = -1;
		this.date = date;
		this.client = client;
		this.albumId = albumId;
		this.type = type;
	}
	
	protected Sale(int id, Date date, String client, int albumId, SaleType type) {
		this.id = id;
		this.date = date;
		this.client = client;
		this.albumId = albumId;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public Album getAlbum() {
		if (album == null) {
			try {
				album = Application.self.albumRepository.getBy(albumId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public SaleType getType() {
		return type;
	}

	public void setType(SaleType type) {
		this.type = type;
	}
	
	public String toString() {
		return date.toString() + " : " + type.name() + " : album " + albumId + " - " + client;
	}

	public enum SaleType {
		LICENSE(1),
		RECORD(2);

		private int val;

		private SaleType(int val){
			this.val = val;
		}

		public int getValue() {
			return val;
		}

	}
}
