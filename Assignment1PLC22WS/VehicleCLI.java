/**
 * @author Lamies Abbas
 * @id 12128050
 */

public class VehicleCLI {

	private enum Commands {
		SHOW("show"),
		ADD("add"),
		DEL("del"),
		COUNT("count"),
		MEANPRICE("meanprice"),
		OLDEST("oldest");

		private final String cmd;

		Commands(String cmd) {
			this.cmd = cmd;
		}

		public static Commands forCmd(String cmd) {
			for (Commands c : values()) {
				if (c.cmd.equals(cmd)) {
					return c;
				}
			}
			throw new IllegalArgumentException("Invalid parameter.");
		}
	}

	public static void main(String[] args) {
		try {
			if(args.length < 2) {
				throw new IllegalArgumentException("Invalid parameter.");
			}
			String filename = args[0];
			VehicleManagement vehicleManagement = new VehicleManagement(filename);
			Commands cmd = Commands.forCmd(args[1]);
			switch (cmd) {
				case SHOW:
				vehicleManagement.show(args);
					break;
				case ADD:
				vehicleManagement.add(args);
					break;
				case DEL:
				vehicleManagement.del(args);
					break;
				case COUNT:
				vehicleManagement.count(args);
					break;
				case MEANPRICE:
				vehicleManagement.meanprice();
					break;
				case OLDEST:
				vehicleManagement.oldest();
					break;
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage()); 
		}
	}
}