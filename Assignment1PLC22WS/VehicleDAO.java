import java.util.List;

public interface VehicleDAO {

	public List<Vehicle> getVehicleList();

	public Vehicle getVehicle(int id);

	public void saveVehicle(Vehicle vehicle);

	public void deleteVehicle(int id);
}