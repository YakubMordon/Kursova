package test;

import airlinesContent.Airline;
import airlinesContent.planeTypes.Cargo;
import airlinesContent.planeTypes.Passenger;
import airlinesContent.planeTypes.Plane;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import static org.junit.Assert.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class MainTest extends ApplicationTest {

    private static final Logger logger = Logger.getLogger(MainTest.class.getName());

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("resources/icon.png");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testCreateCompany(){
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#createCompany");
        sleep(600);
        verifyThat("#companyName",hasText("Company Name: Emirates Airline"));
    }

    @Test
    public void testInsert1() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#insert");
        sleep(600);
        clickOn("#choiceBox").clickOn("CARGO");
        sleep(600);
        clickOn("#modelField").write("B-228");
        sleep(600);
        clickOn("#rangeField").write("5412");
        sleep(600);
        clickOn("#submit");
        sleep(600);
        String example = Plane.getPlanes().get(0).toString();

        assertEquals(" Passenger Capacity = " + 10000 +
                ", Carrying Capacity = " + 15 +
                " tons, Flight Range = " + 5412 +
                " km, Fuel Consumption = " + 17.5 +
                " per km, Model = '" + "B-228" + '\'' + '\n',example);

    }

    @Test
    public void testInsert2() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#insert");
        sleep(600);
        clickOn("#submit");
        sleep(600);
        String example = Plane.getPlanes().get(0).toString();

        assertEquals(" Passenger Capacity = " + 25000 +
                ", Carrying Capacity = " + 7 +
                " tons, Flight Range = " + 1250 +
                " km, Fuel Consumption = " + 11.5 +
                " per km, Model = '" + "A-996" + '\'' + '\n',example);

    }

    @Test
    public void testFilter() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#insert");
        sleep(600);
        clickOn("#submit");
        sleep(600);
        clickOn("#insert");
        sleep(600);
        clickOn("#choiceBox").clickOn("CARGO");
        sleep(600);
        clickOn("#modelField").write("B-228");
        sleep(600);
        clickOn("#rangeField").write("5412");
        sleep(600);
        clickOn("#submit");
        sleep(600);
        clickOn("#filter");
        sleep(600);
        String example = Plane.getPlanes().get(0).toString();

        assertEquals(" Passenger Capacity = " + 25000 +
                ", Carrying Capacity = " + 7 +
                " tons, Flight Range = " + 1250 +
                " km, Fuel Consumption = " + 11.5 +
                " per km, Model = '" + "A-996" + '\'' + '\n',example);

    }

    @Test
    public void testAsc() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#readF");
        sleep(600);
        ArrayList<Plane> planes = new ArrayList<>();
        planes.add(new Cargo("A-331", 1250));
        planes.add(new Cargo("A-885", 1500));
        planes.add(new Passenger("564", 7134));
        clickOn("#asc");
        sleep(600);
        String actual = null,expected = null;

        for(int i = 0; i < planes.size(); i++){
            expected += planes.get(i).toString();
            actual += Plane.getPlanes().get(i).toString();
        }

        assertEquals(expected,actual);

        assertEquals(37,Plane.getTotalCarryingCap(Plane.getPlanes(), logger));

        assertEquals(45000,Plane.getTotalPassengerCap(Plane.getPlanes(), logger));
    }

    @Test
    public void testDesc() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#readF");
        sleep(600);
        ArrayList<Plane> planes = new ArrayList<>();
        planes.add(new Passenger("564", 7134));
        planes.add(new Cargo("A-885", 1500));
        planes.add(new Cargo("A-331", 1250));
        clickOn("#desc");
        sleep(600);
        String actual = null,expected = null;

        for(int i = 0; i < planes.size(); i++){
            expected += planes.get(i).toString();
            actual += Plane.getPlanes().get(i).toString();
        }

        assertEquals(expected,actual);
    }

    @Test
    public void testReadDB() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        String expected = new Passenger("564",7134).toString();
        clickOn("#readDB");
        sleep(600);
        String actual = Plane.getPlanes().get(0).toString();
        assertEquals(expected,actual);
    }

    @Test
    public void testInsertDB() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        ArrayList<Plane> planes = Plane.getPlanes();
        planes.add(new Passenger("564",7134));
        clickOn("#insertDB");
        sleep(600);
        clickOn("#readDB");
        sleep(600);

        String actual = null,expected = null;

        for(int i = 0; i < planes.size(); i++){
            expected += planes.get(i).toString();
            actual += Plane.getPlanes().get(i).toString();
        }

        assertEquals(expected,actual);
    }

    @Test
    public void testReadFile() {
        Plane.getPlanes().clear();
        clickOn("#asc");
        clickOn("#readF");
        sleep(600);

        ArrayList<Plane> planes = new ArrayList<Plane>();
        planes.add(new Cargo("A-885", 1500));
        planes.add(new Passenger("564", 7134));
        planes.add(new Cargo("A-331", 1250));

        String actual = null,expected = null;

        for(int i = 0; i < planes.size(); i++){
            expected += planes.get(i).toString();
            actual += Plane.getPlanes().get(i).toString();
        }

        assertEquals(expected,actual);
    }

    @Test
    public void testWriteFile() {
        Plane.getPlanes().clear();
        clickOn("#asc");

        ArrayList<Plane> planes = Plane.getPlanes();

        planes.add(new Cargo("A-885", 1500));
        planes.add(new Passenger("564", 7134));
        planes.add(new Cargo("A-331", 1250));

        clickOn("#writeF");
        sleep(600);

        clickOn("#readF");
        sleep(600);

        String actual = null,expected = null;

        for(int i = 0; i < planes.size(); i++){
            expected += planes.get(i).toString();
            actual += Plane.getPlanes().get(i).toString();
        }

        assertEquals(expected,actual);
    }
}