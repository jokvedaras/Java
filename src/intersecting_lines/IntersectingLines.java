package intersecting_lines;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Challenge 163
 * Considering the lines as vectors, find if two lines intersect and at what point they intersect
 * @author jokvedaras
 *
 */


public class IntersectingLines {
	public static void main(String[] args) {
		//parse input using scanner
		ArrayList<LineSegment> lines = parseInput(new Scanner(System.in));
		
		//loop and find intersecting lines
		for (int i = 0; i < lines.size() - 1; i++) {
			for (int j = i + 1; j < lines.size(); j++) {
				lines.get(i).printIntersectWith(lines.get(j));
			}
		}
	}

	public static ArrayList<LineSegment> parseInput(Scanner sc) {
		//initialize array
		ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
		//populate array
		String label = sc.next();
		while (!label.equals("END")) {
			lines.add(new LineSegment(label, new Vector(sc.nextDouble(), sc
					.nextDouble()),
					new Vector(sc.nextDouble(), sc.nextDouble())));
			label = sc.next();
		}
		return lines;
	}
}

/**
 * Line segment vector runs from coordinate p (start point) to p+r (end point)
 * @author jokvedaras
 *
 */
class LineSegment {
	private String label;
	private Vector start, length;

	public LineSegment(String label, Vector start, Vector end) {
		this.label = label;
		this.start = start;
		this.length = end.minus(start);
	}
	
	/**
	 * Two vectors intersect if there dot product is 0
	 * x = currentLineSegment
	 * y = parameterLineSegement
	 * lone x = cross product
	 * t = (xStart - yStart) x yLength / (xLength x yLength)
	 * u = (xStart - yStart) x xLength / (xLength x yLength)
	 * @param vector
	 */
	public void printIntersectWith(LineSegment vector) {
		if (!isParallelWith(vector)) {
			double t = (vector.start.minus(start)).crossProduct(vector.length) / (length.crossProduct(vector.length));
			double u = (vector.start.minus(start)).crossProduct(length) / (length.crossProduct(vector.length));
			//if 0 <= t <= 1 and 0 <= u <= 1
			if ((t >= 0) && (t <= 1) && (u >= 0) && (u <= 1)) 
			{
				Vector intersect = start.plus(t, length);
				System.out.println(label + " intersects " + vector.label
						+ " at " + intersect);
			}
		}
		
	}
	
	public boolean isPerpendicularWith(LineSegment vector) {
		return length.dotProduct(vector.length) == 0;
	}
	
	/**
	 * Two vectors are parallel if there cross product is 0
	 * @param vector
	 * @return true/false depending on if they are parallel
	 */
	private boolean isParallelWith(LineSegment vector) {
		return length.crossProduct(vector.length) == 0;
	}
}

/**
 * Handle all vector operations
 * @author jokvedaras
 *
 */
class Vector {
	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "{" + x + ", " + y + "}";
	}

	public Vector plus(double scalar, Vector vector) {
		return new Vector(x + (scalar * vector.x), y + (scalar * vector.y));
	}

	public Vector minus(Vector vector) {
		return this.plus(-1, vector);
	}

	public double crossProduct(Vector vector) {
		return (this.x * vector.y) - (this.y * vector.x);
	}
	
	public double dotProduct(Vector vector){
		return (this.x * vector.x) + (this.y * vector.y);
	}
}

/**
 * Input:
 * 
 * A -2.5 .5 3.5 .5 B -2.23 99.99 -2.10 -56.23 C -1.23 99.99 -1.10 -56.23 D
 * 100.1 1000.34 2000.23 2100.23 E 1.5 -1 1.5 1.0 F 2.0 2.0 3.0 2.0 G 2.5 .5 2.5
 * 2.0 END
 * 
 * Output:
 * 
 * A intersects B at {-2.1472084240174114, 0.5} A intersects C at
 * {-1.1472084240174112, 0.5} A intersects E at {1.5, 0.5} A intersects G at
 * {2.5, 0.5} F intersects G at {2.5, 2.0}
 */
