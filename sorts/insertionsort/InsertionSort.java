import java.util.ArrayList;

class InsertionSort<T extends Comparable<T>>{
        /*
     * Constructor for SelectionSort.
     *
     * @param lst: A generic type ArrayList to be sorted.
     */
    public InsertionSort(ArrayList<T> lst){
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
        int sz = lst.size();
        for (int i = 1; i < sz; i++){
                for (int j = i; ((j > 0) && (lst.get(j).compareTo(lst.get(j - 1)) < 0)); j--){
                    // Swap
                    T jCopy = lst.get(j);
                    lst.set(j, lst.get(j - 1));
                    lst.set(j - 1, jCopy);
                }
        }
        
    }
}