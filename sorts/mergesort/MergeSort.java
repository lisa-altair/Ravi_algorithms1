import java.util.ArrayList;
/*
 * Performs Merge Sort for any generic list of objects,
 * provided the object's class implements Comparable.
 * 
 * @author: Ravi Agrawal
 * @date: Sep 2014
 */
class MergeSort<T extends Comparable<T>>{
    /*
     * Constructor for MergeSort.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
    public MergeSort(ArrayList<T> lst){
		if (lst.size() < 2){
            System.out.println("Nothing to sort");
            System.exit(0);
		}
		sort(lst, 0, (lst.size() - 1));
    }

	/*
	 * This method performs the 'merge' operation of merge sort.
	 *
	 * @param lst: A generic type ArrayList to be sorted.
	 * @param low: first index of the object list to be merged.
	 * @param mid: mid index of the object list to be merged.
	 * @param high: last index of the object list to be merged.
	 */
	void merge(ArrayList<T> lst, int low, int mid, int high){
		int i = low, j = mid + 1, k = low;
		ArrayList<T> mergeHelper = new ArrayList<T>(high - low + 1);
		
		// Copy the list elements to merge
		while (i <= high){
			mergeHelper.add(lst.get(i));
			i++;
		}
		
		// Merge the two halves
		i = low;
		while (k <= high){
			if (j > high)
				lst.set(k++, mergeHelper.get(-low + i++));
			else if (i > mid)
				lst.set(k++, mergeHelper.get(-low + j++));
			else {
				T left = mergeHelper.get(-low + i), right = mergeHelper.get(-low + j);
				if (left.compareTo(right) > 0)
					lst.set(k++, mergeHelper.get(-low + j++));
				else
					lst.set(k++, mergeHelper.get(-low + i++));
			}
		}
	}
	
    /*
     * This method recursively bisects the object list &
	 * calls 'merge' on the halves.
     * 
     * @param lst: A generic type ArrayList.
	 * @param low: first index of the list to be sorted.
	 * @param high: last index of the list to be sorted.
     */
    public void sort(ArrayList<T> lst, int low, int high){
		int mid = (low + high) / 2;
		if (low < high){
			// Sort left & right halves separately
			sort(lst, low, mid);
			sort(lst, mid + 1, high);
			merge(lst, low, mid, high);
		}
    }
}