DUMMY_SOURCE = DummyClass.java
CLIENT_SOURCE = SortingClient.java
SELECTION_SOURCE = selectionsort/SelectionSort.java
INSERTION_SOURCE = insertionsort/InsertionSort.java
MERGE_SOURCE = mergesort/MergeSort.java
QUICK_SOURCE_1WAY = quicksort/QuickSort1Way.java
QUICK_SOURCE_3WAY = quicksort/QuickSort3Way.java
QUICK_SOURCES = quicksort/QuickSort.java quicksort/QuickSortParent.java $(QUICK_SOURCE_1WAY) $(QUICK_SOURCE_3WAY)
EXE = SortingClient
SORTS = SIMQ
N = 100000

SOURCES = $(DUMMY_SOURCE) $(CLIENT_SOURCE) $(SELECTION_SOURCE) $(INSERTION_SOURCE) $(MERGE_SOURCE) $(QUICK_SOURCES)

$(EXE): $(SOURCES)
	javac -Xlint -d . $(SOURCES)

insertion_sort: $(EXE) $(SOURCES)
	java $(EXE) $(N) "I"

selection_sort: $(EXE)
	java $(EXE) $(N) "S"

merge_sort: $(EXE)
	java $(EXE) $(N) "M"

quick_sort: $(EXE)
	java $(EXE) $(N) "Q"

run_all: $(EXE)
	java $(EXE) $(N) $(SORTS)
	
clean:
	find -name "*.class" -delete
