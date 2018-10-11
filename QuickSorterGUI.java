
import java.util.Arrays;
import java.util.Random;
import java.awt.event.*;
import java.awt.*;

public class QuickSorterGUI extends Frame implements ActionListener{ 
	TextArea array = new TextArea("The unsorted array is: " + Arrays.toString(test));
	Button b;
	static int[] test = new int[100];
	
	public static void main(String[] args) { 
		Random rndm = new Random();

		//creates a array random
		for (int i = 0; i < test.length; i++) {
			test[i] = rndm.nextInt(1000);
		}

		QuickSorterGUI f = new QuickSorterGUI("QuickSort");
		f.setSize(300, 300);
		f.setVisible(true);
		

	}
	//Used to create the GUI Window
	public QuickSorterGUI(String title) {
		super(title);
		setLayout(new FlowLayout());
		b = new Button("Quick Sort");
		add(b);
		add(array);
		b.addActionListener(this);
	}
	
	//Used when the button is pressed
	public void actionPerformed(ActionEvent e) { 
		quickSort(test, 0, test.length - 1);
		 array.setText("The sorted array is: "+ Arrays.toString(test));
	}
	
//Prints an array
	private static void printArray(int[] list) {
		for (int i = 0; i < list.length; i++) {
			System.out.print(list[i] + "\t");
		}
	}

	//recursively calls itself to sort the array
	private static void quickSort(int[] list, int lower, int higher){
	    if(lower < higher){ //Base Case
	        int index = partition(list, lower, higher);
	        quickSort(list, lower, index-1);
	        quickSort(list, index+1, higher);
	    }
	}
	
	//Runs quick sorting
	private static int partition (int[] list, int lower, int higher){
	    int pivot;
	    int lowerIndex = lower-1;
	
	    //choose pivot
	   if ((higher - lower) < 3)
	        pivot = list[higher];
	   else {
	       int pivotIndex = median(new int[]{list[lower], list[lower + 1], list[lower + 2]}, 0, 2);
	       pivot = list[pivotIndex];
	       swap(list, pivotIndex, higher);
	   }
	
	    //sorts array around the pivot
	    for(int i = lower; i < higher; i++){
	        if(list[i] <= pivot){
	            lowerIndex++;
	            swap(list, lowerIndex, i);
	        }
	    }
	    if(lowerIndex+1 <= higher)
	    swap(list, lowerIndex+1, higher);
	    return lowerIndex+1;
	}
	
	//swaps two position in an array
	private static void swap(int[] list, int posA, int posB){
	    int temp;
	
	    temp = list[posA];
	    list[posA] = list[posB];
	    list[posB] = temp;
	}
	
	//returns the index of the median of the first three of an array
	public static int median(int[] array, int left, int right) {
	    int center = (left + right) / 2;
	    int[] indexTracker = new int[] {left, center, right};
	
	    if (array[left] > array[center]) {
	        swap(array, left, center);
	        swap(indexTracker, left, center);
	    }
	
	    if (array[left] > array[right]) {
	        swap(array, left, right);
	        swap(indexTracker, left, right);
	    }
	
	    if (array[center] > array[right]) {
	        swap(array, center, right);
	        swap(indexTracker, center, right);
	    }
	
	    swap(array, center, right - 1);
	    swap(indexTracker, center, right-1);
	    return indexTracker[right - 1];
	}
}