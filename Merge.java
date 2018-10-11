/*
 *Credits to jewelsea from https://stackoverflow.com/questions/43009519/javafx-wrong-merge-sort-animation-result
 *and Robert Fisher from https://github.com/RobertNFisher/Algorithm_Analysis_HW_1
 */
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Merge extends Application {
    private static final int N_VALUES = 99; //13
    private static final int SPACING = 11;//60
    private static final int SORT_GROUP_MOVE_DELTA = 1; //200 affects how far out of the screen the bars go 0-400 since screen is set 1080,400

    private static final Duration SPEED = Duration.millis(100);//400

    private int[] helper;
    private StackPane[] helperNodes;
    private Random random = new Random(10);

    @Override
    public void start(Stage stage) throws Exception {
        Pane displayPane = new Pane();
        ArrayList<StackPane> list = new ArrayList<>();
        for (int i = 0; i < N_VALUES; i++) {
            StackPane stackPane = createValueNode(i);
            list.add(stackPane);
        }

        displayPane.getChildren().addAll(list);

        Button sortButton = new Button("Sort");
        sortButton.setOnAction(event -> {
            SequentialTransition sq = new SequentialTransition();
            int[] arr = generateArray(list);
            sq = mergeSort(arr, list, sq);
            sortButton.setDisable(true);
            sq.play();
            sq.setOnFinished(event1 -> sortButton.setDisable(true));
            sortButton.setDisable(true);
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(displayPane);
        borderPane.setBottom(sortButton);
        BorderPane.setAlignment(sortButton, Pos.CENTER);
        BorderPane.setMargin(sortButton, new Insets(10));

        Scene scene = new Scene(borderPane, 1080, 400);
        stage.setTitle("3 Way Merge Sort");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createValueNode(int i) {
        int num = random.nextInt(10);
        Rectangle rectangle = new Rectangle(9, (num * 10) + 50);//40,10,50 rect width, height
        rectangle.setFill(Color.valueOf("#FF7F50"));
        Text text = new Text(String.valueOf(num));
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
        stackPane.setId(String.valueOf(num));
        stackPane.getChildren().addAll(rectangle, text);
        StackPane.setAlignment(text, Pos.TOP_CENTER);
        stackPane.setAlignment(Pos.TOP_CENTER);
        stackPane.setTranslateX(SPACING * i);
        return stackPane;
    }


    private int[] generateArray(List<StackPane> list) {
        int arr[] = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(list.get(i).getId());
        }
        return arr;
    }

    private TranslateTransition move(StackPane sp, int X) {
        TranslateTransition t = new TranslateTransition();
        t.setNode(sp);
        t.setDuration(SPEED);
        t.setToX(X);
        t.setToY(SORT_GROUP_MOVE_DELTA);
        return t;

    }

    public SequentialTransition mergeSort(int arr[], ArrayList<StackPane> list, SequentialTransition sq) {
        int number = arr.length;
        this.helper = new int[number];
        this.helperNodes = new StackPane[number];
        sortRange(0, number - 1, arr, sq, list);
        return sq;
    }

    private void sortRange(int low, int high, int arr[], SequentialTransition sq, ArrayList<StackPane> list) {
        // check if low is smaller then high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the first middle
            int index1 = low + (high - low) / 3;
	        //Get the index of the element which is in the middle
            int index2 = low + 2 * (high - low) / 3; 
            // Sort the left side of the array
            sortRange(low, index1, arr, sq, list);
		    //Sort the middle of the array 
		    sortRange(index1 + 1, index2, arr, sq, list);
            // Sort the right side of the array
            sortRange(index2 + 1, high, arr, sq, list);
            // merge all 3
            merge(low, index1, index2, high, arr, list, sq);
        }
    }


    private void merge(int low, int index1, int index2, int high, int arr[], ArrayList<StackPane> list, SequentialTransition sq) {
        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = arr[i];
            helperNodes[i] = list.get(i);
        }


        int i = low; //left index
        int j = index1 + 1; //middle index
        int r = index2 + 1; //right index
        int k = low; //array index
        
        //select which side is smaller
	    //Case 1: all 3 arrays have remaining numbers
        while (i <= index1 && r <= high && j <= index2) {
            if (helper[i] <= helper[r] && helper[i] <= helper[j]) {
            	//choose left and add to new array
                arr[k] = helper[i];
                list.set(k, helperNodes[i]);
                sq.getChildren().add(move(helperNodes[i], k * SPACING));
                i++;
            }    
            else if (helper[j] <= helper[r] && helper[j] <= helper[i]) {
            	//choose middle /*right*/ and add to new array
                arr[k] = helper[j];
                list.set(k, helperNodes[j]);
                sq.getChildren().add(move(helperNodes[j], k * SPACING));
                j++;
            } 
            else {
            	//choose right
                arr[k] = helper[r];
                list.set(k, helperNodes[r]);
                sq.getChildren().add(move(helperNodes[r], k * SPACING));
                r++;
            }
            k++;
        }
        //Case 2: remaining/ remaining /complete
        while (i <= index1 && j > index2 && r <= high) {
		    if (helper[i] <= helper [r]){
		       //choose left and add to new array
	           arr[k] = helper[i];
		       list.set(k, helperNodes[i]);
	           sq.getChildren().add(move(helperNodes[i], k * SPACING));
		       i++;
	        }
		    else if (helper[r] <= helper [i]){
		       //choose right and add to new array
	           arr[k] = helper[r];
		       list.set(k, helperNodes[r]);
	           sq.getChildren().add(move(helperNodes[r], k * SPACING));
		       r++;
		    }
	            
	           k++;
        }
        //Case 3: remaining/ complete/ remaining
	    while (i <= index1 && j <= index2 && r > high){
	    	if (helper[i] <= helper[j]) { 
	    		//choose left and add to new array
	    		arr[k] = helper[i];
	    		list.set(k, helperNodes[i]);
                sq.getChildren().add(move(helperNodes[i], k * SPACING));
	            i++;
	    	}
	        else if (helper[j] <= helper[i]){
	            arr[k] = helper[j];         
	            //choose right and add to new array
	            list.set(k, helperNodes[j]);
                sq.getChildren().add(move(helperNodes[j], k * SPACING));
	            j++;
	        }
	    	
	        k++;
	    }
	    //Case 4: complete/ remaining/ remaining
	    while (i > index1 && j <= index2 && r <= high){
	        if (helper[j] <= helper[r]) { 
	        	//choose left and add to new array
	            arr[k] = helper[j];
	            list.set(k, helperNodes[j]);
                sq.getChildren().add(move(helperNodes[j], k * SPACING));
	            j++;
	        } 
	        else if (helper[r] <= helper[j]){
	            arr[k] = helper[r];         
	            //choose right and add to new array
	            list.set(k, helperNodes[r]);
                sq.getChildren().add(move(helperNodes[r], k * SPACING));
	            r++;
	        }
	        
	        k++;
	    }
	    //Copy remaining temp array to new array
	    while (i <= index1) {
	        arr[k] = helper[i];
	        list.set(k, helperNodes[i]);
	        sq.getChildren().add(move(helperNodes[i], k * SPACING));
	        i++;
	        k++;
	        
	    }
	    while (j <= index2){
	        arr[k] = helper[j];
	        list.set(k, helperNodes[j]);
	        sq.getChildren().add(move(helperNodes[j], k * SPACING));
	        j++;
	        k++;
	        
	    }

        ParallelTransition moveUp = new ParallelTransition();

        for (int z = low; z <= high; z++) {
            TranslateTransition moveNodeUp = new TranslateTransition();
            moveNodeUp.setNode(helperNodes[z]);
            moveNodeUp.setDuration(SPEED);
            moveNodeUp.setByY(-SORT_GROUP_MOVE_DELTA);
            moveUp.getChildren().add(moveNodeUp);
        }

        sq.getChildren().add(moveUp);
    }

    public static void main(String[] args) {
	   
	    launch(args);
    }    
}