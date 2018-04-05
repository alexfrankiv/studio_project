package view_controller;

import app.Application;
import app.Constants;
import app.Strings;
import model.Musician;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

public class MusicianViewController {
    private List<Musician> dataSource;
    private Musician currentMusician;

    private JList<Musician> musicianList;
    private JPanel contentView;

    public MusicianViewController(){
        reloadListData();
        musicianList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        musicianList.addListSelectionListener(e -> didSelectListItem(e));
        musicianList.setSelectedIndex(0);
    }

    public JPanel getContentView() {
        return contentView;
    }

    private void didSelectListItem(ListSelectionEvent e) {
        int index = musicianList.getSelectedIndex();
        if (index < 0) {return;}
        currentMusician = dataSource.get(index);
    }

    private void reloadListData() {
        reloadData();
        DefaultListModel<Musician> dlm = new DefaultListModel<>();
        for (Musician a : dataSource) {
            dlm.addElement(a);
        }
        musicianList.setModel(dlm);
    }

    private void reloadData() {
        dataSource = Application.self.musicianService.all();
    }


}
