import java.util.Random;

import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.control.*; 
import javafx.scene.Scene; 
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.GridPane; 
import javafx.scene.layout.Pane; 
import javafx.scene.text.Text; 
import javafx.stage.Stage; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Rectangle;

public class BinaryInsertionSort extends Application{

//Uses a combination of binary search and insertion sort to sort an array effeciently
//then creates a visual display to show the effects of the array
static Rectangle[] boxGraph;
static int[] test;
static int[] arrayTestTemp;

static int numbers = 20;
int sceneWidth = 315;
int sceneHeight = 315;
int graphBase = 250;
int barOffSet = 15;
static int intersectionCounter = 0;
Color selectedColor = Color.RED;
Color unselectedColor = Color.GRAY;
Pane pane;
static Text[] text;

public static void main(String[] args){
    text = new Text[numbers];
    for (int i = 0; i < text.length; i++)
        text[i] = new Text("");

    /*int[] nums = new int[] { 90, 1, 5 ,8, 2 ,12, 32, 86 , 0, 2};

    for(int i = 0; i < nums.length; i++)
        insertionSort(nums);
    printArray(nums);*/
    launch(args);
}
@Override
public void start(Stage primaryStage){

    pane = new Pane();
    Random rndm = new Random();
    arrayTestTemp = new int[numbers];
    test = new int[numbers];
    boxGraph = new Rectangle[numbers];

    //Fills arrays with numbers and boxes for those numbers
    for(int i = 0; i < boxGraph.length; i++){
        test[i] = rndm.nextInt(20) + 1;
        arrayTestTemp[i] = test[i];
        boxGraph[i] = new Rectangle(10, test[i]*10);
        text[i].setText(test[i] + "");

        boxGraph[i].setFill(Color.RED);
        boxGraph[i].setY(graphBase - boxGraph[i].getHeight());
        boxGraph[i].setX(barOffSet*(i+1));

        text[i].setY(graphBase - boxGraph[i].getHeight()-10);
        text[i].setX(barOffSet*(i+1));

        pane.getChildren().addAll(boxGraph[i], text[i]);
    }

    printArray(test);

    //Creates a step button to slowly itterate through insertion sort using binary search
    Button step = new Button("Step");
    step.setLayoutX(sceneWidth/2 - 30);
    step.setLayoutY(sceneHeight - 30);
    step.setOnAction(e -> {
                insertionSort(test);
                //printArray(test);
                for(int i = 0; i < boxGraph.length; i++) {
                    boxGraph[i].setHeight(test[i] * 10);
                    boxGraph[i].setY(graphBase - boxGraph[i].getHeight());

                    text[i].setText(test[i] + "");
                    text[i].setY(graphBase - boxGraph[i].getHeight()-10);
                    text[i].setX(barOffSet*(i+1));
                }
        intersectionCounter++;
    });
    pane.getChildren().add(step);

    //Creates reset button
    Button reset = new Button("Reset");
    reset.setLayoutX(sceneWidth/2 + 20);
    reset.setLayoutY(sceneHeight - 30);
    reset.setOnAction(e -> {
        refillArray();
        for(int i = 0; i < boxGraph.length; i++) {
            boxGraph[i].setHeight(test[i] * 10);
            boxGraph[i].setY(graphBase - boxGraph[i].getHeight());

            text[i].setText(test[i] + "");
            text[i].setY(graphBase - boxGraph[i].getHeight()-10);
            text[i].setX(barOffSet*(i+1));
        }

        intersectionCounter = 0;
    });
    pane.getChildren().add(reset);

    // Create a scene and place it in the stage
    Scene scene = new Scene(pane, sceneWidth, sceneHeight);
    primaryStage.setTitle("Insertion Sort"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
}

//Sorts array using insertion sort
public static void insertionSort(int array[]) {
    int i = intersectionCounter;


    if ( i < array.length) {
        int pointer = array[i];
        int previous = i - 1;

        int targetIndex = binarySearch(array, 0, i , pointer);

        if(targetIndex == -1){
            System.out.print("Number not found\n");
        }
        else {
            int tempTarget = array[i];
            int c = targetIndex;
            int shiftCounter = i;

            while (shiftCounter > c) {
                array[shiftCounter] = array[shiftCounter-1];
                shiftCounter--;
            }
            array[targetIndex] = tempTarget;
        }
        //intersectionCounter++;
    }
    else
        printArray(array);
}

public static int binarySearch(int[] array, int lower, int higher, int target) {
    int mid = lower + (higher - lower) / 2;

    if (higher>lower) {
            if (array[mid] < target)
                return binarySearch(array, mid + 1, higher, target);
            else if (array[mid] > target)
                return binarySearch(array, lower, mid , target);
            else
                return mid;
    }
    else
        return mid;
}

//prints out in full, any integer array in full
public static void printArray (int[] array){
    for(int i = 0; i < array.length; i++){
        System.out.print(array[i] + "\t");
    }
    System.out.println("");
}

public static void updateRectangles (Rectangle[] boxes){
    for(int i = 0; i < boxGraph.length; i++){
        boxGraph[i] = new Rectangle(10, test[i]*10);
        boxGraph[i].setY(225 - boxGraph[i].getHeight());
    }
}

public static void refillArray(){
    Random rndm = new Random();
    for ( int i = 0; i < test.length; i++){
        test[i] = rndm.nextInt(20);
    }
}
public static void refreshArray(){
    for ( int i = 0; i < test.length; i++){
        test[i] = arrayTestTemp[i];
    }
}
}