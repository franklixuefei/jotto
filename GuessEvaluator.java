
public final class GuessEvaluator extends Object implements IGuessEvaluator {

	@Override
	public MatchCount countMatches(String word1, String word2) {
		// TODO Auto-generated method stub
		char[] word1arr = word1.toCharArray();
		char[] word2arr = word2.toCharArray();
		boolean[] word1arrProcessed = new boolean[JottoModel.NUM_LETTERS];
		boolean[] word2arrProcessed = new boolean[JottoModel.NUM_LETTERS];
		int numPartial = 0;
		int numExact = 0;
		// exact matches
		for (int i = 0; i < word1arr.length; ++i) {
			if (word1arr[i] == word2arr[i]) {
				numExact++;
				word1arrProcessed[i] = word2arrProcessed[i] = true;
			}
		}
		// partial matches
		for (int i = 0; i < word1arr.length; ++i) {
			if (word1arrProcessed[i]) continue;
			for (int j = 0; j < word2arr.length; ++j) {
				if (i != j) {
					if (word2arrProcessed[j]) continue; // skip already processed chars
					if (word1arr[i] == word2arr[j]) {
						numPartial++;
						word1arrProcessed[i] = true; // no practical value
						word2arrProcessed[j] = true;
						break;
					}
				}
			}
		}
		
		return new MatchCount(numExact, numPartial);
	}
	
	public static void main(String[] args) {
		GuessEvaluator ge = new GuessEvaluator();
		MatchCount mc = ge.countMatches("ABABA", "BABAB");
		System.out.println("exact: " + mc.getExact() + "\npartial: " + mc.getPartial());
	}
	
}
