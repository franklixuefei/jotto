

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class InfoView extends JPanel implements IView{

	
	private JottoModel model;
	
	private JPanel difficultyPanel;
	private JComboBox difficultyBox;
	
	private JPanel restartButtonPanel;
	private JButton restartButton;
	
	
	public InfoView(JottoModel model_) {
		model = model_;
		
		createDifficultyPanel();
		createRestartPanel();
		this.setLayout(new GridBagLayout());
		this.setPreferredSize(new Dimension(this.getPreferredSize().width, JottoGame.infoViewHeight));
		this.setBackground(Color.decode("#00E5EE"));
		
		
		// setup the event to go to the "controller"
		// (this anonymous class is essentially the controller)
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				model.incrementCounter();
//			}
//		});	
		this.add(new JLabel("Level:"));
		this.add(difficultyPanel);
		this.add(Box.createHorizontalStrut(20));
		this.add(restartButtonPanel);
	}
	
	private void createDifficultyPanel() {
		difficultyPanel = new JPanel();
		// setlayout here
		difficultyPanel.setBackground(Color.decode("#00E5EE"));
		difficultyBox = new JComboBox(model.getDifficultyLevelsModel());
		difficultyPanel.add(difficultyBox);
	}
	
	private void createRestartPanel() {
		restartButtonPanel = new JPanel();
		restartButtonPanel.setBackground(Color.decode("#00E5EE"));
		restartButton = new JButton("Restart");
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int difficultyIndex = difficultyBox.getSelectedIndex();
				model.restart(difficultyIndex);
			}
		});
		// setlayout here
		restartButtonPanel.add(restartButton);
	}
	
	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		
	}

}
