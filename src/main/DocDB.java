package main;

import com.google.gson.*;
import java.io.*;

public class DocDB {

	private static String databasePath;
	public static JsonObject database;

	public static void main (String[] args) {
		// Initialize the JSON database
		String databasePath = "/home/lg/database_example.json";
		JsonObject database = initializeDatabase (databasePath);

		// Add new objects (e.g., empresa, conductor, or vehiculo)
//		addEmpresa (database, "1", "Tipo1", "Empresa1", "Address1", "City1", "Country1");
//		addEmpresa (database, "2", "Tipo2", "Empresa2", "Address2", "City2", "Country2");
		addConductor (database, "1", "Nombre1", "Nationality1", "License1", "NewLibreta");
		addConductor (database, "2", "Nombre2", "Nationality2", "License2", "NewLibreta");

		addVehiculo (database, "ABC123", "Brand1", "Country1", "2020", "Chassis1");
		addVehiculo (database, "XYZ789", "Brand2", "Country2", "2021", "Chassis2");

		// Update an existing object by key (empresa's id, conductor's id, vehiculo's placa)
//		updateEmpresa (database, "1", "NewName", "NewAddress");
		updateConductor (database, "1", "NewName", "NewNationality", "NewLicense", "NewLibreta");
		updateVehiculo (database, "ABC123", "NewBrand", "Colombia", "2022", "100012");

		// Search for an object by key
		JsonObject foundEmpresa = searchEmpresa (database, "1");
		JsonObject foundConductor = searchConductor (database, "1");
		JsonObject foundVehiculo = searchVehiculo (database, "ABC123");

		// Remove an object by key
		//removeEmpresa(database, "1");
		//removeConductor(database, "1");
		//removeVehiculo(database, "ABC123");
		// Save the updated database back to the JSON file
		saveDatabase (database);
	}

	public static JsonObject initializeDatabase (String databasePath) {
		DocDB.databasePath = databasePath;
		try {
			File file = new File (DocDB.databasePath);
			if (file.exists ()) {
				System.out.println (">>> Cargando base de datos..");
				FileReader reader = new FileReader (file);
				JsonParser parser = new JsonParser ();
				database = parser.parse (reader).getAsJsonObject ();
			} else {
				System.out.println (">>> Creando base de datos..");
				database = new JsonObject ();
			}
		} catch (IOException e) {
			e.printStackTrace ();
		}
		return (database);
	}

	public static void saveDatabase (JsonObject database) {
		try (FileWriter writer = new FileWriter (DocDB.databasePath)) {
			Gson gson = new GsonBuilder ().setPrettyPrinting ().create ();
			gson.toJson (database, writer);
		} catch (IOException e) {
			e.printStackTrace ();
		}
	}

	// Search for an existing empresa object by id
	public static JsonObject searchEmpresa (JsonObject database, String nombre) {
		JsonArray empresasArray = database.getAsJsonArray ("empresa");
		if (empresasArray != null)
			for (JsonElement element : empresasArray) {
				JsonObject empresa = element.getAsJsonObject ();
				if (empresa.get ("nombre").getAsString ().equals (nombre))
					return empresa;
			}
		return null;
	}

	// Add a new empresa object
	public static void addEmpresa (String nombre, String direccion, String ciudad, String pais, String tipoId, String id) {
		JsonObject empresa = new JsonObject ();
		empresa.addProperty ("nombre", nombre);
		empresa.addProperty ("direccion", direccion);
		empresa.addProperty ("ciudad", ciudad);
		empresa.addProperty ("pais", pais);
		empresa.addProperty ("tipo_id", tipoId);
		empresa.addProperty ("id", id);

		JsonArray empresasArray = (JsonArray) database.getAsJsonArray ("empresa");
		if (empresasArray == null) {
			empresasArray = new JsonArray ();
			database.add ("empresa", empresasArray);
		}
		empresasArray.add (empresa);
		saveDatabase (database);
	}

	// Update an existing empresa object by id
	public static void updateEmpresa (String nombre, String direccion, String ciudad, String pais, String id, String tipoId) {
		JsonArray empresasArray = database.getAsJsonArray ("empresa");
		if (empresasArray != null)
			for (JsonElement element : empresasArray) {
				JsonObject empresa = element.getAsJsonObject ();
				if (empresa.get ("nombre").getAsString ().equals (nombre)) {
					empresa.addProperty ("nombre", nombre);
					empresa.addProperty ("direccion", direccion);
					empresa.addProperty ("ciudad", ciudad);
					empresa.addProperty ("pais", pais);
					empresa.addProperty ("id", id);
					empresa.addProperty ("tipo_id", tipoId);
				}
			}
	}

	// Add a new conductor object
	public static void addConductor (JsonObject database, String id, String nombre, String nacionalidad, String licencia, String libreta) {
		JsonObject conductor = new JsonObject ();
		conductor.addProperty ("id", id);
		conductor.addProperty ("nombre", nombre);
		conductor.addProperty ("nacionalidad", nacionalidad);
		conductor.addProperty ("licencia", licencia);
		conductor.addProperty ("libreta", libreta);

		JsonArray conductoresArray = database.getAsJsonArray ("conductor");
		if (conductoresArray == null) {
			conductoresArray = new JsonArray ();
			database.add ("conductor", conductoresArray);
		}
		conductoresArray.add (conductor);
		saveDatabase (database);
	}

	// Add a new vehiculo object
	private static void addVehiculo (JsonObject database, String placa, String marca, String pais, String fabricacion, String nroChasis) {
		JsonObject vehiculo = new JsonObject ();
		vehiculo.addProperty ("placa", placa);
		vehiculo.addProperty ("marca", marca);
		vehiculo.addProperty ("pais", pais);
		vehiculo.addProperty ("fabricacion", fabricacion);
		vehiculo.addProperty ("chasis", nroChasis);

		JsonArray vehiculosArray = database.getAsJsonArray ("vehiculo");
		if (vehiculosArray == null) {
			vehiculosArray = new JsonArray ();
			database.add ("vehiculo", vehiculosArray);
		}
		vehiculosArray.add (vehiculo);
		saveDatabase (database);
	}

	// Update an existing conductor object by id
	public static void updateConductor (JsonObject database, String id, String nombre, String nacionalidad, String licencia, String libreta) {
		JsonArray conductoresArray = database.getAsJsonArray ("conductor");
		if (conductoresArray != null)
			for (JsonElement element : conductoresArray) {
				JsonObject conductor = element.getAsJsonObject ();
				if (conductor.get ("id").getAsString ().equals (id)) {
					conductor.addProperty ("nombre", nombre);
					conductor.addProperty ("nacionalidad", nacionalidad);
					conductor.addProperty ("licencia", licencia);
					conductor.addProperty ("libreta", libreta);
					DocDB.saveDatabase (database);
					return;
				}
			}
		DocDB.addConductor (database, id, nombre, nacionalidad, licencia, libreta);
	}

	// Update an existing vehiculo object by placa
	public static void updateVehiculo (JsonObject database, String placa, String marca, String pais, String fabricacion, String nroChasis) {
		JsonArray vehiculosArray = database.getAsJsonArray ("vehiculo");
		if (vehiculosArray != null)
			for (JsonElement element : vehiculosArray) {
				JsonObject vehiculo = element.getAsJsonObject ();
				if (vehiculo.get ("placa").getAsString ().equals (placa)) {
					vehiculo.addProperty ("marca", marca);
					vehiculo.addProperty ("pais", pais);
					vehiculo.addProperty ("fabricacion", fabricacion);
					vehiculo.addProperty ("chasis", nroChasis);
					saveDatabase (database);
					return;
				}
			}
		addVehiculo (database, placa, marca, pais, fabricacion, nroChasis);
	}

	// Search for an existing conductor object by id
	public static JsonObject searchConductor (JsonObject database, String id) {
		JsonArray conductoresArray = database.getAsJsonArray ("conductor");
		if (conductoresArray != null)
			for (JsonElement element : conductoresArray) {
				JsonObject conductor = element.getAsJsonObject ();
				if (conductor.get ("id").getAsString ().equals (id))
					return conductor;
			}
		return null;
	}

	// Search for an existing vehiculo object by placa
	public static JsonObject searchVehiculo (JsonObject database, String placa) {
		JsonArray vehiculosArray = database.getAsJsonArray ("vehiculo");
		if (vehiculosArray != null)
			for (JsonElement element : vehiculosArray) {
				JsonObject vehiculo = element.getAsJsonObject ();
				if (vehiculo.get ("placa").getAsString ().equals (placa))
					return vehiculo;
			}
		return null;
	}

	// Remove an existing empresa object by id
	private static void removeEmpresa (JsonObject database, String id) {
		JsonArray empresasArray = database.getAsJsonArray ("empresa");
		if (empresasArray != null)
			for (int i = 0; i < empresasArray.size (); i++) {
				JsonObject empresa = empresasArray.get (i).getAsJsonObject ();
				if (empresa.get ("id").getAsString ().equals (id)) {
					empresasArray.remove (i);
					break; // Remove the first matching element
				}
			}
	}

	// Remove an existing conductor object by id
	private static void removeConductor (JsonObject database, String id) {
		JsonArray conductoresArray = database.getAsJsonArray ("conductor");
		if (conductoresArray != null)
			for (int i = 0; i < conductoresArray.size (); i++) {
				JsonObject conductor = conductoresArray.get (i).getAsJsonObject ();
				if (conductor.get ("id").getAsString ().equals (id)) {
					conductoresArray.remove (i);
					break; // Remove the first matching element
				}
			}
	}

	// Remove an existing vehiculo object by placa
	private static void removeVehiculo (JsonObject database, String placa) {
		JsonArray vehiculosArray = database.getAsJsonArray ("vehiculo");
		if (vehiculosArray != null)
			for (int i = 0; i < vehiculosArray.size (); i++) {
				JsonObject vehiculo = vehiculosArray.get (i).getAsJsonObject ();
				if (vehiculo.get ("placa").getAsString ().equals (placa)) {
					vehiculosArray.remove (i);
					break; // Remove the first matching element
				}
			}
	}

	public static int getNextNroDocumento (String doctype) {
		JsonObject consecutivos = (JsonObject) database.get ("consecutivo");
		if (consecutivos == null) {
			consecutivos = new JsonObject ();
			consecutivos.addProperty ("nro_cartaporte", 1);
			consecutivos.addProperty ("nro_manifiesto", 1);
			consecutivos.addProperty ("nro_declaracion", 1);
		}
		
		int number = -1;
		switch (doctype) {
			case "cartaporte":
				number = consecutivos.get ("nro_cartaporte").getAsInt ();
				consecutivos.addProperty ("nro_cartaporte", number + 1);
				break;
			case "manifiesto":
				number = consecutivos.get ("nro_manifiesto").getAsInt ();
				consecutivos.addProperty ("nro_manifiesto", number + 1);
				break;
			case "declaracion":
				number = consecutivos.get ("nro_declaracion").getAsInt ();
				consecutivos.addProperty ("nro_declaracion", number + 1);
				break;
		}
		saveDatabase (database);

		return number;
	}

// Get value from JsonObject result
	public static String getValue (JsonObject result, String key) {
		if (result.get (key) == null)
			return "";
		else
			return result.get (key).getAsString ();
	}
}
