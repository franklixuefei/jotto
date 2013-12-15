
public class MatchCount extends Object {
	private int numExact;
	private int numPartial;
	
	MatchCount(int numExact, int numPartial) {
		this.numExact = numExact;
		this.numPartial = numPartial;
	}
	
	int getExact() {
		return numExact;
	}
	
	int getPartial() {
		return numPartial;
	}
}
