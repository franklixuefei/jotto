
public class HintConsistentWithGuesses extends Hint {

	private Guess[] guessHistory;
	private String target;
	private IGuessEvaluator ge;
	
	public HintConsistentWithGuesses(IGuessEvaluator ge) {
		super("consistent with guesses", Hint.GUESSES);
		this.ge = ge;
	}

	@Override
	Word[] getHintWords(int maxDesired, IHintData hintData) {
		this.guessHistory = hintData.getGuessHistory();
		this.target = hintData.getTarget();
		return hintData.getKnownWords().getWords(maxDesired, this);
	}

	@Override
	public boolean isOK(Word w) {
		for (Guess g : guessHistory) {
			MatchCount mc1 = ge.countMatches(g.getWord(), w.getWord());
			MatchCount mc2 = ge.countMatches(g.getWord(), this.target);
			if (mc1.getExact() != mc2.getExact() || mc1.getPartial() != mc2.getPartial()) {
				return false;
			}
		}
		return true;
	}
	

}
