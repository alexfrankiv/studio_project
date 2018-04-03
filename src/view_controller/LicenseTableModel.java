package view_controller;

import app.Application;
import app.Strings;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class LicenseTableModel extends AbstractTableModel {
    public enum Filter {
        EMPTY, CURRENT, FINISHED
    }
    private ArrayList data;

    LicenseTableModel() throws SQLException {
        ArrayList arr = (ArrayList) Application.self.saleService.getLicenseData();
        arr.trimToSize();
        data = arr;
    }

    LicenseTableModel(String type, String name) throws SQLException {//, Date from, Date till) throws SQLException, ParseException {
        ArrayList arr = (ArrayList) Application.self.saleService.getLicenseData();
        arr.trimToSize();
        if (type != null)
            arr = (ArrayList) Application.self.saleService.filterByType(type, arr);
        if (name != null)
            arr = (ArrayList) Application.self.saleService.filterByArtist(name, arr);
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
