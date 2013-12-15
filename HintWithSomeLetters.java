
public class HintWithSomeLetters extends Hint {

	private char[] letters; 
	private int numLetters;
	
	public HintWithSomeLetters(int numLetters) {
		super("containing some letters", Hint.LETTERS);
		this.numLetters = numLetters;
	}

	@Override
	Word[] getHintWords(int maxDesired, IHintData hintData) {
		this.letters = hintData.getLetters();
		return hintData.getKnownWords().getWords(maxDesired, this);
	}

	@Override
	public boolean isOK(Word w) {
		int accum = 0;
		for (char c : this.letters) {
			if (w.getWord().indexOf(c) >= 0) {
				accum++;
			}
		}
		return accum >= numLetters;
	}

}
