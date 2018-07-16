package bankingsystem;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentProcesor {
    List<Payment> paymentList = null;
    List<CustomerAccount> customerAccount = null;

    public void loadPayments(String fileName) {
        try {
            CsvReader payments = new CsvReader(fileName);
            payments.readHeaders();
            paymentList = new ArrayList<>();
            while (payments.readRecord()) {
                String surname = payments.get("surname");
                String name = payments.get("name");
                Customer from = new Customer(surname, name);
                String IBAN = payments.get("IBAN");
                String amount = payments.get("amount");
                String comment = payments.get("comment");
                String TO_IBAN = payments.get("TO_IBAN");
                String beneficiaryName = payments.get("beneficiary name");
                String beneficiarySurname = payments.get("beneficiary surname");
                Customer to = new Customer(beneficiarySurname, beneficiaryName);
                paymentList.add(new Payment(from, to, IBAN, TO_IBAN, Double.parseDouble(amount), comment));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CustomerAccount(String fileName) {
        try {
            CsvReader customer = new CsvReader(fileName);
            customer.readHeaders();
            customerAccount = new ArrayList<>();
            while (customer.readRecord()) {
                String surname = customer.get("surname");
                String name = customer.get("name");
                Customer c = new Customer(surname, name);
                String IBAN = customer.get("IBAN");
                String amount = customer.get("amount");
                customerAccount.add(new CustomerAccount(c, IBAN, Double.parseDouble(amount)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PaymentRejection> procesPayment(){
        List<PaymentRejection> paymentRejections = new ArrayList<>();
        String validateCustomer;
        String validateAmount;
        for(Payment p:paymentList){
            validateCustomer = validateCustomer(p.getFrom());
            if(!validateCustomer.equals("OK")){
                paymentRejections.add(new PaymentRejection(p, validateCustomer));
                continue;
            }
            else{
                validateAmount = validateAmount(p.getIBAN_from(), p.getAmount());
                if(!validateAmount.equals("OK")){
                    paymentRejections.add(new PaymentRejection(p, validateAmount));
                    continue;
                }
            }
           proccessCustomerAccount(p);
        }
        return paymentRejections;
    }

    private void proccessCustomerAccount(Payment p) {
        for(CustomerAccount ca: customerAccount){
            if(ca.getIBAN().equals(p.getIBAN_from())){
                ca.setAmount(ca.getAmount()-p.getAmount());
            }
        }
    }

    private String validateCustomer(Customer from) {
        for(CustomerAccount ca:customerAccount){
            if(ca.getCustomer().equals(from)){
                return "OK";
            }
        }
        return "false customer";
    }

    private String validateAmount(String IBAN, double amount) {
        for(CustomerAccount ca:customerAccount){
            if(ca.getIBAN().equals(IBAN) && ca.getAmount()>=amount){
                return "OK";
            }
        }
        return "big amount";
    }
}
