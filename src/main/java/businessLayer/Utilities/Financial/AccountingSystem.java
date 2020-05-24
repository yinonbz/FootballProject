package businessLayer.Utilities.Financial;

public class AccountingSystem implements FinancialInterface {
    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        return amount>0;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        return 0;
    }
}
