import java.util.ArrayList;
/*
 * Performs Quick Sort for any generic list of objects,
 * provided the object's class implements Comparable.
 * This is the original, 1 way partition implemenmtation.
 *
 * @author: Ravi Agrawal
 * @date: Sep 2014
 */
class QuickSort1Way<T extends Comparable<T>> extends QuickSortParent<T>{
	ArrayList<T> lst = null;

	/*
     * Constructor for QuickSort.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
    public QuickSort1Way(ArrayList<T> lst){
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
	 * This method performs the 'partition' operation of quick sort.
	 *
	 * @param low: first index of the object list.
	 * @param high: last index of the object list.
	 * @return: index of the pivot element.
	 */
	int partition(int low, int high){
		int i = low, j = high + 1, mid = (high - low) / 2;

		// Find the pivot object's index
		int pivotIndex = findPivot(low, mid, high);
		
		// Swap element at 'low' with pivot
		T pivot = this.lst.get(pivotIndex);
		this.lst.set(pivotIndex, this.lst.get(low));
		this.lst.set(low, pivot);
		
		// Partition the list by pivot
		while (true){
			// Move the 'i'th pointer until an element greater than pivot
			while ((++i < j) && (this.lst.get(i).compareTo(pivot) <= 0));
			
			// Move the 'j'th pointer until an element less than pivot
			while ((--j >= i) && (this.lst.get(j).compareTo(pivot) >= 0));
			
			// Pointers crossed over, break out of loop.
			if (j < i)
				break;
			
			// Swap 'i'th and 'j'th elements
			T jCopy = this.lst.get(j);
			this.lst.set(j, this.lst.get(i));
			this.lst.set(i, jCopy);
		}
		
		//Swap pivot (@ 'low') with 'j', it's correct position
		this.lst.set(low, this.lst.get(j));
		this.lst.set(j, pivot);

		return j;
	}
	
    /*
     * This method recursively partitions the object list via pivot,
	 * which is put in it's correct position by the partition method.
     * 
	 * @param low: first index of the list.
	 * @param high: last index of the list.
     */
	@Override
    public ArrayList<T> sort(int low, int high){
		int divider = partition(low, high);
		if (low < (divider - 1)){
			this.lst = sort(low, divider - 1);
		}
		if (divider < (high - 1)){
			this.lst = sort(divider + 1, high);
		}
		return this.lst;
    }
}