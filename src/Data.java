import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

	// List of attributes for region and area
	public String region_name;
	public String area_code;
	public List<Sale> sale;

	/**
	 * @param region_name
	 * @param area_code
	 */
	public Data(String region_name, String area_code) {
		super();
		this.region_name = region_name;
		this.area_code = area_code;

		sale = new ArrayList<>();
	}

	// Add new sale
	public void AddSale(Sale s) {
		// if sale list does not contain value for given date, add to list
		if (findSale(s.date) < 0)
			sale.add(s);
	}

	public void printLatestData() {
		System.out.println("Data [region_name=" + region_name + ", area_code=" + area_code + "]");
		int i = 1;
		for (Sale s : sale) {
			System.out.print(i++ + ": ");
			s.printLatestSalesData();
		}
	}

	// function to calculate average price difference between two sales
	public double calculateAveragePriceDifference(int from, int to) {
		// get both average prices
		double from_average_price = sale.get(from).averagePriceData.average_price;
		double to_average_price = sale.get(to).averagePriceData.average_price;

		// calculate difference in percentage and return result
		double diff = (to_average_price - from_average_price) / from_average_price * 100;
		return diff;
	}

	// function to calculate average price difference between two sales
	public double calculateHousePrice(int from, int to, double from_average_price) {
		// get relevant data
		// double from_average_price = sale.get(from).averagePriceData.average_price;
		double from_index = sale.get(from).indexData.index;
		double to_index = sale.get(to).indexData.index;

		// calculate house price and return result
		double house_price = ((to_index - from_index) / 100 * from_average_price) + from_average_price;
		return house_price;
	}

	public void printData() {
		System.out.println("Data [region_name=" + region_name + ", area_code=" + area_code + "]");
		int i = 1;
		for (Sale s : sale) {
			System.out.print(i++ + ": ");
			s.printSales();
		}
	}

	// find and get function
	public int findSale(String date) {
		for (int i = 0; i < sale.size(); i++) {
			if (sale.get(i).date.equals(date))
				return i;
		}
		return -1;
	}
}
