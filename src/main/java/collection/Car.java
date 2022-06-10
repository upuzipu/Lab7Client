package collection;

import java.io.Serializable;
import java.util.Date;

public class Car implements Comparable<Car>, Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private final String name;
    private final Coordinates coordinates;
    private final Integer enginePower;
    private final Date creationDate;
    private final VehicleType vehicleType;
    private final FuelType fuelType;
    private final String owner;

    public Car(String name, Coordinates coordinates, Date creationDate, Integer enginePower, VehicleType vehicleType, FuelType fuelType, String owner) {
        this.id = 0;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.fuelType=fuelType;
        this.vehicleType = vehicleType;
        this.owner = owner;
        this.enginePower=enginePower;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Car o){
        if (o==null)
        {
            return 1;
        }
        int r = this.name.compareTo(o.name);
        if (r==0)
           r = this.coordinates.compareTo(o.coordinates);
        if (r==0) {
            if (vehicleType != null)
                r = this.vehicleType.compareTo(o.vehicleType);
            else if (o.vehicleType != null)
                r = -1;
        }
        return r;
    }

    @Override
    public String toString() {
        String s = "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates.toString() +
                ", creationDate=" + creationDate.toString() +
                ", VehicleType=" + vehicleType.toString() +
                ", fuelType=" + fuelType.toString();
        s+=", owner="+owner;
        s+="}";
        return s;
    }

    public String getName() {
        return name;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getEnginePower(){
        return enginePower;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public String getOwner() {
        return owner;
    }
}
