package test;

import airlinesContent.Airline;
import airlinesContent.planeTypes.Plane;
import file.FileOper;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class FileOperTest {

    private static final Logger logger = Logger.getLogger(PlaneTest.class.getName());

    @org.junit.jupiter.api.Test
    void testWriteFile() throws Exception {
        logger.setUseParentHandlers(false);

        Airline content = new Airline("test");
        content.getCompanyPlanes().addPlane(Plane.PlaneType.PASSENGER, "336", 1250, logger);

        new FileOper().writeFile("test",content,logger);

        Airline readActual = new FileOper().readFile("test",logger);

        String expected = content.getCompanyPlanes().getPlanes().get(0).toString();

        String actual = readActual.getCompanyPlanes().getPlanes().get(0).toString();

        assertEquals(expected,actual);

        content.getCompanyPlanes().getPlanes().clear();
        readActual.getCompanyPlanes().getPlanes().clear();
    }

    @org.junit.jupiter.api.Test
    void testReadFile() throws Exception {
        logger.setUseParentHandlers(false);

        Airline content = new Airline("test");
        content.getCompanyPlanes().addPlane(Plane.PlaneType.PASSENGER, "336", 1250, logger);

        Airline readActual = new FileOper().readFile("test",logger);

        String expected = content.getCompanyPlanes().getPlanes().get(0).toString();

        String actual = readActual.getCompanyPlanes().getPlanes().get(0).toString();

        assertEquals(expected,actual);

    }
}