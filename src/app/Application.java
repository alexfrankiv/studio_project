package app;

import repository.*;
import repository.impl.*;
import service.IAlbumService;
import service.IMusicianService;
import service.impl.AlbumService;
import service.impl.MusicianService;
import service.impl.SaleService;
import view_controller.AlbumDetailsController;
import view_controller.AlbumViewController;
import view_controller.MainController;

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

    // MARK: services
    public final IMusicianService musicianService = new MusicianService();
    public final IAlbumService albumService = new AlbumService();
    public final SaleService saleService = new SaleService(); // !!! make an interface !!!

    // MARK: controllers
    public final MainController mainController = new MainController();
    public AlbumViewController albumViewController;
    public AlbumDetailsController newAlbumViewController;

    // MARK: general behaviour
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
