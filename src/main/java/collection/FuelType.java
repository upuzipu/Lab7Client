package collection;

public enum FuelType {
    GASOLINE,
    DIESEL,
    MANPOWER,
    ANTIMATTER;

    public static boolean isFuelType(String s){
        try {
            FuelType.valueOf(s);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
    public static String enumToStr(){
        StringBuilder s = new StringBuilder();
        for (FuelType o: FuelType.values())
            s.append(o.name()).append(" ");
        return s.toString();
    }
}
