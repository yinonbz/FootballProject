package businessLayer.Utilities.Financial;

public class FinancialProxy implements FinancialInterface {
    private AccountingSystem accountingSystem;
    private TaxSystem taxSystem;
    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        accountingSystem = new AccountingSystem();
        return accountingSystem.addPayment(teamName,date,amount);
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        taxSystem = new TaxSystem();
        return taxSystem.getTaxRate(revenueAmount);
    }
}
