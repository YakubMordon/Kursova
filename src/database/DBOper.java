package database;

import airlinesContent.Airline;
import airlinesContent.planeTypes.Plane;

import javax.swing.text.AbstractDocument;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static message.SendEmail.sendMessage;

public class DBOper {

        private static String dbCon = "jdbc:sqlserver://localhost\\SQLEXPRESS:51338;databaseName=Airlines;encrypt=true;trustServerCertificate=true;";

        public static Airline readFromDB(Logger logger) throws Exception {
            Airline content = new Airline("");
            try {

                logger.warning("��������� JDBC, �������� � �� ����������");
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

                logger.warning("��������� ���������� �� ���� ���� �����, �������� � �� ���������� �� ���� �����");
                Connection conn = DriverManager.getConnection(dbCon,"Owner","1111");

                logger.info("��������� ����� ��� ����� ����������");
                Statement st = conn.createStatement();

                logger.info("�������� ��� ��������� ��� �������� ��� �����");
                ResultSet resultSet = st.executeQuery("SELECT * from Planes");

                String type = null,name = null;
                int range = 0;

                System.out.println("Elements, which was read from db: \n");
                while(resultSet.next()){
                    System.out.println("\tPlane Type: " + resultSet.getString("Plane Type") + "\n\tModel Name: " + resultSet.getString("Model Name") + "\n\tFlightRange: " + resultSet.getString("Flight Range") + "\n");
                    type = resultSet.getString("Plane Type");
                    name = resultSet.getString("Model Name");
                    range = Integer.parseInt(resultSet.getString("Flight Range").trim());
                    content.getCompanyPlanes().addPlane(Plane.PlaneType.valueOf(type),name,range,logger);
                }

                logger.fine("���������� ���� ��������");

            } catch (Exception e) {
                logger.log(Level.WARNING,"�������� �������� �������: ",e);
                sendMessage("�������� �������� �������: " + e);
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

            return content;
        }

        public static void saveToDB(Airline Content,Logger logger) throws Exception {
            try {
                logger.warning("��������� JDBC, �������� � �� ����������");
                DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());

                logger.warning("��������� ���������� �� ���� ���� �����, �������� � �� ���������� �� ���� �����");
                Connection conn = DriverManager.getConnection(dbCon, "Owner", "1111");

                logger.info("��������� ����� ��� ����� ����������");
                Statement st = conn.createStatement();

                logger.info("������� �������");
                st.executeUpdate("Truncate table Planes");

                ArrayList<Plane> planes = Content.getCompanyPlanes().getPlanes();

                for(int i = 0;i < planes.size();i++){
                    String insertSql = "Insert into Planes Values ('" + planes.get(i).getType().toString() + "', '" + planes.get(i).getModelName() + "', " + planes.get(i).getFlightRange() + ")";
                    st.executeUpdate(insertSql);
                }

                logger.fine("����� ��� ���������");

            }catch(Exception e){
                logger.log(Level.WARNING,"�������� �������� �������: ",e);
                sendMessage("�������� �������� �������: " + e);
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        }

}
