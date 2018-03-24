package view_controller;

import app.Application;
import app.Strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {

    private JFrame frame;
    private JComponent currentWindow;

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
//        frame.add(Application.self.albumViewController.getContentView());
//
        update();
    }

    private void update() {
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
            update();
        });
        albumMenu.add(albumsViewItem);

        JMenuItem albumNewItem = new JMenuItem(Strings.MENU_ALBUM_NEW);
        albumNewItem.addActionListener(e -> AlbumDetailsController.presentDialog(true));
        albumMenu.add(albumNewItem);

        JMenuItem albumEditCurrent = new JMenuItem(Strings.MENU_ALBUM_EDIT_CURRENT);
        albumEditCurrent.addActionListener(e -> AlbumDetailsController.presentDialog(false,
                Application.self.albumViewController.getCurrentId()));
        albumMenu.add(albumEditCurrent);

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
            update();
        });
        songMenu.add(viewSongs);

        JMenuItem songNewItem = new JMenuItem(Strings.MENU_SONG_NEW);
        songNewItem.addActionListener(e -> SongDetailsController.presentDialog(true));
        songMenu.add(songNewItem);

        JMenuItem songEditCurrent = new JMenuItem(Strings.MENU_SONG_EDIT);
        songEditCurrent.addActionListener(e -> SongDetailsController.presentDialog(false,
                Application.self.songViewController.getCurrentId()));
        songMenu.add(songEditCurrent);

        JMenuItem songMusicianShareMenu = new JMenuItem(Strings.MENU_MUSICIAN_SHARE);
        songMusicianShareMenu.addActionListener(e -> SongMusicianController.presentDialog(Application.self.songViewController.getCurrentId()));
        songMenu.add(songMusicianShareMenu);

        menuBar.add(songMenu);

        //sales menu
        JMenu salesMenu = new JMenu(Strings.MENU_SALES);

        JMenuItem salesViewItem = new JMenuItem(Strings.MENU_SALES_VIEW);
        salesViewItem.addActionListener(e -> {
            if (currentWindow != null) {
                frame.remove(currentWindow);
            }
            currentWindow = Application.self.salesViewController.getContentView();
            frame.add(currentWindow);
            update();
        });
        salesMenu.add(salesViewItem);

        menuBar.add(salesMenu);

        //help menu
        JMenu helpMenu = new JMenu(Strings.MENU_HELP);
        JMenuItem docsItem = new JMenuItem(Strings.MENU_HELP_DOCS);
        docsItem.addActionListener(e -> System.out.println("DOCS STUB"));
        helpMenu.add(docsItem);
        helpMenu.addSeparator();
        JMenuItem aboutItem = new JMenuItem(Strings.MENU_HELP_ABOUT);
        aboutItem.addActionListener(e -> System.out.println("ABOUT STUB"));
        helpMenu.add(aboutItem);

        menuBar.add(helpMenu);

        menuBar.setVisible(true);
        frame.setJMenuBar(menuBar);
    }
}
