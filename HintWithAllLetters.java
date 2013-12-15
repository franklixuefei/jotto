

public class HintWithAllLetters extends Hint {

	private char[] letters;
	
	public HintWithAllLetters() {
		super("containing all letters", Hint.ALL);
	}

	@Override
	Word[] getHintWords(int maxDesired, IHintData hintData) {
		this.letters = hintData.getLetters();
		return hintData.getKnownWords().getWords(maxDesired, this);
	}

	@Override
	public boolean isOK(Word w) {
		for (char c : this.letters) {
			if (w.getWord().indexOf(c) < 0) {
				return false;
			}
		}
		return true;
	}

}
