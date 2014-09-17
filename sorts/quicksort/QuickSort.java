import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Performs Quick Sort for any generic list of objects.
 * Calls various Quick Sort implementations based on
 * parameters in the "sort.configure" file.
 *
 * @author: Ravi Agrawal
 * @date: Sep 2014
 */
class QuickSort<T extends Comparable<T>>{
	static String whichQuickSort = null;
	QuickSortParent<T> quickObj = null;
	
    /*
     * Constructor for QuickSort.
	 * Parses the file "sort.configure" to figure out the
	 * particular Quick sort implementation to run &
	 * initializes it.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
	public QuickSort(ArrayList<T> lst){
		// Read "sort.configure" file to figure out which
		// Quick sort implementation to run
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("sort.configure"));
			String line = null;

			while ((line = reader.readLine()) != null){
				String[] content = line.replaceAll("\\s+", "").split("=");
				if (content[0].equals("QUICKSORT_TO_RUN")){
					whichQuickSort = content[1];
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

		// Compare the various Quick sort implementations
		if (whichQuickSort.equals("all")){
			double startTime = 0.0, endTime = 0.0, execTime1 = 0.0, execTime2 = 0.0;
			int low = 0, high = (lst.size() - 1);
			
			// Test the performance of "QuickSort1Way"
			ArrayList<T> lstCopy = new ArrayList<T>(lst);
			QuickSort1Way<T> quick1 = new QuickSort1Way<T>(lstCopy);
			startTime = System.nanoTime();
			lstCopy = quick1.sort(low, high);
			endTime = System.nanoTime();
			execTime1 = (endTime - startTime) / (10e9);
			
			// Test the performance of "QuickSort3Way"
			lstCopy = new ArrayList<T>(lst);
			low = 0;
			high = (lst.size() - 1);
			QuickSort3Way<T> quick3 = new QuickSort3Way<T>(lstCopy);
			startTime = System.nanoTime();
			lstCopy = quick3.sort(low, high);
			endTime = System.nanoTime();
			execTime2 = (endTime - startTime) / (10e9);

			System.out.println("Time taken by 'QuickSort1Way': " + execTime1 + 
				"\nTime taken by 'QuickSort3Way': " + execTime2);
		}
		
		// Instantiate "QuickSort1Way.java" implementation
		if (whichQuickSort.equals("QuickSort1Way.java"))
			this.quickObj = new QuickSort1Way<T>(lst);

		// Instantiate "QuickSort3Way.java" implementation
		if (whichQuickSort.equals("QuickSort3Way.java"))
			this.quickObj = new QuickSort3Way<T>(lst);
	}
	public ArrayList<T> sort(int low, int high){
		if (this.quickObj != null)
			return this.quickObj.sort(low, high);
		
		// Return null if 'whichQuickSort' was 'all'
		return null;
	}
}