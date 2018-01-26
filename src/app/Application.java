package app;

import repository.IAlbumPriceRepository;
import repository.IAlbumRepository;
import repository.impl.AlbumPriceRepository;
import repository.impl.AlbumRepository;
import service.IAlbumService;
import service.impl.AlbumService;

public class Application {

    public static Application self = new Application();

    // something like @Autowired
    //repositories
    public IAlbumPriceRepository albumPriceRepository = new AlbumPriceRepository();
    public IAlbumRepository albumRepository = new AlbumRepository();

    //services
    public IAlbumService albumService = new AlbumService();


}
