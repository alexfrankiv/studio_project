package launch;

import app.Application;

import java.sql.SQLException;

public class Studio {

    public static void main(String[] args) throws SQLException {

        javax.swing.SwingUtilities.invokeLater(Application.self.mainController::createAndShowGUI);


//        Application app = new Application();
//
//        for (Album a : app.albumService.all()) {
//            System.out.println("Manager: "+a.getManager());
//            System.out.println(a);
//        }
//        Album MENU_ALBUM = app.albumService.getBy(1);
//        System.out.println(MENU_ALBUM);

//        try {
//            Application.self.albumPriceRepository.save(10.0, MENU_ALBUM);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Album al = new Album();
//        al.setName("Schock");
//        al.setRecordDate(new Date(Calendar.getInstance().getTimeInMillis()));
//        al.setFeeShare(0.5);
//        al.setManagerFeeShare(0.2);
//        al.setManager(Application.self.musicianService.getBy(1));
//        Application.self.albumService.insert(al);
    }
}
