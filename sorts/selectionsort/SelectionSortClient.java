import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*
 * Performs Selection Sort for any generic list of objects,
 * provided the object's class implements Comparable.
 * 
 * @author: Ravi Agrawal
 * @date: August 2014
 */
class SelectionSort<T extends Comparable<T>>{
    /*
     * Constructor for SelectionSort.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
    public SelectionSort(ArrayList<T> lst){
       if (lst.size() < 2){
            System.out.println("Nothing to sort");
            System.exit(0);
       }
       sort(lst);
    }

    /*
     * This method sorts a list of objects.
     * The objects' class passed must implement Comparable.
     * 
     * @param lst: A generic type ArrayList.
     */
    public void sort(ArrayList<T> lst){
        for (int i = 0; i < lst.size(); i++){
            T min = lst.get(i);
            int minIndex = i;
            // Find the minimum from remaining list
            for (int j = i; j < lst.size(); j++){
                if (min.compareTo(lst.get(j)) > 0){
                    min = lst.get(j);
                    minIndex = j;
                }
            }
            // Swap the ith position with the minimum found
            T iCopy = lst.get(i);
            lst.set(i, lst.get(minIndex));
            lst.set(minIndex, iCopy);
        }
    }
}

/*
 * A dummy class used to test Selection Sort for a
 * user defined class.
 * 
 * @author: Ravi Agrawal
 */
class DummyClass implements Comparable<DummyClass>{
    int dummyInt;
    float dummyFloat;
    
    public DummyClass(int dummyInt, float dummyFloat){
        this.dummyInt = dummyInt;
        this.dummyFloat = dummyFloat;
    }
    
    public int getInt(){
        return this.dummyInt;
    }
    
    public float getFloat(){
        return this.dummyFloat;
    }
    
    public int compareTo(DummyClass that){
        float myProduct = this.dummyInt * this.dummyFloat;
        float theirProduct = that.dummyInt * that.dummyFloat;
        if (myProduct > theirProduct)
            return 1;
        else if (theirProduct > myProduct)
            return -1;
        else
            return 0;
    }
}

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
        for (int i = 0; i < lst.size(); i++){
            System.out.println(lst.get(i).getInt() + ", " + 
                               lst.get(i).getFloat());
        }
        SelectionSort<DummyClass> sortObj = new SelectionSort<DummyClass>(lst);
        System.out.println("\nCustom list after sorting (pair product):");
        for (int i = 0; i < lst.size(); i++){
            System.out.println(lst.get(i).getInt() + ", " + 
                               lst.get(i).getFloat());
        }
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