

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class JotCellRenderer extends DefaultTableCellRenderer {

/**
 * 
 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getHorizontalAlignment() {
		return JLabel.CENTER;
	}
	@Override
	public Color getForeground() {
		return Color.WHITE;
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JotTableModel model =(JotTableModel) table.getModel();
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setBackground(model.getRowBackgroundColor(row));
		return c;
	}
}
