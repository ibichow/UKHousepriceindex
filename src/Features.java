import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Features {

	// List of attributes
	public Map<String, Data> dataMap;
	final public static String average_prices_file = "data/average_prices.csv";
	final public static String sales_file = "data/sales.csv";
	final public static String index_file = "data/index.csv";

	Scanner sc;

	public Features() {
		sc = new Scanner(System.in);
		dataMap = new HashMap<>();
	}

	// add new region
	public void AddRegion(Data d) {
		// if region does not exist, add one
		if (!dataMap.containsKey(d.area_code))
			dataMap.put(d.area_code, d);
	}

	// function to display data for selected local authority.
	public void displayCompleteData() {
		// Get local authority
		System.out.print("Enter local authority name: ");
		String name = sc.next();
		// search area code
		String ac = getAreaCode(name);
		// if valid area code was returned
		if (dataMap.containsKey(ac)) {
			// display result
			dataMap.get(ac).printData();
		} else {
			System.out.println("No Matching result found...");
		}
	}

	// function to display data for selected local authority.
	// It is Basic Feature (A).
	public void displayLatestData() {
		// Get local authority
		System.out.print("Enter local authority name: ");
		String name = sc.next();
		// search area code
		String ac = getAreaCode(name);
		// if valid area code was returned
		if (dataMap.containsKey(ac)) {
			// display result
			dataMap.get(ac).printLatestData();
		} else {
			System.out.println("No Matching result found...");
		}
	}

	// function to calculate and display percentage change
	// It is Intermediate Feature (B).
	public void calculatePercentageChange() {
		// Get local authority
		System.out.print("Enter local authority name: ");
		String name = sc.next();
		System.out.print("");
		// search area code
		String ac = getAreaCode(name);
		// if valid area code was returned
		if (dataMap.containsKey(ac)) {
			// get "from" month/year
			System.out.print("Enter starting month (00 to 12): ");
			String from_month = sc.next();
			System.out.print("Enter starting year (4 digits): ");
			String from_year = sc.next();
			// get "to" month/year
			System.out.print("Enter ending month (00 to 12): ");
			String to_month = sc.next();
			System.out.print("Enter ending year (4 digits): ");
			String to_year = sc.next();

			// build date for "from"
			String from = from_year + "-" + from_month + "-" + "01";
			String to = to_year + "-" + to_month + "-" + "01";

			// get from and sale objects
			int from_sale = dataMap.get(ac).findSale(from);
			int to_sale = dataMap.get(ac).findSale(to);
			// System.out.println("Starting ind: "+ from_sale);
			// System.out.println("Ending ind: "+ to_sale);

			// if either from or sale is not found (mean invalid date was entered), display
			// error and return
			if (from_sale < 0 || to_sale < 0) {
				System.out.println("Sorry! sale record was not found...");
				return;
			}

			// get average_price difference for selected authority
			double average_price = dataMap.get(ac).calculateAveragePriceDifference(from_sale, to_sale);

			// Display average price up to 2 digits
			System.out.format("Average Sale Price Change in Percentage: %.2f \n", average_price);

		} else {
			System.out.println("No Matching result found...");
		}
	}

	// function to calculate and display percentage change
	// It is Intermediate Feature (B).
	public void calculateHousePriceIndex() {
		// Get local authority
		System.out.print("Enter local authority name: ");
		String name = sc.next();
		System.out.print("");
		// search area code
		String ac = getAreaCode(name);
		// if valid area code was returned
		if (dataMap.containsKey(ac)) {
			// get "from" month/year
			System.out.print("Enter starting month (00 to 12): ");
			String from_month = sc.next();
			System.out.print("Enter starting year (4 digits): ");
			String from_year = sc.next();
			// get "to" month/year
			System.out.print("Enter ending month (00 to 12): ");
			String to_month = sc.next();
			System.out.print("Enter ending year (4 digits): ");
			String to_year = sc.next();

			System.out.print("Enter Current House Price: ");
			double current_house_price = sc.nextDouble();

			// build date for "from"
			String from = from_year + "-" + from_month + "-" + "01";
			String to = to_year + "-" + to_month + "-" + "01";

			// get from and sale objects
			int from_sale = dataMap.get(ac).findSale(from);
			int to_sale = dataMap.get(ac).findSale(to);
			// System.out.println("Starting ind: "+ from_sale);
			// System.out.println("Ending ind: "+ to_sale);

			// if either from or sale is not found (mean invalid date was entered), display
			// error and return
			if (from_sale < 0 || to_sale < 0) {
				System.out.println("Sorry! record was not found...");
				return;
			}

			// get average_price difference for selected authority
			double average_price = dataMap.get(ac).calculateHousePrice(from_sale, to_sale, current_house_price);

			// Display average price up to 2 digits
			System.out.format("Average House Price in %s was/will be: %.4f \n", to, average_price);

		} else {
			System.out.println("No Matching result found...");
		}
	}

	// function to get matched authority by name
	public String getAreaCode(String name) {
		List<Data> search = new ArrayList<>();
		for (Data d : dataMap.values()) {
			if (d.region_name.toLowerCase().contains(name.toLowerCase())) {
				search.add(d);
			}
		}

		// if there was only one matching data, return area code of that region
		if (search.size() == 1) {
			return search.get(0).area_code;
		}
		// if multiple matches were found
		else if (search.size() > 1) {
			int i = 1;
			System.out.println("More than 1 areas were matched. Select one: ");
			// display all matching areas and ask user to chose one
			for (Data d : search) {
				System.out.println((i++) + ": " + d.region_name);
			}
			System.out.print("-> ");
			i = sc.nextInt();
			// return area code of matched index
			if (i > 0 && i <= search.size())
				return search.get(i - 1).area_code;
		}
		// return no match string
		return "none";
	}

	// Read data from Average Price CSV file
	public void ReadDataFromAveragePriceFile() {
		String line = null;
		try {
			System.out.println("Getting data, please wait...");
			File file = new File(average_prices_file);
			Scanner sc = new Scanner(file);

			// read the first line and discard (this is the header)
			sc.nextLine();

			// reading file line by line
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				// split line by comma
				String[] params = cleanData(line);
				// create a new data object
				Data data = new Data(params[1], params[2]);
				// add data object to data map
				AddRegion(data);
				// Create a new Average Price Object
				AveragePriceData apd = new AveragePriceData(toDouble(params[3]), toDouble(params[4]),
						toDouble(params[5]), toDouble(params[6]));
				// Create a new sale object
				Sale sale = new Sale(params[0]);
				// Add Average price object to sale
				sale.averagePriceData = apd;
				// Add sale object to added region
				dataMap.get(params[2]).AddSale(sale);
			}
		} catch (FileNotFoundException ex) {
			System.out.print(ex);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println(line);
			// System.out.println(Arrays.toString(line.split(",")));
		}
		System.out.println("TOTAL READ DATA: " + dataMap.get("K04000001").sale.size());
	}

	// Read data from Sales CSV file
	public void ReadDataFromSalesFile() {
		String line = null;
		try {
			System.out.println("Getting data, please wait...");
			File file = new File(sales_file);
			Scanner sc = new Scanner(file);

			// read the first line and discard (this is the header)
			sc.nextLine();

			// reading file line by line
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				// split line by comma
				String[] params = cleanData(line);
				// create a new sale data object
				SalesData salesData = new SalesData(toInteger(params[3]));
				// If date is not set, create a new sale object and set it
				if (dataMap.get(params[2]).findSale(params[0]) < 0) {
					// Create a new sale object
					Sale sale = new Sale(params[0]);
					// Add sale object to added region
					dataMap.get(params[2]).AddSale(sale);
				}
				// get sales object from sales list for given date and set sales data in it.
				int i = dataMap.get(params[2]).findSale(params[0]);
				dataMap.get(params[2]).sale.get(i).salesData = salesData;

			}
		} catch (FileNotFoundException ex) {
			System.out.print(ex);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println(line);
			// System.out.println(Arrays.toString(line.split(",")));
		}
		System.out.println("TOTAL READ SALES DATA: " + dataMap.get("K04000001").sale.size());
	}

	// Read data from Index CSV file. It works similar to Sales CSV function
	public void ReadDataFromIndexFile() {
		String line = null;
		try {
			System.out.println("Getting data, please wait...");
			File file = new File(index_file);
			Scanner sc = new Scanner(file);

			// read the first line and discard (this is the header)
			sc.nextLine();

			// reading file line by line
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				// split line by comma
				String[] params = cleanData(line);
				// create a new Index data object
				IndexData indexData = new IndexData(toDouble(params[3]));
				// If date is not set, create a new sale object and set it
				if (dataMap.get(params[2]).findSale(params[0]) < 0) {
					// Create a new sale object
					Sale sale = new Sale(params[0]);
					// Add sale object to added region
					dataMap.get(params[2]).AddSale(sale);
				}
				// get sales object from sales map for given date and set sales data in it.
				int i = dataMap.get(params[2]).findSale(params[0]);
				dataMap.get(params[2]).sale.get(i).indexData = indexData;

			}
		} catch (FileNotFoundException ex) {
			System.out.print(ex);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println(line);
			// System.out.println(Arrays.toString(line.split(",")));
		}
		System.out.println("TOTAL READ Index DATA: " + dataMap.get("K04000001").sale.size());
	}

	// function to clean read data by removing leading and trailing spaces
	public String[] cleanData(String line) {
		String[] params = line.split(",", -1);
		for (int i = 0; i < params.length; i++)
			params[i] = params[i].trim();
		return params;
	}

	// function to convert string to double
	public double toDouble(String s) {
		try {
			double v = Double.parseDouble(s);
			return v;
		} catch (Exception e) {
			return 0;
		}

	}

	// function to convert string to Integer
	public int toInteger(String s) {
		try {
			int v = Integer.parseInt(s);
			return v;
		} catch (Exception e) {
			return 0;
		}

	}

	public void printDataMap() {
		for (Data d : dataMap.values()) {
			d.printData();
		}
	}

	@Override
	public String toString() {
		return "Features [data=" + dataMap + "]";
	}

}
