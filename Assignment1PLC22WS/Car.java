import java.util.*;

public class Car extends Vehicle {
	
    private final int year_of_last_inspection;

    public Car(String brand, String model, int year_built, double base_price, int id, int year_of_last_inspection) {
        super(brand, model, year_built, base_price, id);
        if(Calendar.getInstance().get(Calendar.YEAR) < year_of_last_inspection) throw new IllegalArgumentException("Inspection year invalid.");
        this.year_of_last_inspection = year_of_last_inspection;
    }

    public int getYearOfLastInspection() {
        return year_of_last_inspection;
    }
    @Override
    public double getDiscount() {
        double res = 0.05 * this.getAge() + 0.02 * (double) this.getYearOfLastInspection();
        if(res > 0.15) {
            res = 0.15;
        }
        return res * this.getBasePrice();
    }

    @Override
    public String getType() {
        return "Car";
    }
}
