package interfaces;

public interface ISubscription {

	void addDuration(int id);
	void updateType(int id);
	void viewSubHis(int id);
	void viewSubStat(int id);
	void cancelSub(int id);
}