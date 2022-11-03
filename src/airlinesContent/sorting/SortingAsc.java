package airlinesContent.sorting;

import airlinesContent.planeTypes.Plane;

import java.util.Comparator;

public class SortingAsc implements Comparator<Plane> {
    @Override
    public int compare(Plane a, Plane b) {
        return a.getFlightRange() - b.getFlightRange();
    }
}
