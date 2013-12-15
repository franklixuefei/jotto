

import java.awt.Color;

import javax.swing.table.DefaultTableModel;

public class JotTableModel extends DefaultTableModel implements IJottoTableModel {
	
	private JottoModel model;
	
	public JotTableModel(JottoModel jottoModel) {
		// TODO Auto-generated constructor stub
		this.model = jottoModel;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= model.getGuessHistory().size()) return null;
		if (columnIndex == 0) {
			return model.getGuessHistory().get(rowIndex).getPartial();
		} else {
			return model.getGuessHistory().get(rowIndex).getExact();
		}
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return JottoModel.NUM_ROWS;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	 public boolean isCellEditable(int row, int column) {
	     return false;
	 }

	@Override
	public Color getRowBackgroundColor(int row) {
		if (row > model.getGuessHistory().size()-1) {
			return Color.LIGHT_GRAY;
		} else {
			if (row % 2 == 0) {
				return Color.decode("#6699FF");
			} else {
				return Color.decode("#669966");
			}
		}
	}

	@Override
	public Color getRowBorderColor(int row) {
		if (model.getGameOver() || model.getWordFound()) { // game is not ongoing
			if (model.getWordFound()) { // if win
				return Color.GREEN;
			} else { // if lose
				return Color.RED;
			}
		}
		return null;
	}
}
