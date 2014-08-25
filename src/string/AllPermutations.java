package string;

public class AllPermutations {
    
    public static void main(String[] args) {
        permutation ("", "ABC");
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        //Base Case. If str length == 0, return prefix
        if (n == 0) System.out.println(prefix);
        else {
            //loop through str
            for (int i = 0; i < n; i++){
                //Call permutation for every characer at prefix + str[i], str[0 to i] + str [i+1 to n]
                //permutation(prefix + char at position i, str - char[i])
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
            }
        }
    }   
}