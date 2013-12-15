

public class HintContainsLetter extends Hint{

	private char letter;
	
	public HintContainsLetter() {
		super("containing the letter", Hint.LETTER);
	}

	@Override
	Word[] getHintWords(int maxDesired, IHintData hintData) {
		this.letter = hintData.getLetter();
		return hintData.getKnownWords().getWords(maxDesired, this);
	}

	@Override
	public boolean isOK(Word w) {
		return w.getWord().indexOf(this.letter) >= 0;
	}
	

}
