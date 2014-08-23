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