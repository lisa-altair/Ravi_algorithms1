import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;
import java.lang.Thread;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Client code for testing the performance of various sorts.
 * The objects of a custom class called "DummyClass" are sorted.
 * DummyClass has two fields: DummyInt & DummyFloat.
 * The list of DummyClass' objects are sorted by the 
 * product of DummyInt & DummyFloat.
 * 
 * @author: Ravi Agrawal
 * @last modified: Sep 2014
 * 
 */
public class SortingClient<T extends Comparable<T>> implements Runnable{
	char sortToRun;
	// Enable this to generate list with duplicate values
	static boolean duplicates = true;
	double javaExecTime;
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
					System.out.println("Insertion Sort execution time: "+ execTime + " secs");
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
					System.out.println("Selection Sort execution time: "+ execTime + " secs");
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
					System.out.println("Merge Sort execution time: "+ execTime + " secs");
				} else {
					System.err.println("The list did not sort correctly.");
					System.exit(1);
				}
				break;
			case 'Q':
				// Take intrinsic lock on 'System' class
				// to prevent thread interference in timing measurements.
				synchronized(System.class){
					QuickSort<T> quickObj = new QuickSort<T>(this.lst);
					int low = 0, high = (this.lst.size() - 1);
					startTime = System.nanoTime();
					this.lst = quickObj.sort(low, high);
					endTime = System.nanoTime();
				}
				execTime = (endTime - startTime) / (10e9);
				if (this.lst != null) {
					// Check that the sort was performed correctly
					if (this.checkResult(this.lst)){
						System.out.println("Quick Sort execution time: "+ execTime + " secs");
					} else {
						System.err.println("The list did not sort correctly.");
						System.exit(1);
					}
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
		
		System.out.println("Java Sort execution time: "+ execTime + " secs");
			
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
			if (threadI != null)
				threadI.join();
			if (threadS != null)
				threadS.join();
			if (threadM != null)
				threadM.join();
			if (threadQ != null)
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
		ArrayList<Integer> lst = new ArrayList<Integer>();
		Random r = new Random();

		// Create a list of Integer objects with pseudo-random values
		if (duplicates){
			for (int i = 0; i < limit; i++){
				Integer newObj = new Integer(r.nextInt(100));
				lst.add(newObj);
			}
		} else {
			for (int i = 0; i < limit; i++){
				Integer newObj = new Integer(r.nextInt(limit + 100));
				lst.add(newObj);
			}
		}
		System.out.println("\nSorting a list of Integer objects:");
		sortMain(sortsToRun, lst);	
	}

	/*
	 * This method generates a list of "DummyClass" objects
	 * and calls the sortMain() method on it.
	 *
	 * @param sortsToRun: A String used to determine which sorts to run.
	 */
	public static void sortCustom(String sortsToRun){
		ArrayList<DummyClass> lst = new ArrayList<DummyClass>();
		Random r = new Random();
		
		// Create a list of "DummyClass" objects with pseudo-random values
		if (duplicates){
			for (int i = 0; i < limit; i++){
				DummyClass newObj = new DummyClass(r.nextInt(100), (float)r.nextInt(100));
				lst.add(newObj);
			}
		} else {
			for (int i = 0; i < limit; i++){
				DummyClass newObj = new DummyClass(r.nextInt(), r.nextFloat());
				lst.add(newObj);
			}
		}
		System.out.println("\nSorting a list of custom class \"DummyClass\" objects:");
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
		
		// Parse 'sorts.configure'
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("sort.configure"));
			String line = null;

			while ((line = reader.readLine()) != null){
				String[] content = line.replaceAll("\\s+", "").split("=");
				// Check if duplicates are allowed
				if (content[0].equals("DUPLICATES_ALLOWED")){
					duplicates = Boolean.parseBoolean(content[1]);
					break;
				}
			}

			// Close the input stream
			if (reader != null)
				reader.close();
		} catch (FileNotFoundException e){
			System.err.println("File sort.configure not found");
			System.exit(1);
		} catch(IOException e){
			System.err.println(e);
		}
		
		// Sort an 'Integer' class' objects
		sortInteger(sortsToRun);
		
		// Sort a custom class' objects
		sortCustom(sortsToRun);
    }
}