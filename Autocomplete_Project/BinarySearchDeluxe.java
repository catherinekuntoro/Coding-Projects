import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// Implements binary search for clients that may want to know the index of
// either the first or last key in a (sorted) collection of keys.
public class BinarySearchDeluxe {

    // The index of the first key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {

        //Checking if any of the inputs are invalid
        if (a == null || key == null || comparator == null) {
            throw new NullPointerException("There is a null array, key, or comparator.");
        }

        int lowIndex = 0;
        int highIndex = a.length - 1;

        int indexKey = -1;

        //Want to continue the process until the end
        while (lowIndex <= highIndex) {
            int midIndex = lowIndex + (highIndex - lowIndex) / 2;

            int compareResult = comparator.compare(key, a[midIndex]);

            //key is smaller
            if (compareResult < 0) {

                //The item is at the left side of the array
                highIndex = midIndex - 1;

            } else if (compareResult > 0) {

                //The item is at the right side of the array
                lowIndex = midIndex + 1;
            } else {

                //Keep on going to ensure the first index of the duplicate is foudn
                highIndex = midIndex - 1; //want to keep going to find the earliest
                indexKey = midIndex;
            }
        }

        return indexKey;

    }

    // The index of the last key in a[] that equals the search key,
    // or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key,
                                        Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new NullPointerException("There is a null array, key, or comparator.");
        }

        int lowIndex = 0;
        int highIndex = a.length - 1;

        int indexKey = -1;

        //Want to continue the process until the end
        while (lowIndex <= highIndex) {
            int midIndex = lowIndex + (highIndex - lowIndex) / 2;

            int compareResult = comparator.compare(key, a[midIndex]);

            //key is smaller
            if (compareResult < 0) {

                //the item must be on the left side
                highIndex = midIndex - 1;
            } else if (compareResult > 0) {

                //the item must be on the right side
                lowIndex = midIndex + 1;
            } else {
                lowIndex = midIndex + 1; //want to keep going to find the earliest
                indexKey = midIndex;
            }
        }

        return indexKey;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println(count);
    }
}
