

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class AnnotView extends JPanel implements IView{

	private JottoModel model;
	
	public AnnotView(JottoModel model_) {
		this.setPreferredSize(new Dimension(this.getPreferredSize().width, JottoGame.annotViewHeight));
		this.setBackground(Color.WHITE);
		model = model_;
		createLetterDisplayLayout();
	}
	
	private void createLetterDisplayLayout() {
		//JPanel letter_panel = new JPanel();
		this.setLayout(new GridLayout(2, 13));
		this.setBackground(Color.gray);
		this.setPreferredSize(new Dimension(0, 60));
		for (Integer i = 0; i < 26; i++) {
			final JButton lettersButton = new JButton();
			lettersButton.setFont(new Font("Arial", Font.BOLD, 12));
			lettersButton.setOpaque(true);
			lettersButton.setBorderPainted(false);
			lettersButton.setBackground(Color.WHITE);
			lettersButton.addMouseListener(new MouseAdapter() {
				    public void mouseClicked(MouseEvent e) {
				        if (e.getButton() == 1) { // if left click
				        	if (lettersButton.getBackground() == Color.GREEN) {
				        		lettersButton.setBackground(Color.WHITE);
				        	} else {
				        		lettersButton.setBackground(Color.GREEN);
				        	}
				        } else {
				        	if (lettersButton.getBackground() == Color.RED) {
				        		lettersButton.setBackground(Color.WHITE);
				        	} else {
				        		lettersButton.setBackground(Color.RED);
				        	}
				        }
				    }
			});
			lettersButton.setText(""+(char)('A'+i));
			this.add(lettersButton);
		}
	}

	
	@Override
	public void updateView() {
		// nothing
	}

}
