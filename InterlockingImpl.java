package interlockTrain;
import java.util.*;
import java.lang.*;
public class InterlockingImpl implements Interlocking{
	ArrayList <train> a = new ArrayList ();/*This is a arraylist that stores the train that is created.*/
	
	public static void main(String args []) {
		InterlockingImpl imp = new InterlockingImpl();
		imp.addTrain("t1", 1, 8);
		imp.addTrain("t2", 3, 4);
		imp.addTrain("t3", 9, 2);
		//System.out.println(imp.a.get(0).getName());
		//System.out.println(imp.getTrain("t2"));
		String [] trains = {"t1", "t2", "t3"};
		int all = 0;
		while(!imp.a.isEmpty()) {
			all = imp.moveTrains(trains);
			int i = 0;
			while(i < imp.a.size()) {
				if(imp.a.get(i).getSec() == -1) {
					imp.a.remove(i);
					continue;
				}
				else i++;
			}
		}
		System.out.println(all);
	}
	
	public void addTrain(String trainName, int entryTrackSection, int destinationTrackSection) {
		int i = 0;
		int judge1 = 1;
		int judge2 = 1;
		while(i < a.size()) {
			if(trainName != a.get(i).getName()) {
				i++;
			}
			else {
				judge1 = 0;
				throw new IllegalArgumentException();
			}
		}
		i = 0;
		while(i < a.size()) {
			if(entryTrackSection != a.get(i).getSec()) {
				i++;
			}
			else {
				judge2 = 0;
				throw new IllegalStateException();
			}
		}
		if(judge1 == 1 && judge2 == 1) {
			train t = new train(trainName, entryTrackSection, destinationTrackSection);
			a.add(t);
		}
	}
	
	public int moveTrains(String[] trainNames) {
		int len = trainNames.length;
		int total = 0;
		int i = 0;
		int count = 0;
		while(count < len - 1) {
			if(a.get(count).getSec() == -1) {
				throw new IllegalArgumentException();
			}
			else count++;
		}
		while(i < len) {
			if(getTrain(trainNames[i]) == -1) {
				i++;
				continue;
			}
			if(getTrain(trainNames[i]) == 1) {
				if(getSection(5) != "NULL") {
					i++;
					continue;
				}
				else {
					move(trainNames[i], 5);
					total++;
					i++;
					continue;
				}
			}
			if(getTrain(trainNames[i]) == 2) {
				move(trainNames[i], -1);
				total++;
				i++;
				continue;
			}
			if(getTrain(trainNames[i]) == 3) {
				if(getSection(1) != "NULL" || getSection(6) != "NULL") {
					i++;
					continue;
				}
				else {
					if(getDestination(trainNames[i]) == 3) {
						move(trainNames[i], -1);
						total++;
						i++;
						continue;
					}
					else if(getDestination(trainNames[i]) == 4){
						if(getSection(1) != "NULL") {
							i++;
							continue;
						}
						else {
							move(trainNames[i], 4);
							total++;
							i++;
							continue;
						}
					}
					else{
						move(trainNames[i], 7);
						total++;
						i++;
						continue;
					}
				}
			}
			if(getTrain(trainNames[i]) == 4) {
				if(getDestination(trainNames[i]) == 4) {
					move(trainNames[i], -1);
					total++;
					i++;
					continue;
				}
				else {
					if(getSection(1) != "NULL") {
						i++;
						continue;
					}
					else {
						move(trainNames[i], 3);
						total++;
						i++;
						continue;
					}
				}
			}
			if(getTrain(trainNames[i]) == 5) {
				if(getDestination(trainNames[i]) == 8 && (getSection(9) == "NULL" || getDestination(getSection(9)) == 9)) {
					move(trainNames[i], 8);
					total++;
					i++;
					continue;
				}
				else if(getDestination(trainNames[i]) == 8 && getDestination(getSection(9)) == 2) {
					i++;
					continue;
				}
				else if(getDestination(trainNames[i]) == 9 && (getSection(9) == "NULL" || getDestination(getSection(9)) == 9)) {
					move(trainNames[i], 8);
					total++;
					i++;
					continue;
				}
				else if(getDestination(trainNames[i]) == 9 && getDestination(getSection(9)) == 2) {
					i++;
					continue;
				}
			}
			if(getTrain(trainNames[i]) == 6) {
				move(trainNames[i], 2);
				total++;
				i++;
				continue;
			}
			if(getTrain(trainNames[i]) == 7) {
				if(getSection(3) != "NULL" && getDestination(trainNames[i]) == 3) {
					i++;
					continue;
				}
				else if(getDestination(trainNames[i]) == 3 && getSection(4) != "NULL") {
					i++;
					continue;
				}
				else if(getDestination(trainNames[i]) == 11){
					move(trainNames[i], 11);
					total++;
					i++;
					continue;
				}
				else if(getDestination(trainNames[i]) == 3) {
					move(trainNames[i], 3);
					total++;
					i++;
					continue;
				}
			}
			if(getTrain(trainNames[i]) == 8) {
				move(trainNames[i], -1);
				total++;
				i++;
				continue;
			}
			if(getTrain(trainNames[i]) == 9) {
				if(getDestination(trainNames[i]) == 9) {
					move(trainNames[i], -1);
					total++;
					i++;
					continue;
				}
				else {
					move(trainNames[i], 6);
					total++;
					i++;
					continue;
				}
			}
			if(getTrain(trainNames[i]) == 10) {
				if(getSection(9) != "NULL" && getDestination(getSection(9)) == 2) {
					i++;
					continue;
				}
				else {
					move(trainNames[i], 6);
					total++;
					i++;
					continue;
				}
			}
			if(getTrain(trainNames[i]) == 11) {
				if(getDestination(trainNames[i]) == 11) {
					move(trainNames[i], -1);
					total++;
					i++;
					continue;
				}
				else {
					move(trainNames[i], 7);
					total++;
					i++;
					continue;
				}
			}
		}
		return total;
	}
	
	public String getSection(int trackSection) {
		int i = 0;
		if(trackSection > 11 || trackSection < 1) {
			throw new IllegalArgumentException();
		}
		while(i < a.size()) {
			if(a.get(i).getSec() == trackSection) {
				return a.get(i).getName();
			}
			else
				i++;
		}
		return "NULL";
	}
	
	public int getEntry(String trainName) {
		int i = 0;
		while(i < a.size()) {
			if(a.get(i).getName() == trainName) {
				return a.get(i).getEnt();
			}
			else {
				i++;
			}
		}
		return -1;
	}
	
	public int getDestination(String trainName) {
		int i = 0;
		while(i < a.size()) {
			if(a.get(i).getName() == trainName) {
				return a.get(i).getDest();
			}
			else {
				i++;
			}
		}
		return -1;
	}
	
	public int getTrain(String trainName) { 
		int i = 0;
		int count = 1;
		while(count < 12) {
			if(getSection(count) == trainName) {
				break;
			}
			else {
				count++;
				continue;
			}
		}
		if(count == 12) throw new IllegalArgumentException();
		while(i < a.size()) {
			if(a.get(i).getName() == trainName) {
				return a.get(i).getSec();
			}
			else {
				i++;
			}
		}
		return -1;
	}
	
	public void move(String name, int next) {
		int i = 0;
		while(i < a.size()) {
			if(a.get(i).getName() == name) {
				a.get(i).setSec(next);
				break;
			}
			else {
				i++;
			}
		}
	}
}
