
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;


public class JottoGame {

	private static final Dimension preferredAppSize = new Dimension(728, 800);
	private static final Dimension minAppSize = new Dimension(728, 800);
	
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static final int infoViewHeight = 70;
	
	public static final int annotViewHeight = 70;
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("Jotto");
		
		// create Model and initialize it
		
		IWordList wordList = new WordList();

		IGuessEvaluator guessEvaluator = new GuessEvaluator();
		
		JottoModel model = new JottoModel(wordList, guessEvaluator);
	    model.addHint(new HintContainsLetter());
	    model.addHint(new HintWithoutLetter());
	    model.addHint(new HintWithAllLetters());
	    model.addHint(new HintWithSomeLetters(3));
	    model.addHint(new HintConsistentWithGuesses(guessEvaluator));
		
		// create information view, tell it about model (and controller)
		InfoView infoView = new InfoView(model);
		// tell Model about View. 
		model.addView(infoView);
		
		// create game view ...
		GameView gameView = new GameView(model);
		model.addView(gameView);
		
		// create annotation view ...
		AnnotView annotView = new AnnotView(model);
		model.addView(annotView);
		
		// create the window
		JPanel p = new JPanel(new BorderLayout());
		frame.setBackground(Color.decode("#CCFFFF"));
		frame.getContentPane().add(p);
		p.add(infoView, BorderLayout.NORTH);
		p.add(gameView, BorderLayout.CENTER);
//		p.add(new JLabel("Annotation: (green - must exist in secret word; red - cannot exist in secret word)"), BorderLayout.SOUTH);
		p.add(annotView, BorderLayout.SOUTH);
		
		
		frame.setMinimumSize(JottoGame.minAppSize);
		frame.setBounds(JottoGame.screenSize.width/2-JottoGame.preferredAppSize.width/2, JottoGame.screenSize.height/2-JottoGame.preferredAppSize.height/2, JottoGame.preferredAppSize.width, JottoGame.preferredAppSize.height);
		frame.setPreferredSize(JottoGame.preferredAppSize);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
