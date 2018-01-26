package launch;

import app.Application;
import model.Album;

public class Launch {

    public static void main(String[] args) {
        Application app = new Application();

        for (Album a : app.albumService.all()) {
            System.out.println(a);
        }
        System.out.println(app.albumService.getBy(1));
    }
}
