import java.util.ArrayList;
/*
 * Performs Quick Sort for any generic list of objects,
 * provided the object's class implements Comparable.
 * Uses Dijkstra's 3 Way partitioning algorithm to improve
 * performance in case of duplicate keys.
 * 
 * @author: Ravi Agrawal
 * @date: Sep 2014
 */
class QuickSort3Way<T extends Comparable<T>> extends QuickSortParent<T>{
    ArrayList<T> lst = null;
	
	/*
     * Constructor for QuickSort.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
    public QuickSort3Way(ArrayList<T> lst){
		if (lst.size() < 2){
            System.out.println("Nothing to sort");
            System.exit(0);
		}
		
		this.lst = new ArrayList<T>(lst);
	}
	
	/*
	 * This method calculates median of three objects.
	 *
	 * @param low: first index of the object list.
	 * @param mid: middle index of the object list.
	 * @param high: last index of the object list.
	 * @return: index of the median object.
	 */
	int medianOfThree(int low, int mid, int high){
		T left = this.lst.get(low), center = this.lst.get(mid), right = this.lst.get(high);
		if (left.compareTo(center) > 0){
			if (left.compareTo(right) < 0)
				return low;
			else if (center.compareTo(right) > 0)
				return mid;
			else
				return high;
		} else if (left.compareTo(right) > 0){
			return low;
		} else if (center.compareTo(right) < 0){
			return mid;
		} else
			return high;
	}
	
	/*
	 * This finds the pivot element's index
	 *
	 * @param low: first index of the object list.
	 * @param mid: middle index of the object list.
	 * @param high: last index of the object list.
	 * @return: index of the pivot element.
	 */
	int findPivot(int low, int mid, int high){
		// For small lists, use the median of arguments as pivot
		if ((high - low) < 40){
			int median = medianOfThree(low, mid, high);
			return median;
		}
		
		// For larger arrays, find pivot by Tukey's ninther,
		// also known as "median of medians"
		int offset = (high - low) / 8;
		int firstMedian = medianOfThree(low, low + offset, low + (2 * offset));
		int secondMedian = medianOfThree(mid - offset, mid, mid + offset);
		int thirdMedian = medianOfThree(mid + (2 * offset), high - offset, high);
		int ninther = medianOfThree(firstMedian, secondMedian, thirdMedian);
		return ninther;
	}

    /*
     * This method sorts the object list using Quick Sort with 
	 * Dijkstra's 3-Way partitioning algorithm.
     * 
	 * @param low: first index of the list.
	 * @param high: last index of the list.
     */
    public ArrayList<T> sort(int low, int high){
		int lt = low, gt = high, mid = (high - low) / 2;
		
		// Find the pivot object's index
		int pivotIndex = findPivot(low, mid, high);
		
		// Swap element at 'low' with pivot
		T pivot = this.lst.get(pivotIndex);
		this.lst.set(pivotIndex, this.lst.get(low));
		this.lst.set(low, pivot);

		// Position the pivot element(s) correctly
		for (int i = low; i <= gt; i++){
			T value = this.lst.get(i);
			
			// Simply increment 'i' if this.lst[i] == this.lst[lt];
			if (value.compareTo(this.lst.get(lt)) == 0)
				continue;

			// Swap 'lt' with 'i' if this.lst[i] < this.lst[lt]
			if(value.compareTo(this.lst.get(lt)) < 0){
				this.lst.set(lt++, value);
				this.lst.set(i, pivot);
				continue;
			}
			
			// Swap 'i' with 'gt' as this.lst[i] > this.lst[lt]
			this.lst.set(i--, this.lst.get(gt));
			this.lst.set(gt--, value);
		}
		
		// Partition the list
		if (low < (lt - 1))
			this.lst = sort(low, lt - 1);
		if (gt < (high - 1))
			this.lst = sort(gt + 1, high);

		return this.lst;
	}
}