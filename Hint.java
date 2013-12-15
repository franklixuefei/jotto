

public abstract class Hint implements IWordPredicate{
	 public static final String[] HINT_ARGS = {
	      "LETTER", "LETTERS", "ALL", "NONE", "GUESSES"};
	 public static final int LETTER 	= 0;
	 public static final int LETTERS 	= 1;
	 public static final int ALL 		= 2;
	 public static final int NONE 		= 3;
	 public static final int GUESSES 	= 4;
	
	protected String description;
	protected int argType;
	
	Hint(String aDescription, int argType) {
		this.description = aDescription;
		this.argType = argType;
	}
	
	int getArgumentType() {
		return this.argType;
	}
      
	String getDescription() {
		return this.description;
	}
      
	abstract Word[] getHintWords(int maxDesired, IHintData hintData);
      
	public abstract boolean isOK(Word w);
      
	static void main(String[] args) {
		
	}

}
