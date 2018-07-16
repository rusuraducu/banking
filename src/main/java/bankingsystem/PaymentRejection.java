package bankingsystem;

public class PaymentRejection extends Payment {
    private String rejectReason;

    public PaymentRejection(Customer from, Customer to, String IBAN_from, String IBAN_to, double amount, String comment, String rejectReason) {
        super(from, to, IBAN_from, IBAN_to, amount, comment);
        this.rejectReason = rejectReason;
    }

    public PaymentRejection(Payment p, String rejectReason) {
        super(p.getFrom(), p.getTo(), p.getIBAN_from(), p.getIBAN_to(), p.getAmount(), p.getComment());
        this.rejectReason = rejectReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    @Override
    public String toString() {
        return "PaymentRejection{" + super.toString()+
                "rejectReason='" + rejectReason + '\'' +
                '}';
    }
}
