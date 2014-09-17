import java.util.ArrayList;

/*
 * This class acts as a super class to all Quick sort Implementations.
  */
class QuickSortParent<T extends Comparable<T>>{
	public ArrayList<T> sort(int low, int high){
		System.out.println("Wrong call! Called QuickSortParent instead of implementations");
		return (new ArrayList<T>());
	}
}