package rotate_array;

/**
 * Challenge 169 on Reddit DailyProgrammer
 * Create a Program to accept input of an Array and be able
 * to rotate and print that array.
 * 
 * @author Joe Kvedaras
 */

import java.util.Scanner;

class Rotate{

	private static int size;
	private static Object [][] array;
	
	public static void main (String[] args){
		//Make a new scanner to accept input from command line
		try{
			Scanner sc = new Scanner(System.in);
			System.out.print("Size of Array? ");
			size = Integer.parseInt(sc.nextLine());
			//initialize array at user requested size. Limit size to 100
			if (size > 0 && size < 100){
				array = new Object[size][size];
			}
			else{
				System.out.println("Please enter a valid array. 0 < Size < 100.");
			}
			for (int i = 0; i < size; i++){
				for (int j = 0; j < size; j ++){
					array[i][j] = sc.nextInt();
				}
			}
			//close scanner
			sc.close();
			
			//rotate array
			printRotation(0, array);
			printRotation(90,array);
			printRotation(180,array);
			printRotation(270,array);
		}
		catch (Exception e){
			System.out.println("System Error Occured. Try running again.");
		}
		
	}
	
	
/**
 * Print any square array rotated any number of degrees. Runs at O(n^2).
 * @param degrees
 * @param array
 * @return rotated array
 */
	private static void printRotation(int degrees, Object [][] array){
		int rotation = degrees / 90;
		// for any rotation over 4, simplify.
		rotation = rotation % 4;

		int endOut = 0, endIn = 0, inner = 0, outer = 0;
		boolean controler = false;
		
		if (rotation == 0 || rotation == 4){//Array[i][j]
			endOut = array.length - 1;
			endIn = array[0].length - 1;
			inner = 0;// row i
			outer = 0;// col j
		}
		else if (rotation == 1){//Array[j][-i]
			inner = (array.length-1) * -1; //row i
			endIn = 0;
			endOut = array[0].length -1;
			outer = 0; //column j
			controler = true;
		}			
		else if (rotation == 2){//Array[-i][-j]
			outer = (array.length-1) * -1;//col j
			inner = (array[0].length -1) * -1;//row i
			endOut = 0;
			endIn = 0;
		}
		else if (rotation == 3){//Array[-j][i]
			outer = (array[0].length -1) * -1; //col j
			endOut = 0;
			inner = 0; //row i
			endIn = array.length-1;
			controler = true;
		}
		else
			System.out.println("Error!");
		
		//loop through array and print out results
		System.out.println("Rotation of " + degrees);
		for (int i = outer; i <= endOut;i++){
			for (int j = inner;j <= endIn ;j++){
				if(controler)
					System.out.print(array[Math.abs(j)][Math.abs(i)]);
				else
					System.out.print(array[Math.abs(i)][Math.abs(j)]);
			}
			System.out.println("");
		}

	}
	
	
}