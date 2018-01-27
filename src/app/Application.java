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

public class Application {

    public final static Application self = new Application();

    // something like @Autowired
    //repositories
    public IMusicianRepository musicianRepository = new MusicianRepository();
    public IAlbumPriceRepository albumPriceRepository = new AlbumPriceRepository();
    public IAlbumRepository albumRepository = new AlbumRepository();

    //services
    public IMusicianService musicianService = new MusicianService();
    public IAlbumService albumService = new AlbumService();


}
