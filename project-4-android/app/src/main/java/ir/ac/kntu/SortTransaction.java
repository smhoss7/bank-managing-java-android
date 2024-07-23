package ir.ac.kntu;

import java.util.Comparator;

public class SortTransaction implements Comparator<Transaction> {
    @Override
    public int compare(Transaction object1, Transaction object2) {
        if (object1.getTime().isAfter(object2.getTime())) {
            return -10;
        }
        if (object1.getTime().isBefore(object2.getTime())) {
            return 20;
        }
        return 0;
    }
}
