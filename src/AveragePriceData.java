
public class AveragePriceData {

	// list of all attributes for a single date value
	public double average_price;
	public double monthly_change;
	public double annual_change;
	public double annual_price_sa;

	/**
	 * @param average_price
	 * @param monthly_change
	 * @param annual_change
	 * @param annual_price_sa
	 */
	public AveragePriceData(double average_price, double monthly_change, double annual_change, double annual_price_sa) {
		super();
		this.average_price = average_price;
		this.monthly_change = monthly_change;
		this.annual_change = annual_change;
		this.annual_price_sa = annual_price_sa;
	}

	@Override
	public String toString() {
		return " AveragePriceData [average_price=" + average_price + ", monthly_change=" + monthly_change
				+ ", annual_change=" + annual_change + ", annual_price_sa=" + annual_price_sa + "] ";
	}

}
