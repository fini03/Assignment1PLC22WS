import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
/**
 * @author Lamies Abbas
 * @id 12128050
 */

public class VehicleManagement {

    private final VehicleDAO vehicleDAO;

    public VehicleManagement(String filename) {
        this.vehicleDAO = new SerializedVehicleDAO(filename);
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }
    public void show(String[] args) {
		if (args.length <= 2) {
			for (Vehicle v : vehicleDAO.getVehicleList()) {
				print(v);
			}
		} else {
			Vehicle v = vehicleDAO.getVehicle(Integer.parseInt(args[2]));
            if (v != null) {
               print(v);
            }
		}
	}
    public void add(String[] args) {
        try {
            if(args[2].equals("car")) {
                if(args.length != 9) {
                    throw new IllegalArgumentException("Invalid parameter.");
                }
                try {
                    int id = Integer.parseInt(args[3]);
                    String brand = args[4];
                    String model = args[5];
                    int year_built = Integer.parseInt(args[6]);
                    double base_price = Double.parseDouble(args[7]);
                    int inspection = Integer.parseInt(args[8]);
                    Vehicle vehicle = new Car(brand, model, year_built, base_price, id, inspection);
                    vehicleDAO.saveVehicle(vehicle);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid parameter.");
                }
            } else if(args[2].equals("truck")) {
                if(args.length != 8) {
                    throw new IllegalArgumentException("Invalid parameter.");
                }
                try {
                    int id = Integer.parseInt(args[3]);
                    String brand = args[4];
                    String model = args[5];
                    int year_built = Integer.parseInt(args[6]);
                    double base_price = Double.parseDouble(args[7]);
                    Vehicle vehicle = new Truck(brand, model, year_built, base_price, id);
                    vehicleDAO.saveVehicle(vehicle);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid parameter.");
                }
            } else {
                throw new IllegalArgumentException("Invalid parameter.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); 
        }
	}

	public void del(String[] args) {
		int id = Integer.parseInt(args[2]);
		vehicleDAO.deleteVehicle(id);
	}

	public void count(String[] args) {
        try {
            if(args.length <= 2) {
                System.out.println(vehicleDAO.getVehicleList().size());
            } else {
                if(args[2].equalsIgnoreCase("car")) {
                    System.out.println(vehicleDAO.getVehicleList().stream().filter(v -> v.getType().equalsIgnoreCase("car")).count());
                } else if(args[2].equalsIgnoreCase("truck")) {
                    System.out.println(vehicleDAO.getVehicleList().stream().filter(v -> v.getType().equalsIgnoreCase("truck")).count());
                } else {
                    throw new IllegalArgumentException("Invalid parameter.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); 
        }
	}

	public void meanprice() {
		double meanprice = vehicleDAO.getVehicleList().stream().mapToDouble(x -> x.getPrice()).sum();
		meanprice = meanprice/vehicleDAO.getVehicleList().size();
		DecimalFormat df = getDecimalFormat();
        System.out.println(df.format(meanprice));
	}

	public void oldest() {
        int date = vehicleDAO.getVehicleList().stream().max((x,y) -> Integer.compare(x.getAge(), y.getAge())).get().getYearBuilt();
        for (Vehicle v : vehicleDAO.getVehicleList().stream().filter(x -> x.getYearBuilt() == date).collect(Collectors.toList())) {
			System.out.println("Id: " + v.getId());
		}	
    }
	
	
    public static DecimalFormat getDecimalFormat() {
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		return new DecimalFormat("0.00", dfs);
	}

    private String format(String print) {
		String spaces = "";
		for(int i = 0; i < 11 - print.length(); i++) {
			spaces += " ";
		}
		return print + ':' + spaces;
	}

    private void print(Vehicle vehicle) {
        DecimalFormat df = getDecimalFormat();
		StringBuilder builder = new StringBuilder();
		builder.append(format("Type")).append(vehicle.getType()).append("\n");
		builder.append(format("Id")).append(vehicle.getId()).append("\n");
		builder.append(format("Brand")).append(vehicle.getBrand()).append("\n");
		builder.append(format("Model")).append(vehicle.getModel()).append("\n");
		builder.append(format("Year")).append(vehicle.getYearBuilt()).append("\n");
		if(vehicle.getType().equalsIgnoreCase("car")) {
			builder.append(format("Inspection")).append(((Car)vehicle).getYearOfLastInspection()).append("\n");
        }
		builder.append(format("Base price")).append(df.format(vehicle.getBasePrice())).append("\n");
		builder.append(format("Price")).append(df.format(vehicle.getPrice())).append("\n");
		System.out.println(builder.toString());
	}
}