
public class Sale {

	// List of attributes for sale data
	public String date;
	public AveragePriceData averagePriceData;
	public SalesData salesData;
	public IndexData indexData;

	/**
	 * @param date
	 * @param property
	 */
	public Sale(String date) {
		super();
		this.date = date;
	}

	public void printLatestSalesData() {
		System.out.print("[date=" + date + "]");
		if (averagePriceData != null)
			System.out.print(" {Average Price: " + averagePriceData.average_price);
		if (salesData != null)
			System.out.print(", Sales Volume: " + salesData.sales_volume);
		System.out.println("}");
	}

	public void printSales() {
		System.out.print("[date=" + date + "]");
		if (averagePriceData != null)
			System.out.print(averagePriceData.toString());
		if (salesData != null)
			System.out.print(salesData.toString());
		if (indexData != null)
			System.out.print(indexData.toString());
		System.out.println();
	}

	@Override
	public String toString() {
		return "Sale [date=" + date + ", averagePriceData=" + averagePriceData + ", salesData=" + salesData
				+ ", indexData=" + indexData + "]";
	}

}
