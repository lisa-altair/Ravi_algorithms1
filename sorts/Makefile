DUMMY_SOURCE = DummyClass.java
CLIENT_SOURCE = SortingClient.java
SELECTION_SOURCE = selectionsort/SelectionSort.java
INSERTION_SOURCE = insertionsort/InsertionSort.java
MERGE_SOURCE = mergesort/MergeSort.java
EXE = SortingClient
SORTS = SIM
N = 100000

SOURCES = $(DUMMY_SOURCE) $(CLIENT_SOURCE) $(SELECTION_SOURCE) $(INSERTION_SOURCE) $(MERGE_SOURCE)

$(EXE): $(SOURCES)
	javac -Xlint -d . $(SOURCES)

insertion_sort: $(EXE) $(SOURCES)
	java $(EXE) $(N) "I"

selection_sort: $(EXE)
	java $(EXE) $(N) "S"

merge_sort: $(EXE)
	java $(EXE) $(N) "M"

run_all: $(EXE)
	java $(EXE) $(N) $(SORTS)
	
clean:
	find -name "*.class" -delete
