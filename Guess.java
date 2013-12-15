

public class Guess extends MatchCount {

	private String testWord;
	
	Guess(String aWord, int numExact, int numPartial) {
		super(numExact, numPartial);
		// TODO Auto-generated constructor stub
		this.testWord = aWord;
	}
	
	String getWord() {
		return testWord;
	}
	
}
