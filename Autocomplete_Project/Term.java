import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

// An immutable data type that represents an autocomplete term: a query string
// and an associated real-valued weight.
public final class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    public Term(String query, long weight) {

        //Checking for any illegal input
        if (query == null) {
            throw new NullPointerException();
        }
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        this.query = query;
        this.weight = weight;
    }

    public Term(String query) {
        if (query == null) {
            throw new NullPointerException();
        }
        this.query = query;
        weight = 0;
    }

    // A reverse-weight comparator. Compare the terms in
    //descending order by weight
    public static Comparator<Term> byReverseWeightOrder() {

        Comparator<Term> comparator = new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {

                //Invalid input has been checked in the constructor
                return (int) Math.signum(o2.getWeight() - o1.getWeight());
            }
        };

        return comparator;
    }

    // A prefix-order comparator. Compare first "r" characters
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("negative prefix");
        }

        Comparator<Term> comparator = new Comparator<Term>() {
            @Override
            public int compare(Term o1, Term o2) {
                //Null query has been checked in the constructor

                //Use Math.min just in case the length is shorter than "r"
                String o1String = o1.getQuery().substring(0, Math.min(r, o1.getQuery().length()));
                String o2String = o2.getQuery().substring(0, Math.min(r, o2.getQuery().length()));

                return o1String.compareTo(o2String);
            }
        };

        return comparator;
    }

    // Compare this term to that in lexicographic order by query and
    // return a negative, zero, or positive integer based on whether this
    // term is smaller, equal to, or larger than that term.
    public int compareTo(Term that) {

        //Compare lexicographic order first
        if (!this.getQuery().equals(that.getQuery())) {
            return this.getQuery().compareTo(that.getQuery());
        }
        //compare by weight
        else if (this.getWeight() != that.getWeight()) {
            return (int) Math.signum(this.getWeight() - that.getWeight());

        } else {
            return 0;
        }
    }

    //getters:
    public String getQuery() {
        return query;
    }

    public long getWeight() {
        return weight;
    }

    // A string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
