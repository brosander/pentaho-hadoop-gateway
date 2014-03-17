package org.pentaho.gateway.util;

/**
 * Created by bryan on 3/15/14.
 */
public class Pair<First, Second> {
    private final First first;
    private final Second second;

    public static <First, Second> Pair<First, Second> of(First first, Second second) {
        return new Pair<First, Second>(first, second);
    }

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
