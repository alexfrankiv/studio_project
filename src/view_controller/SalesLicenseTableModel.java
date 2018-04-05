package view_controller;

import app.Application;
import app.Strings;
import model.Album;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SalesLicenseTableModel extends AbstractTableModel {
    public enum Filter {
        EMPTY, CURRENT, FINISHED
    }
    private ArrayList data;

    SalesLicenseTableModel(boolean hide) throws SQLException {
        ArrayList arr = (ArrayList) Application.self.saleService.getLicenseData(hide);
        arr.trimToSize();
        data = arr;
    }

    SalesLicenseTableModel(Album album, boolean hide) throws SQLException {//, Date from, Date till) throws SQLException, ParseException {
        ArrayList arr = (ArrayList) Application.self.saleService.getLicenseData(hide);
        arr.trimToSize();
        if (album != null) {
            arr = (ArrayList) Application.self.saleService.filterByAlbum(album, arr);
        }
		/*
		if (from != null)
			arr = (ArrayList) saleSer.filterByStart(from, arr);
		if (till != null)
			arr = (ArrayList) saleSer.filterByEnd(till, arr);
		*/
        data = arr;
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
		/*if (col == 7) {
			final JButton button = new JButton("Подробиці");
	        button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	                //...
	            }
	        });
	        return button;
		}*/
        return ((ArrayList) data.get(row)).get(col);
    }

    public String getColumnName(int col) {
        String[] names = Strings.SALES_LICENSE_TABLE_LABELS;//, "" };
        return (String) names[col];
    }

    public Class getColumnClass(int col){
        if (col == 6) return BigDecimal.class;
        return String.class;
    }


}
