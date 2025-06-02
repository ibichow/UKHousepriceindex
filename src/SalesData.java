
public class SalesData {

	// List of attributes for sales data
	public int sales_volume;

	/**
	 * @param sales_volume
	 */
	public SalesData(int sales_volume) {
		super();
		this.sales_volume = sales_volume;
	}

	@Override
	public String toString() {
		return "SalesData [sales_volume=" + sales_volume + "]";
	}

}
