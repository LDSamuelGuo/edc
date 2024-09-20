package interlockTrain;
public class train {
	private String name;
	private int enterSection;
	private int destinationSection;
	private int currentSection;
	public int getSec() {
		return this.currentSection;
	}
	public int getEnt() {
		return this.enterSection;
	}
	public int getDest() {
		return this.destinationSection;
	}
	public String getName() {
		return this.name;
	}
	public void setSec(int section) {
		this.currentSection = section;
	}
	public train() {
		
	}
	public train(String name, int enter, int destination) {
		this.enterSection = enter;
		this.destinationSection = destination;
		this.currentSection = enter;
		this.name = name;
	}
}
