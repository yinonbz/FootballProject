package businessLayer.Utilities.Financial;

public class TaxSystem implements FinancialInterface {
    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        return false;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        if (revenueAmount < 15000) {
            return 0.1;
        } else if (revenueAmount > 15000 && revenueAmount < 53000) {
            return 0.12;
        } else if (revenueAmount > 53000 && revenueAmount < 85500) {
            return 0.22;
        } else if (revenueAmount > 85500 && revenueAmount < 163000) {
            return 0.24;
        }else if(revenueAmount>163000&&revenueAmount<207350){
            return 0.24;
        }else if(revenueAmount>207350&&revenueAmount<518000){
            return 0.35;
        }else if(revenueAmount>518000){
            return 0.37;
        }return 0;

    }
}
