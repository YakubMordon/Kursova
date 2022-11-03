package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import airlinesContent.planeTypes.Cargo;
import airlinesContent.planeTypes.Passenger;
import airlinesContent.planeTypes.Plane;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    private static final Logger logger = Logger.getLogger(PlaneTest.class.getName());
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @org.junit.jupiter.api.Test
    void test1AddPlane() {
        logger.setUseParentHandlers(false);

        Plane planes = new Plane();
        planes.addPlane(Plane.PlaneType.PASSENGER, "336", 1250, logger);

        Assertions.assertEquals(new Passenger("336", 1250).toString(), planes.getPlanes().get(0).toString());
        planes.getPlanes().clear();
    }

    @org.junit.jupiter.api.Test
    void test2AddPlane() {
        logger.setUseParentHandlers(false);

        Plane planes = new Plane();
        planes.getPlanes().clear();
        planes.addPlane(Plane.PlaneType.CARGO, "228", 1650, logger);

        Assertions.assertEquals(new Cargo("228", 1650).toString(), planes.getPlanes().get(0).toString());
        planes.getPlanes().clear();
    }

    @org.junit.jupiter.api.Test
    void test1ListOfPlanes() {
        logger.setUseParentHandlers(false);

        Plane planes = new Plane();
        planes.getPlanes().clear();

        System.setOut(new PrintStream(outputStreamCaptor));
        planes.listOfPlanes(planes.getPlanes(),logger);

        assertEquals("There are no planes.",outputStreamCaptor.toString().trim());

        System.setOut(new PrintStream(standardOut));
    }

    @org.junit.jupiter.api.Test
    void test2ListOfPlanes() {

        logger.setUseParentHandlers(false);

        Plane planes = new Plane();
        planes.addPlane(Plane.PlaneType.CARGO,"228",1650,logger);

        System.setOut(new PrintStream(outputStreamCaptor));
        planes.listOfPlanes(planes.getPlanes(),logger);

        String test = (1 + " Type Of Plane: " + Plane.PlaneType.CARGO + "," + " Passenger Capacity = " + 10000 +
                ", Carrying Capacity = " + 15 +
                " tons, Flight Range = " + 1650 +
                " km, Fuel Consumption = " + 17.5 +
                " per km, Model = '" + "228" + '\'');

        assertEquals(test,outputStreamCaptor.toString().trim());

        System.setOut(new PrintStream(standardOut));

        planes.getPlanes().clear();
    }

    @org.junit.jupiter.api.Test
    void test1SortPlanes() {
        logger.setUseParentHandlers(false);
        ArrayList<Plane> planes = new ArrayList<Plane>();

        planes.add(new Passenger("137",154));
        planes.add(new Cargo("324",864));
        planes.add(new Passenger("247",652));

        ArrayList<Plane> planesRight = new ArrayList<Plane>();

        planesRight.add(new Passenger("137",154));
        planesRight.add(new Passenger("247",652));
        planesRight.add(new Cargo("324",864));

        String expected = null,actual = null;

        planes = Plane.sortPlanes(planes, Plane.rangeSorting.ASC,logger);

        for(int i = 0; i < 3; i++){
            expected += planesRight.get(i).toString();
            actual += planes.get(i).toString();
        }

        assertEquals(expected,actual);
    }

    @org.junit.jupiter.api.Test
    void test2SortPlanes() {
        logger.setUseParentHandlers(false);
        ArrayList<Plane> planes = new ArrayList<Plane>();

        planes.add(new Passenger("137",154));
        planes.add(new Cargo("324",864));
        planes.add(new Passenger("247",652));

        ArrayList<Plane> planesRight = new ArrayList<Plane>();

        planesRight.add(new Cargo("324",864));
        planesRight.add(new Passenger("247",652));
        planesRight.add(new Passenger("137",154));

        String expected = null,actual = null;

        planes = Plane.sortPlanes(planes, Plane.rangeSorting.DESC,logger);

        for(int i = 0; i < 3; i++){
            expected += planesRight.get(i).toString();
            actual += planes.get(i).toString();
        }

        assertEquals(expected,actual);
    }

    @org.junit.jupiter.api.Test
    void testFilterPlanes() {
        logger.setUseParentHandlers(false);
        ArrayList<Plane> planes = new ArrayList<Plane>();

        planes.add(new Passenger("137",154));
        planes.add(new Cargo("324",864));
        planes.add(new Passenger("247",652));

        ArrayList<Plane> planesRight = new ArrayList<Plane>();

        planesRight.add(new Passenger("137",154));
        planesRight.add(new Passenger("247",652));

        String expected = null,actual = null;

        planes = Plane.filterPlanes(planes,logger);

        for(int i = 0; i < 2; i++){
            expected += planesRight.get(i).toString();
            actual += planes.get(i).toString();
        }

        assertEquals(expected,actual);

    }

    @org.junit.jupiter.api.Test
    void testGetTotalPassengerCap() {
        logger.setUseParentHandlers(false);
        ArrayList<Plane> planes = new ArrayList<Plane>();

        planes.add(new Passenger("137",154));
        planes.add(new Cargo("324",864));
        planes.add(new Passenger("247",652));

        assertEquals(60000,Plane.getTotalPassengerCap(planes,logger));
    }

    @org.junit.jupiter.api.Test
    void testGetTotalCarryingCap() {
        logger.setUseParentHandlers(false);
        ArrayList<Plane> planes = new ArrayList<Plane>();

        planes.add(new Passenger("137",154));
        planes.add(new Cargo("324",864));
        planes.add(new Passenger("247",652));

        assertEquals(29,Plane.getTotalCarryingCap(planes,logger));
    }

}