package bankingsystem;

import java.util.List;

public class test {
    public static void main(String[] args) {
        PaymentProcesor pp = new PaymentProcesor();
        pp.CustomerAccount("D:\\customer.csv");
        pp.loadPayments("D:\\requestsCSV.csv");
        List<PaymentRejection> rejectList= pp.procesPayment();
        for(PaymentRejection pj:rejectList){
            System.out.println(pj.toString());
        }

    }
}
