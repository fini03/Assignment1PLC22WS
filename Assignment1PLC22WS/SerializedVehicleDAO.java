/**
 * @author Lamies Abbas
 * @id 12128050
 */
import java.io.*;
import java.util.*;

public class SerializedVehicleDAO implements VehicleDAO {
    
    private final String filename;

    public SerializedVehicleDAO(String filename) {
        this.filename = filename;
    }
    
    @SuppressWarnings("unchecked")
    private List<Vehicle> deserializeVehicles() {
        File file = new File(filename);
        List<Vehicle> vehicles = new ArrayList<>();        
        if(!file.exists()) return vehicles;

        try {
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(filename));
            vehicles = (List<Vehicle>) reader.readObject(); //unchecked
            reader.close();
        } catch (IOException ioe) {
            System.err.println("Error during deserialization: " + ioe.getMessage());
            System.exit(1);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error during deserialization: " + cnfe.getMessage());
            System.exit(1);
        } finally {}
        return vehicles;
    }

    @Override
    public List<Vehicle> getVehicleList() {
        return deserializeVehicles();
    }

    @Override
	public Vehicle getVehicle(int id) {
        for(Vehicle v : this.getVehicleList()) {
            if(v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    @Override
	public void saveVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = this.getVehicleList();
        if(vehicles.stream().anyMatch(x -> x.getId() == vehicle.getId())) {
            throw new IllegalArgumentException("Vehicle already exists. (id=" + vehicle.getId() + ")");
        } else {
            vehicles.add(vehicle);
            serializeVehicles(vehicles);
        }
    }

    @Override
	public void deleteVehicle(int id) {
        List<Vehicle> vehicles = this.getVehicleList();
        for(int i = 0; i < vehicles.size(); i++) {
            if(vehicles.get(i).getId() == id) {
                vehicles.remove(i);
                serializeVehicles(vehicles);
                return;
            }
        }
        throw new IllegalArgumentException("Vehicle not found. (id=" + id + ")");
    }

    private void serializeVehicles(List<Vehicle> vehicles) {
        File file = new File(filename);
        if(file.exists()) {file.delete();}
        try {
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename, true));
            writer.writeObject(vehicles);
            writer.close();
        } catch (IOException ioe) {
            System.err.println("Error during serialization: " + ioe.getMessage());
            System.exit(1);
        } finally {}
    }
}