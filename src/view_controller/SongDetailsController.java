package view_controller;

import app.Application;
import app.Strings;
import model.Album;
import model.Musician;
import model.dto.MusicianSong;
import model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Created by root on 21.02.18.
 */
public class SongDetailsController  extends JDialog{
    private boolean createNew;

    private JPanel contentPane;

    private JLabel windowLabel;

    private JTextField nameInput;



    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox albumBox;


    private List<Album> albums;

    private JTextField authorInput;

    private Song currentSong;

    public SongDetailsController(Song song ){
        this.createNew = false;
        this.currentSong = song;
        setup();
    }

    public SongDetailsController(){
        this.createNew = true;
        setup();
    }
    
    private void setup(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void setupComponents() {

        albums = Application.self.albumService.all();

        DefaultComboBoxModel<Album> dla = new DefaultComboBoxModel<>();
        for (Album a : albums){
            dla.addElement(a);
        }
        albumBox.setModel(dla);

        windowLabel.setText((this.createNew) ? Strings.DIALOG_NEW_SONG_TITLE : Strings.DIALOG_EDIT_SONG_TITLE);

        if (!this.createNew) {
            if(currentSong == null){
                Application.showMessage(Strings.DIALOG_CHOOSE_SONG);
            }



            nameInput.setText(currentSong.getName());
            String author = currentSong.getAuthor();
            authorInput.setText(author);

           // albumInput.setText(String.valueOf(currentSong.getAlbum_id()));

        }
    }


    private void onOK() {
        Song song = (createNew) ? new Song() : currentSong;
        if (nameInput.getText().isEmpty()|| nameInput.getText()==null){
            Application.showMessage(Strings.DIALOG_EMPTY_NAME_ERROR);
            return;
        } else {
            song.setName(nameInput.getText());
        }
        if(authorInput.getText().isEmpty() || authorInput.getText() == null){
            Application.showMessage(Strings.DIALOG_EMPTY_AUTHOR_ERROR);
            return ;
        } else {
            song.setAuthor(authorInput.getText());
        }

        song.setAlbum_id(((Album)albumBox.getSelectedItem()).getId());
        if(song.getAlbum_id() < 0) {
            Application.showMessage(Strings.DIALOG_WRONG_ALBUM_ERROR);
            return;
        }



        try {
            if (createNew) {
                Application.self.songService.insert(song);
                Application.showMessage(Strings.INSERTED_SONG);

            } else {
                Application.self.songService.update(song);
                Application.showMessage(Strings.CHANGED_SONG);

            }
        }catch(Exception e){
            e.printStackTrace();
        }







        dispose();
        Application.self.songViewController.refresh();
    }

    private void onCancel() {
        dispose();
    }

    public static void presentDialog(boolean createNew) {
        SongDetailsController dialog = new SongDetailsController();
        doPresent(dialog);
    }

    public static void presentDialog(boolean createNew, Song song) {
        SongDetailsController dialog = new SongDetailsController(song);
        doPresent(dialog);
    }

    private  static void doPresent(SongDetailsController dialog) {
        dialog.setupComponents();
        dialog.pack();
        dialog.setVisible(true);
    }









}
