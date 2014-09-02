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
	ArrayList<T> lst = null;
	ArrayList<T> lstCopy = null;
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
			T lstCopyObj = this.lstCopy.get(i);
			
			if (lstObj.compareTo(lstCopyObj) != 0){
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
		
		startTime = System.nanoTime();
		switch (sortToRun) {
			case 'I':
				InsertionSort<T> insertObj = new InsertionSort<T>(this.lst);
				endTime = System.nanoTime();
				execTime = (endTime - startTime) / (10e9);
				
				// Check that the sort was performed correctly
				if (this.checkResult(this.lst)){
					System.out.println("Time taken by Insertion Sort: " + execTime + " secs");
				} else {
					System.err.println("The list did not sort in natural order.");
					System.exit(1);
				}
				break;
			
			case 'S':
				SelectionSort<T> selObj = new SelectionSort<T>(this.lst);
				endTime = System.nanoTime();
				execTime = (endTime - startTime) / (10e9);
				
				// Check that the sort was performed correctly
				if (this.checkResult(this.lst)){
					System.out.println("Time taken by Selection Sort: " + execTime + " secs");
				} else {
					System.err.println("The list did not sort in natural order.");
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
	private void sortMain(String sortsToRun){
        long startTime, endTime;
		double execTime;

		// Sort the list using Java's sorting implementation
		startTime = System.nanoTime();
		Collections.sort(this.lstCopy);
		endTime = System.nanoTime();
		execTime = (endTime - startTime) / (10e9);
		
		System.out.println("Time taken by Java's Sorting function: " + execTime + " secs");	
		
		// Create separate threads for each sort
		if (sortsToRun.contains("I")){
			SortingClient<T> insertionSortObj = new SortingClient<T>();
			insertionSortObj.lst = new ArrayList<T>(this.lst);
			insertionSortObj.lstCopy = new ArrayList<T>(this.lstCopy);
			insertionSortObj.sortToRun = 'I';
			new Thread(insertionSortObj).start();
		}
		
		if (sortsToRun.contains("S")){
			SortingClient<T> selectionSortObj = new SortingClient<T>();
			selectionSortObj.lst = new ArrayList<T>(this.lst);
			selectionSortObj.lstCopy = new ArrayList<T>(this.lstCopy);
			selectionSortObj.sortToRun = 'S';
			new Thread(selectionSortObj).start();
		}
    }
    
	/*
	 * This method generates a list of "DummyClass" objects
	 * and calls the sortMain() method on it.
	 *
	 * @param sortsToRun: A String used to determine which sorts to run.
	 */
	public static void sortCustom(String sortsToRun){
		SortingClient<DummyClass> sortDummy = new SortingClient<DummyClass>();
		sortDummy.lst = new ArrayList<DummyClass>();
		sortDummy.lstCopy = new ArrayList<DummyClass>();
		Random r = new Random();
		
		// Create a list of "DummyClass" objects with pseudo-random values
		for (int i = 0; i < limit; i++){
			DummyClass newObj = new DummyClass(r.nextInt(), r.nextFloat());
			sortDummy.lst.add(newObj);
			sortDummy.lstCopy.add(newObj);
		}
		
		sortDummy.sortMain(sortsToRun);	
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
		
		// Sort a custom class' objects
		sortCustom(sortsToRun);
    }
}