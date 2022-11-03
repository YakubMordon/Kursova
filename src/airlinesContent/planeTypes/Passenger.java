package airlinesContent.planeTypes;

public class Passenger extends Plane {
    final PlaneType type = PlaneType.PASSENGER;
    public Passenger (String newModelName,int modelRange) {
        modelName = newModelName;
        flightRange = modelRange;
        passengerCapacity = 25000;
        carryingCapacity = 7;
        fuelConsumption = 11.5;
    }

    @Override
    public PlaneType getType() { return type; }
}
