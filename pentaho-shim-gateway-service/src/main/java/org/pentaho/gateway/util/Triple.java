package org.pentaho.gateway.util;

/**
 * Created by bryan on 3/15/14.
 */
public class Triple<First, Second, Third> {
    private final First first;
    private final Second second;
    private final Third third;

    public static <First, Second, Third> Triple<First, Second, Third> of(First first, Second second, Third third) {
        return new Triple<First, Second, Third>(first, second, third);
    }

    public Triple(First first, Second second, Third third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }

    public Third getThird() {
        return third;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }
}