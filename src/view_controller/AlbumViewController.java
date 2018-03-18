package view_controller;

import app.Application;
import app.Constants;
import app.Strings;
import model.Album;
import model.Musician;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;

public class AlbumViewController {

    private List<Album> dataSource;
    private Album currentAlbum;

    private JList<Album> albumList = new JList<>();
    private JPanel contentView;

    private JLabel albumNameLabel;
    private JLabel recordDateLabel;
    private JLabel managerNameLabel;
    private JLabel managerPhoneLabel;
    private JLabel albumFeeShareLabel;
    private JLabel albumRateLabel;
    private JLabel albumPriceLabel;
    private JButton newPriceButton;
    private JLabel managerRateLabel;
    private JLabel managerFeeShareLabel;

    public AlbumViewController(){
        reloadListData();

        albumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        albumList.addListSelectionListener(e -> didSelectListItem(e));
        albumList.setSelectedIndex(0);

        newPriceButton.addActionListener(e -> willEditPrice(e));
    }

    public JPanel getContentView() {
        return contentView;
    }

    public long getCurrentId() {
        int index = albumList.getSelectedIndex();
        if (index < 0) {return -1;}
        currentAlbum = dataSource.get(index);
        return currentAlbum.getId();
    }

    public void refresh() {
        reloadListData();
        repaintDetails();
    }

    // MARK: model job
    private void reloadListData() {
        reloadData();
        DefaultListModel<Album> dlm = new DefaultListModel<>();
        for (Album a : dataSource) {
            dlm.addElement(a);
        }
        albumList.setModel(dlm);
    }

    private void reloadData() {
        dataSource = Application.self.albumService.all();
    }

    private void repaintDetails() {
        if (currentAlbum == null) {return;}
        albumNameLabel.setText(currentAlbum.getName());
        recordDateLabel.setText(currentAlbum.getRecordDate().toString());
        albumFeeShareLabel.setText(String.valueOf(currentAlbum.getFeeShare()));
        albumRateLabel.setText(String.valueOf(currentAlbum.getRating() * Constants.RATING_MAX_VALUE));
        albumPriceLabel.setText(String.valueOf(currentAlbum.getCurrentPrice()));

        Musician manager = currentAlbum.getManager();
        managerNameLabel.setText(manager.getName() + ' ' + manager.getLastName());
        managerPhoneLabel.setText(manager.getPhone());
        managerFeeShareLabel.setText(String.valueOf(currentAlbum.getManagerFeeShare()));
        managerRateLabel.setText(String.valueOf(currentAlbum.getManager().getRating() * Constants.RATING_MAX_VALUE));
    }

    // MARK: Listeners
    private void didSelectListItem(ListSelectionEvent e) {
        int index = albumList.getSelectedIndex();
        if (index < 0) {return;}
        currentAlbum = dataSource.get(index);
        repaintDetails();
    }

    private void willEditPrice(ActionEvent e) {
        String newPriceStr = JOptionPane.showInputDialog(null, Strings.DIALOG_NEW_PRICE);
        BigDecimal newPrice = null;
        try {
            newPrice = BigDecimal.valueOf(Double.parseDouble(newPriceStr));
        } catch (NumberFormatException ex) {
            Application.showMessage(Strings.DIALOG_NUMBER_FORMAT_ERROR);
            return;
        } catch (NullPointerException ex) {return;}
        currentAlbum.setCurrentPrice(newPrice);
        try {
            Application.self.albumPriceRepository.save(newPrice, currentAlbum);
        } catch (Exception ex) {
            Application.showMessage(ex.getMessage());
        }
        repaintDetails();
    }
}
