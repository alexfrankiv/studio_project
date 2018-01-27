package launch;

import app.Application;
import model.Album;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

public class Launch {

    public static void main(String[] args) throws SQLException {
        Application app = new Application();

        for (Album a : app.albumService.all()) {
            System.out.println("Manager: "+a.getManager());
            System.out.println(a);
        }
        Album album = app.albumService.getBy(1);
        System.out.println(album);

        try {
            Application.self.albumPriceRepository.save(10.0, album);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Album al = new Album();
        al.setName("Schock");
        al.setRecordDate(new Date(Calendar.getInstance().getTimeInMillis()));
        al.setFeeShare(0.5);
        al.setManagerFeeShare(0.2);
        al.setManager(Application.self.musicianService.getBy(1));
        Application.self.albumService.insert(al);
    }
}
