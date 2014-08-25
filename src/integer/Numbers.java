package integer;


/**
 * Java Solution to a variety of number problems
 */

public class Numbers {
	
	/**
	 * Find pi to the nth digit
	 */
	public void piNthDigits(int length) {
		double piDigits = Math.floor((Math.PI % 1) * (Math.pow(10.0, length)));
		System.out.println((3 + (piDigits / Math.pow(10, length))) + "\n");
	}

	/**
	 * Find fibonnacci Sequence of a number recursively
	 */
	public static int fibonnacciSeqRecursive(int length) {
		if (length == 0 || length == 1) {
			return 1;
		} else {
			return fibonnacciSeqRecursive(length - 1)
					+ fibonnacciSeqRecursive(length - 2);
		}
	}

	/**
	 * Find fibonnacci sequence of a number iteratively
	 */
	public static int fibonnacciSeqIterative(int length) {
		
		int fibArray [] = new int[length + 1];
		fibArray[0] = fibArray[1] = 1; //set first and second number in fib Seq to 1
		
		for (int i = 2; i <= length; i++){
			fibArray[i] = fibArray[i - 1] + fibArray[i - 2];
		}
		return fibArray[length];
		
	}
	
	public static void main (String args[]){
		//System.out.println("Recursive: " + fibonnacciSeqRecursive(150));
		System.out.println("Iterative: " + fibonnacciSeqIterative(150));
	}
}
