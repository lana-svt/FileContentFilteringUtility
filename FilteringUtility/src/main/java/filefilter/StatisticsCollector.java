package filefilter;

public class StatisticsCollector {
    private int count = 0, countInt = 0, countDouble = 0;
    private double sumDouble = 0, minDouble = Double.MAX_VALUE, maxDouble = Double.MIN_VALUE;
    private long sumInt = 0L, minInt = Long.MAX_VALUE, maxInt = Long.MIN_VALUE;
    private int minLength = Integer.MAX_VALUE, maxLength = 0;

    public void addDoubleNumber(double value) {
        count++;
        countDouble++;
        sumDouble += value;
        minDouble = Math.min(minDouble, value);
        maxDouble = Math.max(maxDouble, value);
    }

    public void addIntegerNumber(long value) {
        count++;
        countInt++;
        try {
            sumInt = Math.addExact(sumInt, value);
        } catch (ArithmeticException e) {
            System.err.println("Overflow when adding a number " + value);
        }
        minInt = Math.min(minInt, value);
        maxInt = Math.max(maxInt, value);
    }

    public void addString(String value) {
        count++;
        int length = value.length();
        minLength = Math.min(minLength, length);
        maxLength = Math.max(maxLength, length);
    }

    public String getShortStatistics() {
        return String.format("Number of entries: %d\n", count);
    }

    public String getFullStatistics() {
        if(count == 0) return "Number of entries: 0";
        String result = String.format("Number of entries: %d\n", count);
        if (minLength != Integer.MAX_VALUE) {
            result +=  String.format("Minimum line length : %d; Maximum line length: %d\n", minLength, maxLength);
        }
        if (minDouble != Double.MAX_VALUE) {
            result += String.format("Minimum fractional number: %.2f; Maximum fractional number: %.2f; Sum: %.2f; Average: %.2f\n",
                    minDouble, maxDouble, sumDouble, sumDouble / countDouble);
        }
        if (minInt != Long.MAX_VALUE) {
            result += String.format("Minimum integer number: %d; Maximum integer number: %d; Sum: %d; Average: %.2f\n",
                    minInt, maxInt, sumInt, (double) sumInt / countInt);
        }
        return result;
    }
}
