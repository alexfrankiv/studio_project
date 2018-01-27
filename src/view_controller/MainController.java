package view_controller;

import app.Application;
import app.Strings;

import javax.swing.*;
import java.awt.*;

public class MainController {

    private JFrame frame;

    public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Studio");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //min sizes
        frame.setMinimumSize(new Dimension(600, 400));

        setMenuBar();

        Application.self.albumViewController = new AlbumViewController();
        frame.add(Application.self.albumViewController.getContentView());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //album menu
        JMenu albumMenu = new JMenu(Strings.MENU_ALBUM);
        JMenuItem albumNewItem = new JMenuItem(Strings.MENU_ALBUM_NEW);
        albumNewItem.addActionListener(e -> NewAlbumViewController.presentDialog());
        albumMenu.add(albumNewItem);

        menuBar.add(albumMenu);

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
