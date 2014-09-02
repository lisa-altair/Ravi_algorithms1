DUMMY_SOURCE = DummyClass.java
CLIENT_SOURCE = SortingClient.java
SELECTION_SOURCE = selectionsort/SelectionSort.java
INSERTION_SOURCE = insertionsort/InsertionSort.java
EXE = SortingClient
SORTS = SI
N = 10000

SOURCES = $(DUMMY_SOURCE) $(CLIENT_SOURCE) $(SELECTION_SOURCE) $(INSERTION_SOURCE)

$(EXE): $(SOURCES)
	javac -Xlint -d . $(SOURCES)

insertion_sort: $(EXE) $(SOURCES)
	java $(EXE) $(N) "I"

selection_sort: $(EXE)
	java $(EXE) $(N) "S"

run_all: $(EXE)
	java $(EXE) $(N) $(SORTS)
	
clean: *.class
	rm *.class
