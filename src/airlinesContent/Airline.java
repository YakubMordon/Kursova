package airlinesContent;

import airlinesContent.planeTypes.Plane;

public class Airline {

    String AirlineName;

    Plane companyPlanes;

    public Airline(){
        AirlineName = "";
        this.companyPlanes = new Plane();
    };

    public Airline(String airlineName) {
        AirlineName = airlineName;
        this.companyPlanes = new Plane();
    }

    public String getAirlineName() { return AirlineName; }

    public void setAirlineName(String airlineName) { AirlineName = airlineName; }

    public Plane getCompanyPlanes() { return this.companyPlanes; }

}
