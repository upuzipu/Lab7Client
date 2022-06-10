package ioManager;

import collection.*;

import java.util.Date;
import java.util.Objects;

public class RequestElement {
    private final IReadable in;
    private final IWritable out;
    private final boolean interactive;

    public RequestElement(IReadable in, IWritable out, boolean interactive) {
        this.in = in;
        this.out = out;
        this.interactive = interactive;
    }

    private interface ICondition<T> {
        boolean check(T o);
    }

    private interface IExpression<T> {
        T exec();
    }

    private String readStr() {
        return in.readline();
    }

    private Integer readInt() {
        try {
            return Integer.parseInt(in.readline());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Float readFloat() {
        try {
            return Float.parseFloat(in.readline());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private <T> T readArg(String message, IExpression<T> query) {
        if (interactive)
            out.write(message);
        return query.exec();
    }

    private <T> T readArgWhile(String message, String hint, ICondition<T> condition, IExpression<T> query) {
        if (interactive)
            out.writeln(message);
        T o = readArg(">>>", query);
        while (interactive && !condition.check(o)) {
            if (interactive)
                out.writeln(hint);
            o = readArg(">>>", query);
        }
        return o;
    }

    private String readName() {
        return readArgWhile("Введите название Vehicle: ", "Название не может быть пустым и длина должна быть меньше или равна 128 символов",
                (s) -> s != null && !s.isEmpty() && s.length() <= 128, this::readStr);
    }

    private Coordinates readCoords() {
        Float x = (Float) readArgWhile("Введите координаты по Х: ", "Значение должно быть действительным числом",
                Objects::nonNull, this::readFloat);
        Float y = (Float) readArgWhile("Введите координаты по Y: ", "Значение должно быть действительным числом",
                Objects::nonNull, this::readFloat);
        return new Coordinates(x, y);
    }

    private int readEnginePower() {
        return readArgWhile("Введите enginePower: ", "Значение поля должно быть больше 0",
                (s) -> s != null && s > 0, this::readInt);
    }

    private VehicleType readVehicleType() {
        String vehicleTypeSTR = readArgWhile("Введите VehicleType: ", "Значение должно быть пустым или одним из: " + VehicleType.enumToStr(),
                (s) -> s != null && (s.isEmpty() || VehicleType.isType(s)), this::readStr);
        if (vehicleTypeSTR.isEmpty())
            return null;
        else return VehicleType.valueOf(vehicleTypeSTR);
    }

    private FuelType readFuelType() {
        String fuelTypeSTR = readArgWhile("Введите FuelType: ", "Значение должно быть пустым или одним из: " + FuelType.enumToStr(),
                (s) -> s != null && (s.isEmpty() || FuelType.isFuelType(s)), this::readStr);
        if (fuelTypeSTR.isEmpty())
            return null;
        else return FuelType.valueOf(fuelTypeSTR);
    }

    public Car readElement(UserToken user) {
        return new Car(readName(), readCoords(), new Date(), readEnginePower(), readVehicleType(), readFuelType(), user.getLogin());
    }
}
