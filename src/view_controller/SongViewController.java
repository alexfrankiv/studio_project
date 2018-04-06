package view_controller;


import app.Application;
import app.Strings;
import model.Musician;
import model.dto.MusicianSong;
import model.Song;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongViewController {
    private List<Song> dataSource;


    private Song currentSong ;

    private JList<Song> songList;

    private JPanel contentView ;

    private JLabel songNameLabel ;

    private JLabel albumNameLabel ;

    private JLabel authorLabel ;

    private JLabel musicianNamesLabel;
    private JLabel musicianLastNameLabel ;

    private JButton addMusician;

    private JLabel musicianShareLabel ;
    private JPanel songInfoPanel;
    private List<Musician> musicians ;


    public SongViewController() {

        reloadListData();

        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        songList.addListSelectionListener(e -> didSelectListItem(e));
        songList.setSelectedIndex(0);
        addMusician.addActionListener(e -> addMusician(e));


    }

    public JPanel getContentView() {
        return contentView;
    }


    public Song getCurrentSong() {
        return ((Song)songList.getSelectedValue());
    }

    public void refresh() {
        reloadListData();
        repaintDetails();
    }

    private void reloadListData() {
        reloadData();

        DefaultListModel<Song> dlm = new DefaultListModel<>();
        for (Song s : dataSource) {
            dlm.addElement(s);
           // System.out.println("added song" + s.toString());
        }
        songList.setModel(dlm);


    }

    private void reloadData() {

        dataSource = Application.self.songService.all();


    }

    private void repaintDetails()  {
        if (currentSong == null) return;

        songNameLabel.setText(currentSong.getName());

        long album_id = currentSong.getAlbum_id();

        String albumName = null;

        try{
            albumName = Application.self.albumRepository.getBy(album_id).getName();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        albumNameLabel.setText(albumName);
        authorLabel.setText(currentSong.getAuthor());


        //add at least one musician

       List<Musician> mList = new ArrayList<>();

       try{
           mList= Application.self.songService.getSongMusicians(getCurrentSong().getId());
       }catch(Exception e){
           e.printStackTrace();
       }

       String allMusicians = "";
        try {
            for (Musician m : Application.self.songRepository.getSongMusicians(currentSong.getId())) {
                allMusicians += m.getFullName() + "; ";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        musicianNamesLabel.setText(allMusicians);
       /*
       if(mList.size()>0){
       musicianLastNameLabel.setText(mList.get(0).getLastName());
       musicianNamesLabel.setText(mList.get(0).getName());
       long musId = mList.get(0).getId();
        double fee_share = 0;
        try {
            fee_share = Application.self.musicianSongRepository.getFee(musId, getCurrentSong().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        musicianShareLabel.setText(String.valueOf(fee_share));
        }else {
           musicianLastNameLabel.setText(" - ");
           musicianNamesLabel.setText(" - ");
           musicianShareLabel.setText(String.valueOf(0));
        }
        */

    {
    }




    }


    private void didSelectListItem(ListSelectionEvent e) {
        int index = songList.getSelectedIndex();
        if (index < 0) return;
        currentSong = dataSource.get(index);

        repaintDetails();


    }

    public void addMusician(ActionEvent e) {
        musicians = Application.self.musicianService.all();
        Collections.sort(musicians,Musician.musComparator);
        JTextField feeShare = new JTextField();
        JComboBox musicianBox = new JComboBox();

        DefaultComboBoxModel<Musician> dlm = new DefaultComboBoxModel<>();
        for (Musician m : musicians) {
            dlm.addElement(m);
        }
        musicianBox.setModel(dlm);
        Object[] message = {
                "Musician:", musicianBox,
                "Fee share:", feeShare
        };
        int option = JOptionPane.showConfirmDialog(null, message, Strings.DIALOG_ADD_MUSICIAN, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            MusicianSong ms = new MusicianSong();
            ms.setMusician_id(((Musician)musicianBox.getSelectedItem()).getId());
            ms.setSong_id(getCurrentSong().getId());
            Double fee = null;
            try {
                fee = Double.parseDouble(feeShare.getText());
                if (fee < 0 || fee > 1) { /////////////////////////////////////////////////////////////////////////////////// maxFee
                    Application.showMessage(Strings.INPUT_WRONG_SHARE);
                } else {
                    ms.setFee_share(fee);
                    try {
                        Application.self.musicianSongRepository.insert(ms);
                        Application.showMessage(Strings.DIALOG_MUSICIAN_ADDED_TO_SONG);
                    } catch (Exception ex) {
                        Application.showMessage(ex.getMessage());
                    }
                }

            } catch (NumberFormatException ex) {
                Application.showMessage(Strings.DIALOG_NUMBER_FORMAT_ERROR);
                return;
            } catch (NullPointerException ex) {
                return;
            }


            repaintDetails();
        }

    }


}











