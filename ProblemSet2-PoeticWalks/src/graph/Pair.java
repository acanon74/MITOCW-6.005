//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
package graph;

/**
 * This data structure stores String a and b, which in context should relate.
 * <p>
 * We implement equals() to compare the source and target of two Pair object.
 * Using the String nature of the data, we implement hashCode() to produce a string hash with the form "source target".
 *
 * @param <K> a,b Strings which must have some relate in some way.
 */

public class Pair<K> {
    final K a;
    final K b;

    /**
     * Abstraction function:
     * We glue a String a and a String b.
     * <p>
     * Representation invariant:
     * a, b must be immutable Strings
     * <p>
     * Safety from Rep exposure:
     * a,b are immutable Strings.
     * a,b have package level exposure, this should only be used here since a general Pair implementation was
     * tailored to our use case.
     *
     */

    public Pair(K a, K b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Override of equals(), checks for equality of objects and then compares a, b fields in both objects.
     *
     * @param obj Another Pair to compared with
     * @return true if object are the same type and have the same fields, otherwise false.
     */
    @Override
    public boolean equals(Object obj){

        if(obj == null){
            return false;
        }

        if(obj.getClass() != this.getClass()){
            return false;
        }

        final Pair other = (Pair) obj;

        return ((this.a == other.a) && (this.b == other.b));
    }

    /**
     * Override of hashCode() to be able to use Pair with Collections which implement hash encoding.
     * We simply append target to source label and create a hash of it using the generic hash function. This guarantees uniqueness.
     *
     *
     * @return hash of the union (a + " " + b) using the generic hash function.
     */
    @Override
    public int hashCode() {
        String beforeHash = (a + "-" + b);
        return beforeHash.hashCode();
    }
}
