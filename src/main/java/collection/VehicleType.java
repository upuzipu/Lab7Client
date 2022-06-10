package collection;

public enum VehicleType {
    PLANE,
    CHOPPER,
    HOVERBOARD;

    public static boolean isType(String s){
        try {
            VehicleType.valueOf(s);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    public static String enumToStr(){
        StringBuilder s = new StringBuilder();
        for (VehicleType o: VehicleType.values())
            s.append(o.name()).append(" ");
        return s.toString();
    }
}
