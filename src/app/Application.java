package app;

import repository.*;
import repository.impl.*;
import service.*;
import service.impl.*;
import view_controller.*;
import view_controller.SalesViewController;

import javax.swing.*;

public class Application {

    public final static Application self = new Application();

    // something like @Autowired
    // MARK: repositories
    public final IMusicianRepository musicianRepository = new MusicianRepository();
    public final IAlbumPriceRepository albumPriceRepository = new AlbumPriceRepository();
    public final IAlbumRepository albumRepository = new AlbumRepository();
    public final ISaleRepository saleRepository = new SaleRepository();
    public final IFlowRepository flowRepository = new FlowRepository();
    public final ISongRepository songRepository = new SongRepository();
    public final IMusicianSongRepository musicianSongRepository = new MusicianSongRepository();

    // MARK: services
    public final IMusicianService musicianService = new MusicianService();
    public final IAlbumService albumService = new AlbumService();
    public final ISaleService saleService = new SaleService();
    public final ISongService songService = new SongService();
    public final IMusicianSongService musicianSongService = new MusicianSongService();

    // MARK: controllers
    public final MainController mainController = new MainController();
    public AlbumViewController albumViewController;
    public AlbumDetailsController newAlbumViewController;
    public SongMusicianController songMusicianController;
    public SongViewController songViewController;
    public SalesViewController salesViewController;
    public SalesNewController salesNewController;
    public SalesLicenseController salesLicenseController;
    public MusicianViewController musicianViewController;

    ///////////////////////////////////////

    ///////////////////////////////////////


    // MARK: general behaviour
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
