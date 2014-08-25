package data_structures;

public class KsmallestMain {

	public static void main(String [] args)
	{
		int smallest [] = {200,4,6,8,34,3,102,1,23};
		quick_srt(smallest,0,5);
		System.out.println("The smallest number is: " + smallest[0]);
	}
	public static int kSmall(int k,int anArray[], int first, int last)
	{
		int pivotIndex = 0;
		if(k < pivotIndex - first + 1){
			return kSmall(k, anArray,first, pivotIndex -1);
		}
		else if(k == pivotIndex - first + 1){
			return first;
		}
		else{
			return kSmall(k-(pivotIndex - first +1), anArray, 
					pivotIndex+1,last);
		}
	}
	 public static void quick_srt(int array[],int low, int n){
		  int lo = low;
		  int hi = n;
		  if (lo >= n) {
		  return;
		  }
		  int mid = array[(lo + hi) / 2];
		  while (lo < hi) {
		  while (lo<hi && array[lo] < mid) {
		  lo++;
		  }
		  while (lo<hi && array[hi] > mid) {
		  hi--;
		  }
		  if (lo < hi) {
		  int T = array[lo];
		  array[lo] = array[hi];
		  array[hi] = T;
		  }
		  }
		  if (hi < lo) {
		  int T = hi;
		  hi = lo;
		  lo = T;
		  }
		  quick_srt(array, low, lo);
		  quick_srt(array, lo == low ? lo+1 : lo, n);
		  }
		}
