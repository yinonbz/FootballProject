package businessLayer.Utilities.Financial;

public interface FinancialInterface {
    boolean addPayment(String teamName, String date, double amount);
    double getTaxRate(double revenueAmount);
}
