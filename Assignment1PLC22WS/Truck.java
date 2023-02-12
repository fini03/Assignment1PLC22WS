/**
 * @author Lamies Abbas
 * @id 12128050
 */


public class Truck extends Vehicle {

    public Truck(String brand, String model, int year_built, double base_price, int id) {
        super(brand, model, year_built, base_price, id);
    }

    @Override
    public double getDiscount() {
        double res = 0.05 * this.getAge();
        if(res > 0.2) {
            res = 0.2;
        }
        return res * this.getBasePrice();
    }

    @Override
    public String getType() {
        return "Truck";
    }
}