import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ThreeWayMergeSort{ //extends Application {

	static int[] array; //private
	static int[] tempMergArr; //was private
	static int length; //private
	
	static Rectangle[] boxGraph;
	static int[] test;
	
	static int numbers = 99;
	int sceneWidth = 315;
	int sceneHeight = 315;
	int graphBase = 250;
	int barOffSet = 15;
 	Color selectedColor = Color.RED;
	Color unselectedColor = Color.GRAY;
	Pane pane;
	static Text[] text;

	public static void main(String[] a){
	
	    //Randomly elected numbers, and setup for merge sort
	    Random rndm = new Random();
	    int[] inputArray = new int[numbers];
	    for (int i = 0; i < inputArray.length; i++){
	        inputArray[i] = rndm.nextInt(100);
	    }
	    ThreeWayMergeSort sorter = new ThreeWayMergeSort();
	    sorter.sort(inputArray);
	    for(int i:inputArray){
	        System.out.print(i + " ");
	    }
    //launch(a);
}
	/*@Override
	public void start(Stage primaryStage){
	
	    pane = new Pane();
	  //  Random rndm = new Random();
	    tempMergArr = new int[numbers];
	    test = new int[numbers];
	    boxGraph = new Rectangle[numbers];
	    
	    // Create a scene and place it in the stage
	    Scene scene = new Scene(pane, sceneWidth, sceneHeight);
	    primaryStage.setTitle("3 Way Merge Sort"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	}*/

	//Initializes recursive method for sorting
	public void sort(int inputArray[]) {
	    array = inputArray; //this.array
	    length = inputArray.length; //this.length
	    tempMergArr = new int[length]; //this.tempMergArr
	    merge(0, length - 1);
	}
	
	//splits array into smaller arrays
	private void merge(int lowerIndex, int higherIndex) {
	
	    if (lowerIndex < higherIndex) {
	        int index1 = lowerIndex + (higherIndex - lowerIndex) / 3;
	        int index2 = lowerIndex + 2 * (higherIndex - lowerIndex) / 3;
	        // Sorts the left side of the array
	        merge(lowerIndex, index1);
	        // sorts the middle of the array
	        merge(index1 + 1, index2);
	        // sorts the right side of the array
	        merge(index2 + 1, higherIndex);
	        // Merges both sides
	        merge(lowerIndex, index1, index2, higherIndex);
	    }
	}
	
	private void merge(int lowerIndex, int index1,int index2, int higherIndex) {
	    //create a temporary array for sorting
	    for (int i = lowerIndex; i <= higherIndex; i++) {
	        tempMergArr[i] = array[i];
	    }
	    int leftIndex = lowerIndex;
	    int middleIndex = index1 + 1;
	    int rightIndex = index2 + 1;
	    int arrayIndex = lowerIndex;
	    //Select which side is smaller
	    //Case 1: all 3 arrays have remaining numbers
	    while (leftIndex <= index1 && rightIndex <= higherIndex && middleIndex <= index2) {
	
	        if (tempMergArr[leftIndex] <= tempMergArr[rightIndex]
	                && tempMergArr[leftIndex] <= tempMergArr[middleIndex]) { //choose left and add to new array
	            array[arrayIndex] = tempMergArr[leftIndex];
	            leftIndex++;
	
	        } else if (tempMergArr[middleIndex] <= tempMergArr[rightIndex]
	                && tempMergArr[middleIndex] <= tempMergArr[leftIndex]) {
	            array[arrayIndex] = tempMergArr[middleIndex];         //choose right and add to new array
	            middleIndex++;
	        }
	
	        else {
	            array[arrayIndex] = tempMergArr[rightIndex];
	            rightIndex++;
	        }
	        arrayIndex++;
	    }
	    //Case 2: remaining/ remaining /complete
	    while (leftIndex <= index1 && middleIndex > index2 && rightIndex <= higherIndex){
	
	            if (tempMergArr[leftIndex] <= tempMergArr[rightIndex]) { //choose left and add to new array
	                array[arrayIndex] = tempMergArr[leftIndex];
	                leftIndex++;
	            } else if (tempMergArr[rightIndex] <= tempMergArr[leftIndex]){
	                array[arrayIndex] = tempMergArr[rightIndex];         //choose right and add to new array
	                rightIndex++;
	            }
	            arrayIndex++;
	    }
	
	    //Case 3: remaining/ complete/ remaining
	    while (leftIndex <= index1 && middleIndex <= index2 && rightIndex > higherIndex){
	        if (tempMergArr[leftIndex] <= tempMergArr[middleIndex]) { //choose left and add to new array
	            array[arrayIndex] = tempMergArr[leftIndex];
	            leftIndex++;
	        } else if (tempMergArr[middleIndex] <= tempMergArr[leftIndex]){
	            array[arrayIndex] = tempMergArr[middleIndex];         //choose right and add to new array
	            middleIndex++;
	        }
	        arrayIndex++;
	    }
	    //Case 4: complete/ remaining/ remaining
	    while (leftIndex > index1 && middleIndex <= index2 && rightIndex <= higherIndex){
	        if (tempMergArr[middleIndex] <= tempMergArr[rightIndex]) { //choose left and add to new array
	            array[arrayIndex] = tempMergArr[middleIndex];
	            middleIndex++;
	        } else if (tempMergArr[rightIndex] <= tempMergArr[middleIndex]){
	            array[arrayIndex] = tempMergArr[rightIndex];         //choose right and add to new array
	            rightIndex++;
	        }
	        arrayIndex++;
	    }
	    //Copy remaining temp array to new array
	    while (leftIndex <= index1) {
	        array[arrayIndex] = tempMergArr[leftIndex];
	        arrayIndex++;
	        leftIndex++;
	    }
	    while (middleIndex <= index2){
	        array[arrayIndex] = tempMergArr[middleIndex];
	        arrayIndex++;
	        middleIndex++;
	    }
	
	}

}