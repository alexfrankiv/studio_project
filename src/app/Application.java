package app;

import repository.IAlbumPriceRepository;
import repository.IAlbumRepository;
import repository.IMusicianRepository;
import repository.impl.AlbumPriceRepository;
import repository.impl.AlbumRepository;
import repository.impl.MusicianRepository;
import service.IAlbumService;
import service.IMusicianService;
import service.impl.AlbumService;
import service.impl.MusicianService;
import view_controller.AlbumViewController;
import view_controller.MainController;
import view_controller.NewAlbumViewController;

import javax.swing.*;

public class Application {

    public final static Application self = new Application();

    // something like @Autowired
    // MARK: repositories
    public final IMusicianRepository musicianRepository = new MusicianRepository();
    public final IAlbumPriceRepository albumPriceRepository = new AlbumPriceRepository();
    public final IAlbumRepository albumRepository = new AlbumRepository();

    // MARK: services
    public final IMusicianService musicianService = new MusicianService();
    public final IAlbumService albumService = new AlbumService();

    // MARK: controllers
    public final MainController mainController = new MainController();
    public AlbumViewController albumViewController;
    public NewAlbumViewController newAlbumViewController;

    // MARK: general behaviour
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
