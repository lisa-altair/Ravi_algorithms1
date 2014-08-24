import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 * Client code for testing Selection Sort.
 * Requires a file with unsorted data as input.
 * This client can test for Integer, Double and
 * String values. In addition, it shows how the
 * Selection Sort implementation can sort a 
 * custom class' objects.
 * 
 * @author: Ravi Agrawal
 * @date: Aug 2014
 * 
 */
public class SelectionSortClient{
    /*
     * This method displays any generic data type list.
     * 
     * @param lst: ArrayList of generic type.
     */
    private static <T> void display(ArrayList<T> lst){
        for (int i = 0; i < lst.size(); i++){
            System.out.print(lst.get(i) + " ");
        }
        System.out.println();
    }
    
    /*
     * The following three methods are used to test
     * Integer, Double & String data sort operations
     * respectively.
     * 
     * @param: s: A Scanner object for the input data file.
     */
    private static void sortInt(Scanner s){
        ArrayList<Integer> lst = new ArrayList<Integer>();
        while (s.hasNextInt())
            lst.add(s.nextInt());
        
        System.out.println("List before sorting:");
        display(lst);
        SelectionSort<Integer> sortObj = new SelectionSort<Integer>(lst);
        System.out.println("List after sorting:");
        display(lst);
    }
    
    private static void sortDouble(Scanner s){
        ArrayList<Double> lst = new ArrayList<Double>();
        while (s.hasNextDouble())
            lst.add(s.nextDouble());
        
        System.out.println("List before sorting:");
        display(lst);
        SelectionSort<Double> sortObj = new SelectionSort<Double>(lst);
        System.out.println("List after sorting:");
        display(lst);
    }
    
    private static void sortString(Scanner s){
        ArrayList<String> lst = new ArrayList<String>();
        while (s.hasNext())
            lst.add(s.next());
        
        System.out.println("List before sorting:");
        display(lst);
        SelectionSort<String> sortObj = new SelectionSort<String>(lst);
        System.out.println("List after sorting:");
        display(lst);
    }
    
    /*
     * This method tests the Selection sort
     * implementation for a user-defined data set.
     */
    private static void sortDummy(){
        
        // Create a list of DummyClass objects
        ArrayList<DummyClass> lst = new ArrayList<DummyClass>();
        lst.add(new DummyClass(5, 1.0f));
        lst.add(new DummyClass(2, 2.0f));
        lst.add(new DummyClass(3, 1.8f));

        System.out.println("\nCustom list before sorting:");
        display(lst);
        SelectionSort<DummyClass> sortObj = new SelectionSort<DummyClass>(lst);
        System.out.println("\nCustom list after sorting (pair product):");
        display(lst);
    }
    
    public static void main(String args[]){
        if (args.length < 1){
            System.err.println("Enter the file's name which is to be sorted");
            System.exit(1);
        }

        String fileToSort = args[0];
        Scanner s = null;
        try{
            s = new Scanner(new File(fileToSort));
        } catch(FileNotFoundException e){
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
        // Call Selection Sort for either Integer, Double or String type data
        if (s.hasNextInt()){
            sortInt(s);
        }
        if (s.hasNextDouble()){
            sortDouble(s);
        }
        if (s.hasNext()){
            sortString(s);
        }
        // Call Selection Sort on DummyClass's object list
        sortDummy();
    }
}