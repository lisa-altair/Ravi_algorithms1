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