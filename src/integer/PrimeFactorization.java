package integer;

import java.util.Scanner;

/**
 * Find the prime factors of a number
 * 
 */
public class PrimeFactorization {
	
	public PrimeFactorization(){
		
	}
	
	/**
	 * Returns the prime factors of a number
	 */
	public int[] findPrimeFactor(int num) {
		// array that will keep the prime factorizations
		int[] factors = new int[num / 2];
		int prime = 2;// first prime to try
		int index = 0;
		while (num != 1) {
			if (num % prime == 0) {
				num = num / prime;
				factors[index] = prime; // save the factor
				index++;
			} else
				prime = getNextPrime(prime, num);
		}
		return factors;
	}

	/**
	 * Return the next prime number to try.
	 */
	private int getNextPrime(int prime, int num) {
		if (isPrime(num)) // check if the current number is already a prime
			prime = num;
		else // find next best prime
		{
			prime++;
			while (!isPrime(prime) && prime < num) {
				prime++;
			}
		}
		return prime;
	}

	/**
	 * Checks if a number is a prime number.
	 */
	private boolean isPrime(int number) {
		boolean isPrime = true;
		int i = 2;
		while (i < number) {
			if (number % i == 0) {
				isPrime = false;
				break;
			}
			i++;
		}
		return isPrime;

	}

	/**
	 * Print an array
	 */
	private void printArray(int[] arr, int num) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != 0)
				System.out.print(arr[i] + " X ");
		}
		System.out.print("1 = " + num);
		System.out.println();
	}

	/*
	 * class driver
	 */
	public static void main(String[] args) {
		int num = 0;
		PrimeFactorization prime = new PrimeFactorization();
		
		try{
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter number to find Prime factors of: ");
			num = scan.nextInt();
			prime.printArray(prime.findPrimeFactor(num), num);
			scan.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
}
