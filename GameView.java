

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;




public class GameView extends JPanel implements IView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JottoModel model;
	
	private JTable scoreGrid;
	private JTable letterGrid;
	private JTable jotGrid;
	private JButton guessButton;
	private JTextField guessBox;
	private JPanel letterGridPanel;
	private JPanel jotGridPanel;
	
	private JPanel hintControllerPanel;
	private JComboBox hintTypes;
	private JTextField maxHints;
	private JButton hintButton;
	
	private JPanel hintListPanel;
	private JList hintList;
	
	private JLabel winLabel;
	private JLabel loseLabel;
	
	private boolean oldStatus;

	public GameView(JottoModel model_) {
		model = model_;
		this.createLetterGrid();
		this.createJotGrid();
		this.createHintController();
//		this.createHintList();
		
		this.setLayout(new GridBagLayout());
		JPanel gameBoardPanel = new JPanel();
		gameBoardPanel.setLayout(new BoxLayout(gameBoardPanel, BoxLayout.X_AXIS));
		gameBoardPanel.add(letterGridPanel);
		gameBoardPanel.add(Box.createHorizontalStrut(20));
		gameBoardPanel.add(jotGridPanel);
		gameBoardPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Jotto Board",
				TitledBorder.CENTER,
				TitledBorder.TOP));

		JPanel hintPanel = new JPanel();
		
		hintPanel.setLayout(new BoxLayout(hintPanel, BoxLayout.Y_AXIS));
//		hintPanel.add(hintControllerPanel);
		hintPanel.add(Box.createHorizontalStrut(20));
		hintPanel.add(createHintList());
//		table_panel.setAlignmentX(0.0f);
//		table_panel.setAlignmentY(0.0f);
		
		
		GridBagConstraints gc = new GridBagConstraints();

//		gc.insets = new Insets(10, 10, 10, 10);
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx= 0.5;
		gc.weighty = 0.5;
		this.add(gameBoardPanel, gc);
		this.add(hintPanel, gc);
		
		
	}
//	
//	private void createScoreGrid() {
//		String[] colHeadings = {"SCORE"};
//
//		scoreGrid = new JTable(model.getScoreTableModel());
//		scoreGrid.setBackground(Color.gray);
//		scoreGrid.setBorder(BorderFactory.createLineBorder(Color.black,1));
//		scoreGrid.setAutoCreateColumnsFromModel(true);
//		scoreGrid.setRowHeight(40);
//		//table.setMaximumSize(new Dimension(500,300));
//		//table.setMinimumSize(new Dimension(500,500));
//		scoreGrid.setPreferredSize(new Dimension(40, 400));
//		// a GridBagLayout with default constraints centres
//		// the widget in the window
//		GridBagConstraints gc = new GridBagConstraints();
//		gc.weightx = 0.5;
//		gc.weighty = 0.5;
//		gc.anchor = GridBagConstraints.EAST;
//		this.add(scoreGrid, gc);
//	}
	
	private JDialog createPopupWindow(String heading, String content) {
		JDialog popupWindow = new JDialog(new JFrame(), heading, false);
		JLabel body = new JLabel(content);
		body.setHorizontalAlignment(JLabel.CENTER);
		body.setPreferredSize(new Dimension(300,200));
//		JTextArea textArea = new JTextArea(content);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);
//		JScrollPane areaScrollPane = new JScrollPane(textArea);
		popupWindow.add(body);
		popupWindow.pack();
		return popupWindow;
		
		
	}

	
	private void createLetterGrid() {		
		letterGridPanel = new JPanel();
		letterGridPanel.setLayout(new BoxLayout(letterGridPanel, BoxLayout.Y_AXIS));
		letterGridPanel.setAlignmentX(CENTER_ALIGNMENT);
//		letterGrid = new JTable(data, columnNames) {
//			public TableCellRenderer getCellRenderer( int row, int column ) {
//				System.out.println("asdfa");
//                return new PlusMinusCellRenderer();
//            }
//		};
		letterGrid = new JTable(model.getLetterTableModel()) 
			{ // this is for pre-reder row borders
				/**
				 * 
				 */
				
				private static final long serialVersionUID = 5L;
				

				public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
					Color borderColor = model.getLetterTableModel().getRowBorderColor(row);
					
					Border outside = new MatteBorder(4, 0, 4, 0, borderColor);
					Border inside = new EmptyBorder(0, 4, 0, 4);
					Border highlight = new CompoundBorder(outside, inside);
					
					Border leftBorder = new MatteBorder(4,4,4,0, borderColor);
					Border rightBorder = new MatteBorder(4,0,4,4,borderColor);
					
					
					Component c = super.prepareRenderer(renderer, row, column);
					JComponent jc = (JComponent)c;
					
					if ((model.getGameOver() || model.getWordFound()) && row == model.getGuessHistory().size()-1) {
						jc.setBorder( highlight );
						if (column == 0) {
							jc.setBorder(leftBorder);
						}
						if (column == JottoModel.NUM_LETTERS-1) {
							jc.setBorder(rightBorder);
						}
					}

					return c;
				}
			};
		
		letterGrid.setBackground(Color.magenta);
		letterGrid.setBorder(BorderFactory.createLineBorder(Color.black,1));
		letterGrid.setAutoCreateColumnsFromModel(true);
		letterGrid.setRowHeight(40);
		letterGrid.setPreferredSize(new Dimension(200, 400));
		letterGrid.setFocusable(false);
		letterGrid.setRowSelectionAllowed(false);
		letterGrid.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		LetterCellRenderer letterCellRenderer = new LetterCellRenderer();
		letterGrid.setDefaultRenderer(Object.class, letterCellRenderer);
		letterGrid.setFont(new Font("Arial", Font.BOLD, 18));
		
		
//		PanelCellEditorRenderer p = new PanelCellEditorRenderer();
//		letterGrid.setDefaultRenderer(Object.class, p);
//		letterGrid.setDefaultEditor(Object.class, p);
//		letterGrid.setCellSelectionEnabled(true);
		guessBox = new JTextField();
		
//		guessBox.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// go to the model
//				System.out.println("Play pressed");
//			}
//		});
		guessBox.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			
				if (guessBox.getText().length() > 0) {
					if (hintTypes.getSelectedIndex() == Hint.LETTERS) {
						hintButton.setEnabled(guessBox.getText().length() >= 3);
					} else {
						hintButton.setEnabled(true);
					}
				} else {
					hintButton.setEnabled(false);
				}
				if (!(model.getGameOver() || model.getWordFound())) {
					if (guessBox.getText().length() == JottoModel.NUM_LETTERS) {
						guessButton.setEnabled(true);
					} else {
						guessButton.setEnabled(false);
					}
				}
			}
			
			@Override
			public void keyPressed (KeyEvent e) { 		
				if (!(model.getGameOver() || model.getWordFound())) { // game not over or win
					if (guessBox.getText().length() == JottoModel.NUM_LETTERS && e.getKeyCode() == KeyEvent.VK_ENTER) {
						GameView.this.guessButtonPressed();
						guessBox.setText("");
					}
				}
			}

		});
		guessBox.setAlignmentX(CENTER_ALIGNMENT);
		
		letterGridPanel.add(guessBox);
		letterGridPanel.add(Box.createVerticalStrut(20));
//		letterGridPanel.add(new JLabel("Guesses"));
		letterGridPanel.add(letterGrid);
		
		
		
	}
	
	
	private void createJotGrid() {	
		jotGridPanel = new JPanel();
		jotGridPanel.setLayout(new BoxLayout(jotGridPanel, BoxLayout.Y_AXIS));
		jotGridPanel.setAlignmentX(CENTER_ALIGNMENT);
		
		jotGrid = new JTable(model.getJotTableModel())
		{ // this is for pre-reder row borders
			/**
			 * 
			 */
			
			private static final long serialVersionUID = 6L;
			

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Color borderColor = model.getJotTableModel().getRowBorderColor(row);
				
				Border outside = new MatteBorder(4, 0, 4, 0, borderColor);
				Border inside = new EmptyBorder(0, 4, 0, 4);
				Border highlight = new CompoundBorder(outside, inside);
				
				Border leftBorder = new MatteBorder(4,4,4,0, borderColor);
				Border rightBorder = new MatteBorder(4,0,4,4,borderColor);
				
				
				Component c = super.prepareRenderer(renderer, row, column);
				JComponent jc = (JComponent)c;
				
				if ((model.getGameOver() || model.getWordFound()) && row == model.getGuessHistory().size()-1) {
					jc.setBorder( highlight );
					if (column == 0) {
						jc.setBorder(leftBorder);
					}
					if (column == 1) {
						jc.setBorder(rightBorder);
					}
				}

				return c;
			}
		};
	
		jotGrid.setBackground(Color.GREEN);
		jotGrid.setBorder(BorderFactory.createLineBorder(Color.black,1));
		jotGrid.setAutoCreateColumnsFromModel(true);
		jotGrid.setRowHeight(40);
		jotGrid.setPreferredSize(new Dimension(80, 400));
		jotGrid.setFocusable(false);
		jotGrid.setRowSelectionAllowed(false);
		jotGrid.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		JotCellRenderer jotCellRenderer = new JotCellRenderer();
		jotGrid.setDefaultRenderer(Object.class, jotCellRenderer);
		
		jotGrid.setFont(new Font("Arial", Font.BOLD, 20));
		
		
		guessButton = new JButton();
		guessButton.setText("Guess");
		guessButton.setEnabled(false);
		guessButton.setBackground(Color.red);
//		play.setPreferredSize(new Dimension(80, 30));
		guessButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// go to the model
				GameView.this.guessButtonPressed();
				System.out.println("Play pressed");
			}
		});
		
		guessButton.setAlignmentX(CENTER_ALIGNMENT);
		
		jotGridPanel.add(guessButton);
		jotGridPanel.add(Box.createVerticalStrut(20));
//		JLabel label = new JLabel("Partial      Exact");
//		label.setFont(new Font("Arial", Font.ITALIC, 10));
//		jotGridPanel.add(label);
		jotGridPanel.add(jotGrid);
		
	}
	
	private void createHintController() {
		hintControllerPanel = new JPanel();
		hintControllerPanel.setLayout(new BoxLayout(hintControllerPanel, BoxLayout.X_AXIS));
		hintTypes = new JComboBox(model.getHintTypesModel());
		hintTypes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (hintTypes.getSelectedIndex() == Hint.LETTERS) {

					if (guessBox.getText().length() < 3) {
						hintButton.setEnabled(false);
					} else {
						hintButton.setEnabled(true);
					}
				} else {
					if (guessBox.getText().length() > 0) {
						hintButton.setEnabled(true);
					} else {
						hintButton.setEnabled(false);
					}
				}
				
			}
		});
		maxHints = new JTextField("10", 3);
		hintButton = new JButton("Get Hint");
		hintButton.setEnabled(false);
		hintButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hint button pressed");
				hintButtonPressed();
				
			}
		});
		
		hintControllerPanel.add(hintTypes);
		hintControllerPanel.add(maxHints);
		hintControllerPanel.add(hintButton);
		
	}
	
	private JPanel createHintList() {
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
		winLabel = new JLabel("You Win!");
		winLabel.setFont(new Font("Arial", Font.BOLD, 30));
		winLabel.setForeground(Color.ORANGE);
		winLabel.setVisible(false);
		outerPanel.add(winLabel);
		outerPanel.add(Box.createVerticalStrut(50));
		hintListPanel = new JPanel();
//		hintListPanel.setAlignmentX(4.0f);
		JLabel l = new JLabel("     Type                  #Hints     ");
		l.setAlignmentX(1.0f);
		hintListPanel.add(l);
		hintListPanel.setAlignmentX(0.3f);
		hintListPanel.add(hintControllerPanel);
		hintListPanel.setLayout(new BoxLayout(hintListPanel, BoxLayout.Y_AXIS));
		hintList = new JList(model.getHintListModel());
		JScrollPane scrollable = new JScrollPane(hintList);
		hintListPanel.add(scrollable);

		hintListPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
				"Hint Board",
				TitledBorder.CENTER,
				TitledBorder.TOP));
		outerPanel.add(hintListPanel);

		loseLabel = new JLabel("You Lose...");
		loseLabel.setFont(new Font("Arial", Font.BOLD, 30));
		loseLabel.setForeground(Color.GRAY);
		loseLabel.setVisible(false);
		outerPanel.add(Box.createVerticalStrut(50));
		outerPanel.add(loseLabel);
		return outerPanel;
	}
	
	
	private void guessButtonPressed() {
		model.inputString(this.guessBox.getText());
		if (!model.generateGuess()) {
			JDialog popup = this.createPopupWindow("Error", "<html><p>The word \""+ this.guessBox.getText() +"\" is not a proper word.<br/>Please choose another one<br/>(Get hints by clicking on \"Get Hints\")</p></html>");
			popup.setLocationRelativeTo(null);
			popup.setVisible(true);
			popup.toFront();
		}
		this.guessBox.setText("");
		guessButton.setEnabled(false);
		this.guessBox.requestFocus();
	}
	
	private void hintButtonPressed() {
		try {
			model.inputString(guessBox.getText());
			System.out.println("partial text: " + maxHints.getText() + " with hint types: " + hintTypes.getSelectedIndex());
			model.addElementsToHintList(Integer.parseInt(maxHints.getText()), hintTypes.getSelectedIndex());
		} catch(Exception e) {
			JDialog popup = this.createPopupWindow("Error", "<html><p>#Hints should be associated with a non-negative number.</p></html>");
			popup.setLocationRelativeTo(null);
			popup.setVisible(true);
			popup.toFront();
			
		}
	}
	
	@Override
	public void updateView() {
		// TODO Auto-generated method stub
		if (model.getGameOver()) {
			loseLabel.setVisible(true);
		} else {
			loseLabel.setVisible(false);
		}
		
		if (model.getWordFound()) {
			winLabel.setVisible(true);
		} else {
			winLabel.setVisible(false);
		}
		
	}
	
}

