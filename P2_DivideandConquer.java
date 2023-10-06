import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

// Coordinate class to create an Object of associated x & y coordinates
class Coordinate {
	int x, y; // Declaring x & y axis coordinates of the point.

	Coordinate(int x, int y) { // Calling constructor to initialize the x & y coordinates
		this.x = x;
		this.y = y;
	}

	public String toString() { // Calling toString method of Java String to display coordinates in (x, y) form
		return "(" + x + ", " + y + ")";
	}
}

// Divide-and-Conquer project class that calls the main function.
public class P2_DivideandConquer {

	// Create an array of randomly generated x & y coordinates
	public static List<Coordinate> setRandomCoordinates(int n) {
		List<Coordinate> coordinates = new ArrayList<>(); // Declaring a List named coordinates
		Random rand = new Random();

		for (int i = 0; i < n; i++) {
			int x = rand.nextInt(100);  // Generates random x coordinate from 0 to 100
			int y = rand.nextInt(100);  // Generates random y coordinate from 0 to 100
			coordinates.add(new Coordinate(x, y)); // Adding each x & y coordinate to the List
		}

		return coordinates;
	}

	// Merging the independent staircases to one staircase
	static List<Coordinate> mergeStaircases(List<Coordinate> leftHalf, List<Coordinate> rightHalf) {
		if (leftHalf.isEmpty()) return rightHalf; // Returning the leftHalf List if it is an empty list
		if (rightHalf.isEmpty()) return leftHalf; // Returning the rightHalf List if it is an empty list

		int maxYCoord = leftHalf.stream().max(Comparator.comparingInt(p -> p.y)).get().y; // Getting the element with highest y-coordinate
		List<Coordinate> merged = new ArrayList<>(leftHalf);

		for (Coordinate coordinate : rightHalf) {
			if (coordinate.y > maxYCoord) { // Comparing other y-coordinates with max Y Coordinate
				merged.add(coordinate); // Adding optimal coordinates for staircase to merged list
				maxYCoord = Math.max(maxYCoord, coordinate.y); // Overwriting new max Y Coordinate
			}
		}

		return merged;
	}

	// Get pareto-optimal points to create two independent staircases
	static List<Coordinate> paretoOptimal(List<Coordinate> coordinates) {
		if (coordinates.size() <= 1) return coordinates; // Returning the List if it is an empty list

		int mid = coordinates.size() / 2; // Getting the index of middle element of the coordinates list
		List<Coordinate> leftHalfList = paretoOptimal(new ArrayList<>(coordinates.subList(0, mid))); // Dividing the main list by creating a list of left half of the list using pareto-Optimal points
		List<Coordinate> rightHalfList = paretoOptimal(new ArrayList<>(coordinates.subList(mid, coordinates.size()))); // Dividing the main list by creating a list of right half of the list using pareto-Optimal points

		return mergeStaircases(leftHalfList, rightHalfList);
	}

	// main method
	public static void main(String[] args) {
		int n = 100000;
		List<Coordinate> coordinates = setRandomCoordinates(n);

		long startTime = System.nanoTime(); 
		
		// Sorting the List of coordinates using the x-coordinates
		Collections.sort(coordinates, Comparator.comparingInt(p -> p.x));

		// Divide-and-conquer algorithm to create staircase
		List<Coordinate> paretoCoordinates = paretoOptimal(coordinates);
		
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns");
	}
}
