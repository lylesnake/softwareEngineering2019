import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TravellingSalesmanProblem {

	public static void main(String[] args) {
		// set up our variables.
		int cityCount = 5;  //enter the amount of cities
		int[] pathArray = new int[cityCount]; 
		int[] bestPathArray = new int[cityCount]; 
		int[][] distanceMatrix = new int[cityCount][cityCount]; 
		int currentPathDistance = 0; 
		int bestDistance = Integer.MAX_VALUE; //smaller is best, so start with biggest possible number

		//begin problem
		fillDistanceMatrix(distanceMatrix); //step 1: fill the matrix w/ random numbers		
		for(int passIndex = 1; passIndex < 1000000; passIndex++) {
			resetPathArray(pathArray); //step 2: reset the variable "path array" 
			pathArrayGenerator(pathArray); // step 3: generate a random path (guess)
			currentPathDistance = getCurrentPathDistance(pathArray, distanceMatrix); //step 4: compute the length of the path
			if(currentPathDistance < bestDistance) { //step 5: print the current path with this dialogue if it isn't the best path
				bestDistance = currentPathDistance;
				storeBestPathArray(pathArray,bestPathArray); //5a: hand over the number stored by pathArray to bestPathArray
				printPathArray(pathArray); //5b: print the pathway 
				System.out.println("Path Distance is " + currentPathDistance);
			} //end if
		} //end for loop
		System.out.println("Best distance: " + bestDistance); //print the final guess length, hopefully the best guess
		System.out.print("Best path: "); //print best path directions
		printPathArray(bestPathArray); 
		printToCSVFile(distanceMatrix, cityCount); //6: print to CSV

	} //end main


	private static void fillDistanceMatrix(int[][] distanceMatrix) {
		//step 1: fill the matrix with random numbers
		for(int row = 0; row < distanceMatrix.length; row++) {
			for(int column = 0; column < distanceMatrix.length; column++) {
				if ( row == column){
					distanceMatrix[row][column] = 404;					
				}
				else {
					int randomDistance = (int)(Math.random()* + 100);
					distanceMatrix[row][column] = randomDistance;
				}
				System.out.print (distanceMatrix[row][column] + "\t"); 
			}
			System.out.println();
		} 

	}//end step 1; continue to step 2 


	private static void resetPathArray(int[] pathArray) {
		//step 2: prepare to guess another path
		for(int i =0; i<pathArray.length; i++) {
			pathArray[i] = 0;
		}

	}//end step 2; continue to step 3


	private static void pathArrayGenerator(int[] pathArray) {
		//Step 3: pick a random path
		int nextCity;
		for(int i = 0; i<pathArray.length; i++) {
			nextCity = getCityNotInArrayYet(pathArray); //3a: ensure no cities are revisited
			pathArray[i] = nextCity;
		}
	}//end step 3; continue to step 4


	private static int getCityNotInArrayYet(int[] pathArray) {
		//3a: ensure no cities are revisited
		boolean haveGoodCity = false;
		int possibleNextCity = 0;
		while(!haveGoodCity) {
			possibleNextCity = (int)(Math.random()*pathArray.length +1); //generate the next number in the path
			haveGoodCity = true;
			for(int i = 0; i<pathArray.length; i++) {
				if(possibleNextCity != pathArray[i]) { //if the next city has not been chosen before, continue
					//System.out.println("You may pass.");
				} 
				else {
					//System.out.println("you failed.");
					haveGoodCity = false; //break and repeat for loop if the possible next choice is already present
				}
			}//end for loop
		}
		return possibleNextCity;
	} //end 3a; go back and finish Step 3


	private static int getCurrentPathDistance(int[] pathArray,int[][] distanceMatrix) {
		//Step 4: compute distance for path
		int currentDistanceContribution; 
		int totalDistance = 0;
		int fromCity;
		int toCity;
		for(int i = 0; i<pathArray.length-1; i++) {
			fromCity = pathArray[i];
			toCity = pathArray[i+1];
			currentDistanceContribution = distanceMatrix[fromCity-1][toCity-1];
			totalDistance = totalDistance + currentDistanceContribution;
		}
		fromCity = pathArray[pathArray.length-1];
		toCity = pathArray[0];
		currentDistanceContribution = distanceMatrix[fromCity-1][toCity-1];
		totalDistance = totalDistance + currentDistanceContribution;
		return totalDistance;
	} //end step 4; continue to step 5


	private static void storeBestPathArray(int[] pathArray, int[] bestPathArray) {
		//5a: hand over pathArray to bestPathArray
		for(int i=0; i<pathArray.length; i++) {
			bestPathArray[i] = pathArray[i]; 
		}
	}//continue to 5b


	private static void printPathArray(int[] pathArray) {
		//5b: print the pathway
		System.out.print("Path:[");
		for(int i = 0; i<pathArray.length-1; i++) {
			System.out.print(pathArray[i] + ", ");
		}
		System.out.print(pathArray[pathArray.length-1] + "] ");
	}//repeat from 5a until best path is found


	private static void printToCSVFile(int [][] distanceMatrix, int cityCount) {
		//step 6: convert the distance matrix to a CSV file
		try (PrintWriter writer = new PrintWriter(new File("TSPtoCSV_lyle3.csv"))) {

			StringBuilder sb = new StringBuilder();
			sb.append("City A");
			sb.append(',');
			sb.append("City B");
			sb.append(',');
			sb.append("City C");
			sb.append(',');
			sb.append("City D");
			sb.append(',');
			sb.append("City E");
			sb.append('\n');

			for(int row = 0; row < cityCount; row++) {
				for(int column = 0; column < cityCount; column++) {
					sb.append(distanceMatrix[row][column]);
					sb.append(',');
				}
				sb.append('\n');
			}

			writer.write(sb.toString());

			System.out.println("\n" + "Printed"); 
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}		
	}//end
}