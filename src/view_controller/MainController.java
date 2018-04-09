package view_controller;

import app.Application;
import app.Strings;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainController {

    private enum ScreenCode {
        ALBUM, SONG, SALE, MUSICIAN
    }

    private JFrame frame;
    private JComponent currentWindow;
    private ScreenCode currentWindowCode;

    private JMenuItem editCurrentAlbum;
    private JMenuItem songMusicianShareMenu;
    private JMenuItem songEditCurrent;

    public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Studio");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //min sizes
        frame.setMinimumSize(new Dimension(600, 400));

        setMenuBar();

        Application.self.albumViewController = new AlbumViewController();
        Application.self.songViewController = new SongViewController();
        Application.self.salesViewController = new SalesViewController();
        Application.self.salesNewController = new SalesNewController();
        Application.self.salesLicenseController = new SalesLicenseController();
        Application.self.musicianViewController = new MusicianViewController();
//        frame.add(Application.self.albumViewController.getContentView());
        Application.self.salesViewController.setup();
        Application.self.salesNewController.setup();
        Application.self.salesLicenseController.setup();

        update();
    }

    private void update() {
        if (currentWindowCode == ScreenCode.ALBUM) {
            editCurrentAlbum.setEnabled(true);
        } else {
            editCurrentAlbum.setEnabled(false);
        }
        if (currentWindowCode == ScreenCode.SONG) {
            songEditCurrent.setEnabled(true);
            songMusicianShareMenu.setEnabled(true);
        } else {
            songEditCurrent.setEnabled(false);
            songMusicianShareMenu.setEnabled(false);
        }
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //album menu
        JMenu albumMenu = new JMenu(Strings.MENU_ALBUM);
        JMenuItem albumsViewItem = new JMenuItem(Strings.MENU_ALBUM_VIEW);
        albumsViewItem.addActionListener(e -> {
            if (currentWindow != null) {
                frame.remove(currentWindow);
            }
            currentWindow = Application.self.albumViewController.getContentView();
            frame.add(currentWindow);
            currentWindowCode = ScreenCode.ALBUM;
            update();
        });
        albumMenu.add(albumsViewItem);

        JMenuItem albumNewItem = new JMenuItem(Strings.MENU_ALBUM_NEW);
        albumNewItem.addActionListener(e -> AlbumDetailsController.presentDialog(true));
        albumMenu.add(albumNewItem);

        editCurrentAlbum = new JMenuItem(Strings.MENU_ALBUM_EDIT_CURRENT);
        editCurrentAlbum.addActionListener(e -> {
            AlbumDetailsController.presentDialog(false,
                Application.self.albumViewController.getCurrentId());
        });
        albumMenu.add(editCurrentAlbum);

        menuBar.add(albumMenu);

        //song menu
        JMenu songMenu = new JMenu(Strings.MENU_SONG);

        JMenuItem viewSongs = new JMenuItem(Strings.MENU_SONG_VIEW);
        viewSongs.addActionListener(e -> {
            if (currentWindow != null) {
                frame.remove(currentWindow);
            }
            currentWindow = Application.self.songViewController.getContentView();
            frame.add(currentWindow);
            currentWindowCode = ScreenCode.SONG;
            update();
        });
        songMenu.add(viewSongs);

        JMenuItem songNewItem = new JMenuItem(Strings.MENU_SONG_NEW);
        songNewItem.addActionListener(e -> {
            SongDetailsController.presentDialog(true);
            Application.self.salesNewController.setup();
        });
        songMenu.add(songNewItem);

        songEditCurrent = new JMenuItem(Strings.MENU_SONG_EDIT);
        songEditCurrent.addActionListener(e -> SongDetailsController.presentDialog(false,
                Application.self.songViewController.getCurrentSong()));
        songEditCurrent.setEnabled(false);
        songMenu.add(songEditCurrent);

        songMusicianShareMenu = new JMenuItem(Strings.MENU_MUSICIAN_SHARE);
        songMusicianShareMenu.addActionListener(e -> SongMusicianController.presentDialog(Application.self.songViewController.getCurrentSong()));
        songMenu.add(songMusicianShareMenu);
        songMusicianShareMenu.setEnabled(false);
        menuBar.add(songMenu);

        //sales menu
        JMenu salesMenu = new JMenu(Strings.MENU_SALES);

        JMenuItem salesViewItem = new JMenuItem(Strings.MENU_SALES_VIEW);
        salesViewItem.addActionListener(e -> {
            if (currentWindow != null) {
                frame.remove(currentWindow);
            }
            try {
                Application.self.salesViewController.refreshTable();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            currentWindow = Application.self.salesViewController.getContentView();
            frame.add(currentWindow);
            currentWindowCode = ScreenCode.SALE;
            update();
        });
        salesMenu.add(salesViewItem);

        JMenuItem salesNewItem = new JMenuItem(Strings.MENU_SALES_NEW);
        salesNewItem.addActionListener(e -> {
            if (currentWindow != null) {
                frame.remove(currentWindow);
            }
            currentWindow = Application.self.salesNewController.getContentView();
            frame.add(currentWindow);
            currentWindowCode = ScreenCode.SALE;
            update();
        });
        salesMenu.add(salesNewItem);

        JMenuItem salesLicenseMenu = new JMenuItem(Strings.MENU_SALES_LICENSES);
        salesLicenseMenu.addActionListener(e -> Application.self.salesLicenseController.display());
        salesMenu.add(salesLicenseMenu);

        menuBar.add(salesMenu);

        //musician menu
        JMenu musicianMenu = new JMenu(Strings.MENU_MUSICIAN);
        JMenuItem musicianViewItem = new JMenuItem(Strings.MENU_MUSICIAN_VIEW);
        musicianViewItem.addActionListener(e -> {
            if (currentWindow != null) {
                frame.remove(currentWindow);
            }
            currentWindow = Application.self.musicianViewController.getContentView();
            frame.add(currentWindow);
            currentWindowCode = ScreenCode.MUSICIAN;
            update();
        });
        musicianMenu.add(musicianViewItem);

        /*
        JMenuItem newMusician = new JMenuItem("New..."); //TODO
        newMusician.addActionListener(e -> Application.showMessage(Strings.UNDER_CONSTRUCTION));
        musicianMenu.add(newMusician);
        JMenuItem editMusician = new JMenuItem("Edit..."); //TODO
        editMusician.addActionListener(e -> Application.showMessage(Strings.UNDER_CONSTRUCTION));
        musicianMenu.add(editMusician);
        */
        menuBar.add(musicianMenu);


        //help menu
        JMenu helpMenu = new JMenu(Strings.MENU_HELP);
        JMenuItem aboutItem = new JMenuItem(Strings.MENU_HELP_ABOUT);
        aboutItem.addActionListener(e -> Application.showMessage(Strings.ABOUT_TEXT));
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        menuBar.setVisible(true);
        frame.setJMenuBar(menuBar);
    }
}
