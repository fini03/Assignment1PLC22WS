import java.util.*;
import java.io.Serializable;

public abstract class Vehicle implements Serializable {

	private final String brand;
	private final String model;
	private final int year_built;
	private final double base_price;
	private final int id;
	
	public Vehicle(String brand, String model, int year_built, double base_price, int id) {
		if(Calendar.getInstance().get(Calendar.YEAR) < year_built) throw new IllegalArgumentException("Year built invalid.");
		if(base_price < 0) throw new IllegalArgumentException("Base price invalid.");
		if(brand.isEmpty() || model.isEmpty()) throw new IllegalArgumentException("Invalid parameter.");
		this.brand = brand;
		this.model = model;
		this.year_built = year_built;
		this.base_price = base_price;
		this.id = id;
	}

	public int getAge() {
		return Calendar.getInstance().get(Calendar.YEAR) - year_built;
	}
	public String getBrand() {
		return brand;
	}
	public String getModel() {
		return model;
	}
	public int getYearBuilt() {
		return year_built;
	}
	public double getBasePrice() {
		return base_price;
	}
	public int getId() {
		return id;
	}
	
	public abstract double getDiscount();
	public abstract String getType();

	public double getPrice() {
		return base_price - getDiscount();
	}
}
