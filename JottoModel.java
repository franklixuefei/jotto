
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class JottoModel 
{
	public static final int NUM_LETTERS = 5;
	public static final int NUM_ROWS = 10;
	static final String[] LEVELS = {
      "Easy", "Medium", "Hard", "Any"};
	
	static final int EASY 	= 0;
	static final int MEDIUM = 1;
	static final int HARD 	= 2;
	static final int ANY 	= 3;
	
	
	private String inputLetters; // five chars for a word
	
	private ArrayList<IView> views = new ArrayList<IView>();
	
	private ArrayList<Hint> hints = new ArrayList<Hint>();
	
	private int score = 0;
	
	private LetterTableModel letterTableModel = new LetterTableModel(this);
	
	private JotTableModel jotTableModel = new JotTableModel(this);
	
	private DefaultComboBoxModel hintTypesModel;
	
	private DefaultListModel hintListModel;
	
	private DefaultComboBoxModel difficultyLevelsModel;
	
	private IWordList wordlist;
	
	private IGuessEvaluator guessEvaluator;
	
	private ArrayList<Guess> guessHistory = new ArrayList<Guess>();
	
	private int level = EASY;
	
	private Word target;
	
	private boolean computerAidMode = false;
	
	private boolean gameOver = false;
	private boolean wordGuessed = false;
	
	public JottoModel() {
		
//		inputLetters[0] = 'C'; // testing
//		inputLetters[1] = 'A'; // testing
//		inputLetters[2] = 'E'; // testing
//		inputLetters[2] = 'D'; // testing
		generateTopViewModels();
		generateGameBoardModels();
		generateHintBoardModels();
		
	
	}
	
	public JottoModel(IWordList wl, IGuessEvaluator ge) {
		this();
		wordlist = wl;
		guessEvaluator = ge;
		target = wordlist.randomWord(level);
		System.out.println("Target: "+ target.getWord() +" Difficulty: " + target.getDifficulty());
	}
	
	public void inputString(String s) { // API
		System.out.println(s);
		this.inputLetters = s.toUpperCase();
	}
	
	public void setComputerAidMode(boolean onOff) { // API for computer aid mode button
		this.computerAidMode = onOff;
	}
	
	public int[] getTestWordAid() { // 1 - to circle green(exact match); 2 - to circle orange(partial match; 0 - neither)
		int[] ret = new int[NUM_LETTERS];
		for (int i = 0; i < NUM_LETTERS; ++i) {
			if (this.target.toString().indexOf(this.inputLetters.charAt(i)) >= 0) {
				if (this.inputLetters.charAt(i) == this.target.getWord().charAt(i)) {
					ret[i] = 1;
				} else {
					ret[i] = 2;
				}
			} else {
				ret[i] = 0;
			}
		}
		return ret;
	}
	 
	public boolean getComputerAidMode() { // to determine if this aid mode is on or not
		return this.computerAidMode;
	}
	
	public void setTargetWord(String aTarget) { // for the sake of testing
		int position = wordlist.contains(aTarget);
		if ((position != -1)?true:false) {
			try {
				target = wordlist.get(position);
			} catch(IllegalArgumentException e) {
				// impossible
			}
		}
	}
	
	public boolean generateGuess() { // API for Guess button getting pressed
		if (this.inputLetters.length() != NUM_LETTERS) return false;
		
		if ((wordlist.contains(this.inputLetters) == -1)) { // not found
			return false;
		}
		
		MatchCount mc = guessEvaluator.countMatches(this.inputLetters.toString(), this.target.getWord());
		if (guessHistory.size() == JottoModel.NUM_ROWS) {
			return false;
		}
		guessHistory.add(new Guess(this.inputLetters.toString(), mc.getExact(), mc.getPartial()));
		System.out.println("mc: " + mc.getPartial() + ", " + mc.getExact());
		if (guessHistory.size() == JottoModel.NUM_ROWS) {
			this.gameOver = true;
		} else {
			this.gameOver = false;
		}
		
		
		if (mc.getExact() == JottoModel.NUM_LETTERS) {
			this.wordGuessed = true;
		} else {
			this.wordGuessed = false;
		}
		
		letterTableModel.fireTableDataChanged();
		jotTableModel.fireTableDataChanged();
		notifyObservers();
		return true;
	}
	
	public Word[] getHints(int maxDesired, int hintType) { // API for Hints button getting pressed
		Hint hint = null;
		for (Hint hd : hints) {
			if (hd.getArgumentType() == hintType) {
				hint = hd;
				break;
			}
		}
		System.out.println("selected hint type: " + hint.getArgumentType());
		return hint.getHintWords(maxDesired, new IHintData() {

			@Override
			public String getTarget() {
				return JottoModel.this.target.toString();
			}
			
			@Override
			public char[] getLetters() {
				return JottoModel.this.inputLetters.toCharArray();
			}
			
			@Override
			public char getLetter() {
				System.out.println("input letters:" + JottoModel.this.inputLetters);
				int lastIndex = JottoModel.this.inputLetters.length()-1;
				return JottoModel.this.inputLetters.charAt(lastIndex);
			}
			
			@Override
			public IWordList getKnownWords() {
				return JottoModel.this.wordlist;
			}
			
			@Override
			public Guess[] getGuessHistory() {
				return JottoModel.this.guessHistory.toArray(new Guess[JottoModel.this.guessHistory.size()]);
			}

		});
	}
	
	private void generateTopViewModels() {
		difficultyLevelsModel = new DefaultComboBoxModel();
		for (String str : LEVELS) {
			difficultyLevelsModel.addElement(str);
		}
	}
	
	private void generateGameBoardModels() {
		// already handled in LetterTableModel.java
		// and JotTableModel.java

	}
	
	private void generateHintBoardModels() {
		hintListModel = new DefaultListModel();
		
		hintTypesModel = new DefaultComboBoxModel();
		for (String str : Hint.HINT_ARGS) {
			hintTypesModel.addElement(str);
		}
		
	}
	
	public void addElementsToHintList(int maxDesired, int hintType) {
		Word[] hintwords = this.getHints(maxDesired, hintType);
		hintListModel.clear();
		for (Word word : hintwords) {
			hintListModel.addElement(word.getWord());
		}
	}
	
	public DefaultComboBoxModel getDifficultyLevelsModel() {
		return difficultyLevelsModel;
	}
	
	
	public LetterTableModel getLetterTableModel() {
		return letterTableModel;
	}
	
//	public void setLetterTableModel(AbstractTableModel t) {
//		this.letterTableModel = t;
//	}
//	
//	public DefaultTableModel getScoreTableModel() {
//		return scoreTableModel;
//	}
	
//	public void setScoreTableModel(AbstractTableModel t) {
//		this.scoreTableModel = t;
//	}
	
	public JotTableModel getJotTableModel() {
		return jotTableModel;
	}
	
//	public void setJotTableModel(AbstractTableModel t) {
//		this.jotTableModel = t;
//	}
	
	public DefaultComboBoxModel getHintTypesModel() {
		return hintTypesModel;
	}
	
	public DefaultListModel getHintListModel() {
		return hintListModel;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setGameOver(boolean over) {
		this.gameOver = over;
	}
	
	public boolean getGameOver() {
		return this.gameOver;
	}
	
	public void setWordFound(boolean win) {
		this.wordGuessed = win;
	}
	
	public boolean getWordFound() {
		return this.wordGuessed;
	}
	
	public ArrayList<Guess> getGuessHistory() {
		return this.guessHistory;
	}
	
	public void addView(IView view) {
		views.add(view);
		
		// update the view to current state of the model
		notifyObservers();
	}
	
	public void addHint(Hint aHint) {
		hints.add(aHint);
	}
	
	public void restart(int level) {
		this.level = level;
		this.inputLetters = "";
		this.guessHistory.clear();
		this.gameOver = false;
		this.wordGuessed = false;
		target = wordlist.randomWord(this.level);
		System.out.println("Target: "+ target.getWord() +" Difficulty: " + target.getDifficulty());
		letterTableModel.fireTableDataChanged();
		jotTableModel.fireTableDataChanged();
		notifyObservers();
	}
	
	// notify the IView observer
	private void notifyObservers() {
		for (IView view : this.views) {
			System.out.println("Model: notify View");
			view.updateView();
		}
	}
	
}
