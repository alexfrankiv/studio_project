package model.dto;

/**
 * Created by root on 22.02.18.
 */
public class MusicianSong {
    private long musician_id;
    private long song_id;
    private double fee_share;

    public MusicianSong(long musician_id, long song_id, double fee_share) {
        this.musician_id = musician_id;
        this.song_id = song_id;
        this.fee_share = fee_share;
    }

    public MusicianSong() {
    }

    public long getMusician_id() {
        return musician_id;
    }

    public void setMusician_id(long musician_id) {
        this.musician_id = musician_id;
    }

    public long getSong_id() {
        return song_id;
    }

    public void setSong_id(long song_id) {
        this.song_id = song_id;
    }

    public double getFee_share() {
        return fee_share;
    }

    public void setFee_share(double fee_share) {
        this.fee_share = fee_share;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicianSong that = (MusicianSong) o;

        if (musician_id != that.musician_id) return false;
        if (song_id != that.song_id) return false;
        return Double.compare(that.fee_share, fee_share) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (musician_id ^ (musician_id >>> 32));
        result = 31 * result + (int) (song_id ^ (song_id >>> 32));
        temp = Double.doubleToLongBits(fee_share);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.valueOf(musician_id);

    }
}
