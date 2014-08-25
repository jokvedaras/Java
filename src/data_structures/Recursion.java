package data_structures;


public class Recursion {
	
private static boolean trace = true;

	public Recursion() {
	
	}
	/**
	 * @param a is not negative
	 * @param b
	 * @return the product of a multiplied by b
	 */
	public static int mult(int a, int b)
	{
		if(trace)
			System.out.println("A = "+ a + " B = "+ b);
		if(a == 0 || b==0)
			return 0;
		if(b == 1)
			return a;
		else
			return mult(a, b-1) + a;
	}
}
