
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class LetterTableModel extends DefaultTableModel implements IJottoTableModel {
	private static final long serialVersionUID = 1L;
	private JottoModel model;
	public LetterTableModel(JottoModel jottoModel) {
		this.model = jottoModel;
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
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex >= model.getGuessHistory().size()) return null;
		return model.getGuessHistory().get(rowIndex).getWord().charAt(columnIndex);
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return JottoModel.NUM_ROWS;
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return JottoModel.NUM_LETTERS;
	}
	
	@Override
	 public boolean isCellEditable(int row, int column) {
	     return false;
	 }
	
	
}
