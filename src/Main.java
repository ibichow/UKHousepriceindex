import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Features f = new Features();
		f.ReadDataFromAveragePriceFile();
		f.ReadDataFromSalesFile();
		f.ReadDataFromIndexFile();

		Scanner sc = new Scanner(System.in);
		String op = "0";
		do {
			System.out.println("-".repeat(60));
			System.out.println("Menu");
			System.out.println("0. Display Complete data");
			System.out.println("1. Display latest data");
			System.out.println("2. Percentage Change");
			System.out.println("3. House Price Index");
			System.out.println("4. Close");

			op = sc.nextLine();
			switch (op) {
			case "0":
				f.displayCompleteData();
				break;
			case "1":
				f.displayLatestData();
				break;
			case "2":
				f.calculatePercentageChange();
				break;
			case "3":
				f.calculateHousePriceIndex();
				break;
			case "4":
				System.out.println("Closing...");
				return;
			default:
				System.out.println("ERROR! Invalid input." + op);

			}
		} while (!op.equals("4"));

	}

}
