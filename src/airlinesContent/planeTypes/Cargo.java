package airlinesContent.planeTypes;

public class Cargo extends Plane {
    final PlaneType type = PlaneType.CARGO;
    public Cargo (String newModelName,int modelRange) {
        modelName = newModelName;
        flightRange = modelRange;
        passengerCapacity = 10000;
        carryingCapacity = 15;
        fuelConsumption = 17.5;
    }

    @Override
    public PlaneType getType() { return type; }
}
