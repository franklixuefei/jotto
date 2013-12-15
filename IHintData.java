

public interface IHintData {
	String getTarget();
	
	Guess[] getGuessHistory();
      
	IWordList getKnownWords();
      
	char getLetter();
      
	char[] getLetters();
	  
}
