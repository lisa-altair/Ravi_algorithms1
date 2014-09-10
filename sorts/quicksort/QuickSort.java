import java.util.ArrayList;
/*
 * Performs Quick Sort for any generic list of objects,
 * provided the object's class implements Comparable.
 * 
 * @author: Ravi Agrawal
 * @date: Sep 2014
 */
class QuickSort<T extends Comparable<T>>{
    /*
     * Constructor for QuickSort.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
    public QuickSort(ArrayList<T> lst){
		if (lst.size() < 2){
            System.out.println("Nothing to sort");
            System.exit(0);
		}
		
		sort(lst, 0, (lst.size() - 1));
	}
	
	/*
	 * This method calculates median of three objects.
	 *
	 * @param lst: A generic type ArrayList.
	 * @param low: first index of the object list.
	 * @param mid: middle index of the object list.
	 * @param high: last index of the object list.
	 * @return: index of the median object.
	 */
	int medianOfThree(ArrayList<T> lst, int low, int mid, int high){
		T left = lst.get(low), center = lst.get(mid), right = lst.get(high);
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
	 * @param lst: A generic type ArrayList.
	 * @param low: first index of the object list.
	 * @param mid: middle index of the object list.
	 * @param high: last index of the object list.
	 * @return: index of the pivot element.
	 */
	int findPivot(ArrayList<T> lst, int low, int mid, int high){
		// For small lists, use the median of arguments as pivot
		if ((high - low) < 40){
			int median = medianOfThree(lst, low, mid, high);
			return median;
		}
		
		// For larger arrays, find pivot by Tukey's ninther,
		// also known as "median of medians"
		int offset = (high - low) / 8;
		int firstMedian = medianOfThree(lst, low, low + offset, low + (2 * offset));
		int secondMedian = medianOfThree(lst, mid - offset, mid, mid + offset);
		int thirdMedian = medianOfThree(lst, mid + (2 * offset), high - offset, high);
		int ninther = medianOfThree(lst, firstMedian, secondMedian, thirdMedian);
		return ninther;
	}
	
	/*
	 * This method performs the 'partition' operation of quick sort.
	 *
	 * @param lst: A generic type ArrayList to be sorted.
	 * @param low: first index of the object list.
	 * @param high: last index of the object list.
	 * @return: index of the pivot element.
	 */
	int partition(ArrayList<T> lst, int low, int high){
		int i = low, j = high + 1, mid = (high - low) / 2;
		
		// Find the pivot object's index
		int pivotIndex = findPivot(lst, low, mid, high);
		
		// Swap element at 'low' with pivot
		T pivot = lst.get(pivotIndex);
		lst.set(pivotIndex, lst.get(low));
		lst.set(low, pivot);
		
		// Partition the list by pivot
		while (true){
			// Move the 'i'th pointer until an element greater than pivot
			while ((lst.get(++i).compareTo(pivot) <= 0) && (j >= i));
			
			// Move the 'j'th pointer until an element less than pivot
			while ((lst.get(--j).compareTo(pivot) >= 0) && (j >= i));
			
			// Pointers crossed over, break out of loop.
			if (j < i)
				break;
			
			// Swap 'i'th and 'j'th elements
			T jCopy = lst.get(j);
			lst.set(j, lst.get(i));
			lst.set(i, jCopy);
		}
		
		//Swap pivot (@ 'low') with 'j', it's correct position
		lst.set(low, lst.get(j));
		lst.set(j, pivot);

		return j;
	}
	
    /*
     * This method recursively partitions the object list via pivot,
	 * which is put in it's correct position by the partition method.
     * 
     * @param lst: A generic type ArrayList to be sorted.
	 * @param low: first index of the list.
	 * @param high: last index of the list.
     */
    public void sort(ArrayList<T> lst, int low, int high){
		int divider = partition(lst, low, high);
		if (low < (divider - 1)){
			sort(lst, low, divider - 1);
		}
		if (divider < (high - 1)){
			sort(lst, divider + 1, high);
		}
    }
}