/**
 * File: quicksort.c
 * implements simple quicksort using valid C- tokens 
 */
void quicksort(int array[], int lowIndex, int highIndex) {
  if (lowIndex < highIndex) {
    int pivot;
    pivot = partition(array, lowIndex, highIndex);
    quicksort(array, lowIndex, pivot-1);
    quicksort(array, pivot+1, highIndex);
  }
}