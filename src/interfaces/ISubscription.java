package interfaces;

//Class to handle methods in a different class based on Customer ID
public interface ISubscription {

	void addDuration(int id);
	void updateType(int id);
	void viewSubHis(int id);
	void viewSubStat(int id);
	void cancelSub(int id);
}