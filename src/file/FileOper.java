package file;

import airlinesContent.Airline;
import airlinesContent.planeTypes.Plane;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static message.SendEmail.sendMessage;

public class FileOper {
    PrintStream standart;
    PrintStream fileStream;
    public Airline readFile(String destination,Logger logger) throws Exception {
        Airline content = new Airline("");
        try {
            FileReader fr = new FileReader(destination);
            Scanner reader = new Scanner(fr);

            while (reader.hasNextLine()) {
                Plane.PlaneType type = Plane.PlaneType.valueOf(reader.nextLine());
                String model = reader.nextLine();
                int Range = Integer.parseInt(reader.nextLine());
                content.getCompanyPlanes().addPlane(type, model, Range, logger);
            }

            fr.close();

        }catch (IOException e) {
            logger.log(Level.WARNING,"Знайдено критичну помилку: ",e);
            sendMessage("Знайдено критичну помилку: " + e);
            System.out.println("The error occurred.\n");
            e.printStackTrace();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return content;
    }

    public void writeFile(String destination, Airline content, Logger logger) throws Exception {
        standart = System.out;
        try {
            File myFile = new File(destination);

            if (myFile.createNewFile())
                System.out.println("Файл створено: " + myFile.getName() + "\n");
            else {
                System.out.println("Файл уже існує.\n");
                myFile.delete();
                myFile.createNewFile();
            }

            System.setOut(fileStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(myFile))));

        } catch (IOException e) {
            logger.log(Level.WARNING,"Знайдено критичну помилку: ",e);
            sendMessage("Знайдено критичну помилку: " + e);
            System.out.println("The error occurred.\n");
            e.printStackTrace();
        }

        ArrayList<Plane> planes = content.getCompanyPlanes().getPlanes();
        for (int i = 0; i < planes.size();i++) {
            System.out.println(planes.get(i).getType());
            System.out.println(planes.get(i).getModelName() +
                    "\n" + planes.get(i).getFlightRange());
        }
        System.out.flush();
        System.setOut(standart);
    }

}
