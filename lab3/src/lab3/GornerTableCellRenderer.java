package lab3;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.sql.DatabaseMetaData;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer  implements TableCellRenderer {
	private Double needle1 = null;
	private Double needle2 = null;
	private Double val;
	private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();
	public void setNeedle(Double needle1,Double needle2) { 
		this.needle1 =needle1;
		this.needle2=needle2;
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		val=(Double)table.getModel().getValueAt(row, 0);
		String formattedDouble = formatter.format(value);
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		label.setText(formattedDouble);
		panel.add(label);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		if((col% 2 == 0 && row%2 !=0) || (col%2 !=0 && row%2==0))
		{
			label.setForeground(Color.WHITE);
			panel.setBackground(Color.black);
		}
		else
		{
			panel.setBackground(Color.WHITE);
		}
		if((col==1 || col==2 || col==3 ) && needle1 != null && needle2  != null && (val>=needle1 && val<=needle2))
		{
			panel.setBackground(Color.RED);
		}
		return panel;
	}
	public GornerTableCellRenderer()
	{
		formatter.setMaximumFractionDigits(5);
		formatter.setGroupingUsed(false);
		DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols(); 
		dottedDouble.setDecimalSeparator('.'); 
		formatter.setDecimalFormatSymbols(dottedDouble);		
	}
}
