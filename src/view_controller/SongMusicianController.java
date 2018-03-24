package view_controller;

import app.Application;
import app.Strings;
import model.Musician;
import model.dto.MusicianSong;
import model.Song;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 22.02.18.
 */
public class SongMusicianController extends JFrame {
    private JPanel contentView;
    private JList musicianList;
    private JLabel feeLabel;
    private JButton changeShare;
    private JPanel shareInfoPanel;
    private Song currentSong;
    private JButton buttonCancel;
    private List<Musician> dataSource;
    private Musician currentMusician;
    private long song_id;


    public SongMusicianController(long id){
        System.out.println("Id:"+id);
        this.song_id=id;

    setup();
    }

    public SongMusicianController(){}

    private void setup(){
        setContentPane(contentView);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentView.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void setupComponents(){
        reloadListData();
        musicianList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        musicianList.addListSelectionListener(e -> didSelectListItem(e));
        musicianList.setSelectedIndex(0);
        changeShare.addActionListener(e -> changeShare(e));
    }

    private void changeShare(ActionEvent e){
        long mid = currentMusician.getId();
        long sid = this.song_id;
        double share = 0;
        boolean updated = false;
        JTextField feeShare = new JTextField();
        feeShare.setText(String.valueOf(currentMusician.getRating()));
        Object [] message = {
                " New share",feeShare
        };
        int option = JOptionPane.showConfirmDialog(null,message,  Strings.MENU_SONG_CHANGE_SHARE, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try{
                share = Double.parseDouble(feeShare.getText());

            }catch(NumberFormatException ex){
                Application.showMessage(Strings.DIALOG_NUMBER_FORMAT_ERROR);
            }
            MusicianSong ms = new MusicianSong(mid,sid,share);
            try {
                updated = Application.self.musicianSongRepository.update(ms);
                if(updated){
                    Application.showMessage(Strings.SHARE_CHANGED);
                    reloadListData();
                    repaintDetails();

                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }

        }

    }

    private void  reloadData(){
        try {
            System.out.println("2");


            dataSource = Application.self.songRepository.getSongMusicians(this.song_id);
            System.out.println("3");
        }
        catch(SQLException e){
            System.out.println("4");
            e.printStackTrace();
        }
    }
    private void reloadListData() {
        System.out.println("1");
        reloadData();

        DefaultListModel<Musician> dlm = new DefaultListModel<>();
        for (Musician s : dataSource) {
            dlm.addElement(s);
            System.out.println("added musicians" + s.toString());
        }
        musicianList.setModel(dlm);


    }
    private void onCancel(){dispose();}


    private void didSelectListItem(ListSelectionEvent e) {
        int index = musicianList.getSelectedIndex();
        if (index < 0) return;
        currentMusician = dataSource.get(index);

        repaintDetails();


    }



    private void repaintDetails(){
        feeLabel.setText(String.valueOf(currentMusician.getRating()));



    }


    public static void presentDialog(long id){
        SongMusicianController dialog = new SongMusicianController( id);
        doPresent(dialog);
    }
    private static void doPresent(SongMusicianController songMusicianController){
        songMusicianController.setupComponents();
        songMusicianController.pack();
        songMusicianController.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
