

public class HintWithoutLetter extends Hint {

	private char letter;
	
	public HintWithoutLetter() {
		super("containing no letters", Hint.NONE);
	}

	@Override
	Word[] getHintWords(int maxDesired, IHintData hintData) {
		this.letter = hintData.getLetter();
		return hintData.getKnownWords().getWords(maxDesired, this);
	}

	@Override
	public boolean isOK(Word w) {
		return w.getWord().indexOf(this.letter) < 0;
	}
	
}
