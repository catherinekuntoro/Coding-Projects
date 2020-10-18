import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of
// string and weights, using Term and BinarySearchDeluxe.
public class Autocomplete {
    private Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] termsToCopy) {

        //Check if the input is null
        if (termsToCopy == null) {
            throw new NullPointerException("Null terms array invalid");
        }

        this.terms = new Term[termsToCopy.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = termsToCopy[i];
        }

        //sort based on compareTo()/natural ordering
        Arrays.sort(this.terms);
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {

        //Checking for null entry
        if (prefix == null) {
            throw new NullPointerException("Null prefix");
        }

        Comparator<Term> comparatorReverseWeightOrder = Term.byReverseWeightOrder();

        //ArrayList to store all of the terms with the prefix "prefix", as specified in the parameter
        ArrayList<Term> termsWithSamePrefix = new ArrayList<>();

        for (Term term : terms) {

            //Checking if this term at term[i] has the same prefix
            String checkPrefix = term.getQuery().substring(0, Math.min(prefix.length(), term.getQuery().length()));

            //If it has the same prefix, add it to termsWithSamePrefix
            if (checkPrefix.equals(prefix)) {
                termsWithSamePrefix.add(term);
            }
        }

        //Sort with the comparator
        termsWithSamePrefix.sort(comparatorReverseWeightOrder);

        //Return the array version of the ArrayList
        return termsWithSamePrefix.toArray(new Term[0]);
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {

        //Return the length of the array gained from allMatches()
        return allMatches(prefix).length;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
