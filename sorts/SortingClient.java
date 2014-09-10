import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;
import java.lang.Thread;

/*
 * Client code for testing the performance of various sorts.
 * The objects of a custom class called "DummyClass" are sorted.
 * DummyClass has two fields: DummyInt & DummyFloat.
 * The list of DummyClass' objects are sorted by the 
 * product of DummyInt & DummyFloat.
 * 
 * @author: Ravi Agrawal
 * @date: Aug 2014
 * 
 */
public class SortingClient<T extends Comparable<T>> implements Runnable{
	char sortToRun;
	double javaExecTime;
	static String objectType = null;
	ArrayList<T> lst = null;
	ArrayList<T> sortedList = null;
	static int limit = 10000; // Default number of objects to sort
	
    /*
     * This method displays any generic type list.
     * 
     * @param lst: ArrayList of generic type.
     */
    private void display(ArrayList<T> lst){
        for (int i = 0; i < lst.size(); i++){
            System.out.print(lst.get(i) + " ");
        }
        System.out.println();
    }
    
	/*
	 * This method checks that the list was sorted correctly.
	 * 
	 * @param lst: list sorted by custom sorting implementation.
	 */
    private boolean checkResult(ArrayList<T> lst){
		for (int i = 0; i < lst.size(); i++){
			T lstObj = lst.get(i);
			T sortedLstObj = this.sortedList.get(i);
			
			if (lstObj.compareTo(sortedLstObj) != 0){
				return false;
			}
		}
		return true;
	}
	
	/*
	 * This method is executed by each thread that is created inside 'sortMain()'.
	 * Each thread performs a particular sort on a pre-generated list.
	 */
	public void run(){
        long startTime, endTime;
		double execTime;
		
		switch (sortToRun) {
			case 'I':
				// Take intrinsic lock on 'System' class
				// to prevent thread interference in timing measurements.
				synchronized(System.class){			
					startTime = System.nanoTime();
					InsertionSort<T> insertObj = new InsertionSort<T>(this.lst);
					endTime = System.nanoTime();
				}
				execTime = (endTime - startTime) / (10e9);

				// Check that the sort was performed correctly
				if (this.checkResult(this.lst)){
					System.out.println("Insertion Sort execution time for " + 
						objectType + " objects: " + execTime + " secs");
				} else {
					System.err.println("The list did not sort in natural order.");
					System.exit(1);
				}
				break;
			
			case 'S':
				// Take intrinsic lock on 'System' class
				// to prevent thread interference in timing measurements.
				synchronized(System.class){
					startTime = System.nanoTime();
					SelectionSort<T> selObj = new SelectionSort<T>(this.lst);
					endTime = System.nanoTime();
				}
				execTime = (endTime - startTime) / (10e9);

				// Check that the sort was performed correctly
				if (this.checkResult(this.lst)){
					System.out.println("Selection Sort execution time for " + 
						objectType + " objects: " + execTime + " secs");
				} else {
					System.err.println("The list did not sort correctly.");
					System.exit(1);
				}
				break;
			case 'M':
				// Take intrinsic lock on 'System' class
				// to prevent thread interference in timing measurements.
				synchronized(System.class){
					startTime = System.nanoTime();
					MergeSort<T> mergeObj = new MergeSort<T>(this.lst);
					endTime = System.nanoTime();
				}
				execTime = (endTime - startTime) / (10e9);
				
				// Check that the sort was performed correctly
				if (this.checkResult(this.lst)){
					System.out.println("Merge Sort execution time for " + 
						objectType + " objects: " + execTime + " secs");
				} else {
					System.err.println("The list did not sort correctly.");
					System.exit(1);
				}
				break;
			case 'Q':
				// Take intrinsic lock on 'System' class
				// to prevent thread interference in timing measurements.
				synchronized(System.class){
					startTime = System.nanoTime();
					QuickSort<T> mergeObj = new QuickSort<T>(this.lst);
					endTime = System.nanoTime();
				}
				execTime = (endTime - startTime) / (10e9);
				
				// Check that the sort was performed correctly
				if (this.checkResult(this.lst)){
					System.out.println("Quick Sort execution time for " + 
						objectType + " objects: " + execTime + " secs");
				} else {
					System.err.println("The list did not sort correctly.");
					System.exit(1);
				}
				break;

			default:
					System.err.println("Invalid value of 'sortToRun'");
					System.exit(1);
		}
		return;
	}
	
	/*
	 * This method calls the various sorting functions on a pre-generated list of objects.
	 *
	 * @param sortsToRun: A String used to determine which sorts to run.
	 */
	private static <T extends Comparable<T>> void sortMain(String sortsToRun, ArrayList<T> lst){
        long startTime, endTime;
		double execTime;
		
		// Create a copy of 'lst'
		ArrayList<T> lstCopy = new ArrayList<T>(lst);
		
		// Sort the list using Java's sorting implementation
		startTime = System.nanoTime();
		Collections.sort(lstCopy);
		endTime = System.nanoTime();
		execTime = (endTime - startTime) / (10e9);
		
		System.out.println("Java Sort execution time for " + objectType + 
			" objects: " + execTime + " secs");
			
		// Create separate threads for each sort
		Thread threadI = null, threadS = null, threadM = null, threadQ = null;
		if (sortsToRun.contains("I")){
			SortingClient<T> insertionSortObj = new SortingClient<T>();
			insertionSortObj.lst = new ArrayList<T>(lst);
			insertionSortObj.sortedList = new ArrayList<T>(lstCopy);
			insertionSortObj.sortToRun = 'I';
			threadI = new Thread(insertionSortObj);
			threadI.start();
		}
		
		if (sortsToRun.contains("S")){
			SortingClient<T> selectionSortObj = new SortingClient<T>();
			selectionSortObj.lst = new ArrayList<T>(lst);
			selectionSortObj.sortedList = new ArrayList<T>(lstCopy);
			selectionSortObj.sortToRun = 'S';
			threadS = new Thread(selectionSortObj);
			threadS.start();
		}

		if (sortsToRun.contains("M")){
			SortingClient<T> mergeSortObj = new SortingClient<T>();
			mergeSortObj.lst = new ArrayList<T>(lst);
			mergeSortObj.sortedList = new ArrayList<T>(lstCopy);
			mergeSortObj.sortToRun = 'M';
			threadM = new Thread(mergeSortObj);
			threadM.start();
		}

		if (sortsToRun.contains("Q")){
			SortingClient<T> quickSortObj = new SortingClient<T>();
			quickSortObj.lst = new ArrayList<T>(lst);
			quickSortObj.sortedList = new ArrayList<T>(lstCopy);
			quickSortObj.sortToRun = 'Q';
			threadQ = new Thread(quickSortObj);
			threadQ.start();
		}
		try {
			threadI.join();
			threadS.join();
			threadM.join();
			threadQ.join();
		}catch (InterruptedException e){
			return;
		}
	}

	/*
	 * This method generates a list of Integer objects
	 * and calls the sortMain() method on it.
	 *
	 * @param sortsToRun: A String used to determine which sorts to run.
	 */
	public static void sortInteger(String sortsToRun){
		objectType = "Integer";
		ArrayList<Integer> lst = new ArrayList<Integer>();
		Random r = new Random();

		// Create a list of Integer objects with pseudo-random values
		for (int i = 0; i < limit; i++){
			Integer newObj = new Integer(r.nextInt(limit + 100));
			lst.add(newObj);
		}
		
		sortMain(sortsToRun, lst);	
	}

	/*
	 * This method generates a list of "DummyClass" objects
	 * and calls the sortMain() method on it.
	 *
	 * @param sortsToRun: A String used to determine which sorts to run.
	 */
	public static void sortCustom(String sortsToRun){
		objectType = "DummyClass";
		ArrayList<DummyClass> lst = new ArrayList<DummyClass>();
		Random r = new Random();
		
		// Create a list of "DummyClass" objects with pseudo-random values
		for (int i = 0; i < limit; i++){
			DummyClass newObj = new DummyClass(r.nextInt(), r.nextFloat());
			lst.add(newObj);
		}
		sortMain(sortsToRun, lst);
	}
 
	public static void main(String args[]){
		String sortsToRun = new String();
        Scanner s = new Scanner(args[0]);
		
		// Parse input arguments
		if ((args.length) < 1 || ((args.length == 1) && s.hasNextInt())){
			System.err.println("Missing input argument 'sort string'.\nFormat: java SortingClient <N> <sort string>\n" + 
				"Where 'N': Number of objects to sort(optional) &\n'sort string': A string representing the first letters" +
				" of the sorts to run.\ne.g - For running Insertion & Selection Sorts, sort string = IS or ST");
			System.exit(1);
        }
		
		if (args.length > 1) {
			if (s.hasNextInt())
				limit = s.nextInt();
			
			sortsToRun = args[1];
		} else {
			sortsToRun = args[0];
		}
		
		sortsToRun.toUpperCase();
		
		// Sort an 'Integer' class' objects
		sortInteger(sortsToRun);
		
		// Sort a custom class' objects
		sortCustom(sortsToRun);
    }
}