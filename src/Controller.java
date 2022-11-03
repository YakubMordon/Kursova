import airlinesContent.planeTypes.Plane;
import database.DBOper;
import file.FileOper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

import static message.SendEmail.sendMessage;

public class Controller implements Initializable {
@FXML
private Label TotalCarryingCap;
@FXML
private Label TotalPassengerCap;
@FXML
private AnchorPane insertNode;
@FXML
private ChoiceBox<String> choiceBox;
@FXML
private TextField modelField;
@FXML
private Label flightLabel;
@FXML
private TextField rangeField;
@FXML
private ListView<String> listOfPlanes;
@FXML
private Label companyName;

private String[] types = {"PASSENGER","CARGO"};

private String type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Plane> planes = Plane.getPlanes();
        for (Plane plane : planes) {
            listOfPlanes.getItems().addAll(plane.toString());
        }
        choiceBox.getItems().addAll(types);
        choiceBox.setOnAction(this::getPlaneType);
    }

    public void getPlaneType(javafx.event.ActionEvent e){
        type = choiceBox.getValue();
    }

    public void UpdateList(){
        ArrayList<Plane> planes = Plane.getPlanes();
        listOfPlanes.getItems().clear();
        for (Plane plane : planes) {
            listOfPlanes.getItems().addAll(plane.toString());
        }
    }
    public void UpdateCapacity(){
        int TotalCarrying = Plane.getTotalCarryingCap(Plane.getPlanes(),Main.logger);
        TotalCarryingCap.setText("Total Carrying Capacity: " + TotalCarrying);
        Plane.setTotalCarryingCap(TotalCarrying);

        int TotalPassenger = Plane.getTotalPassengerCap(Plane.getPlanes(),Main.logger);
        TotalPassengerCap.setText("Total Passenger Capacity: " + TotalPassenger);
        Plane.setTotalCarryingCap(TotalPassenger);
    }
    public void SortByAsc(javafx.event.ActionEvent e){
        Plane.setPlanes(Plane.sortPlanes(Plane.getPlanes(), Plane.rangeSorting.ASC,Main.logger));
        UpdateList();
    }

    public void SortByDesc(javafx.event.ActionEvent e){
        Plane.setPlanes(Plane.sortPlanes(Plane.getPlanes(), Plane.rangeSorting.DESC,Main.logger));
        UpdateList();
    }

    public void Insert(javafx.event.ActionEvent e){

        insertNode.setVisible(true);
        insertNode.setDisable(false);
    }

    public void submit(javafx.event.ActionEvent e) throws Exception {

        if(type == null)
            type = "PASSENGER";

        Main.logger.info("Вводимо назву моделі");
        String model = modelField.getText();

        if(model.equals(""))
            model = "A-996";

        Main.logger.info("Вводимо дальність польоту");
        int flightRange;

        try {
            flightRange = Integer.parseInt(rangeField.getText());
        } catch (NumberFormatException numb) {
            flightRange = 1250;
            System.out.println("You've entered not a number, program used default value: " + flightRange);
            flightLabel.setText("You've entered not a number, program used default value: " + flightRange);
        }

        Plane.addPlane(Plane.PlaneType.valueOf(type),model,flightRange, Main.logger);
        UpdateCapacity();
        UpdateList();

        insertNode.setVisible(false);
        insertNode.setDisable(true);
    }

    public void Filter(javafx.event.ActionEvent e){
        Plane.setPlanes(Plane.filterPlanes(Plane.getPlanes(),Main.logger));
        UpdateCapacity();
        UpdateList();
    }

    public void CreateCompany(javafx.event.ActionEvent e){
        Main.logger.finer("Створюємо компанію...");

        if(Objects.equals(Main.content.getAirlineName(), "")) {

            Main.logger.finer("Вписуємо назву компанії");

            Main.content.setAirlineName("Emirates Airline");
            companyName.setText("Company Name: Emirates Airline");
        }else {
            Main.logger.finer("Компанія уже існує");
        }
    }

    public void ReadFile(javafx.event.ActionEvent e) throws Exception {

        FileOper fileRead = new FileOper();

        Plane.getPlanes().clear();

        try {
            Main.content = fileRead.readFile("Company",Main.logger);
        } catch (Exception ex) {
            Main.logger.log(Level.WARNING,"Знайдено критичну помилку: ",ex);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + ex);
            ex.printStackTrace();
        }

        UpdateCapacity();
        UpdateList();
    }

    public void WriteFile(javafx.event.ActionEvent e) throws Exception {
        new FileOper().writeFile("Company",Main.content,Main.logger);
    }

    public void InsertToDB(javafx.event.ActionEvent e) throws Exception {
        DBOper.saveToDB(Main.content, Main.logger);
    }

    public void ReadDB(javafx.event.ActionEvent e) throws Exception {
        Plane.getPlanes().clear();

        try {
            Main.content = DBOper.readFromDB(Main.logger);
        } catch (Exception ex) {
            Main.logger.log(Level.WARNING,"Знайдено критичну помилку: ",ex);
            System.out.println("The error occurred.\n");
            sendMessage("Знайдено критичну помилку: " + ex);
            ex.printStackTrace();
        }
        UpdateCapacity();
        UpdateList();
    }

}
