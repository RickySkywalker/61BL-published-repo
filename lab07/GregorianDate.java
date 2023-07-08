public class GregorianDate extends Date {

    private static final int[] MONTH_LENGTHS = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public GregorianDate(int year, int month, int dayOfMonth) {
        super(year, month, dayOfMonth);
    }

    // YOUR CODE HERE

    @Override
    public int dayOfYear() {
        int precedingMonthDays = 0;
        for (int m = 1; m < month; m += 1) {
            precedingMonthDays += getMonthLength(m);
        }
        return precedingMonthDays + dayOfMonth;
    }

    @Override
    public Date nextDate(){
        int returnDayOfMonth = dayOfMonth;
        int returnMonth = month;
        int returnYear = year;
        if (month != 12){
            if (dayOfMonth+1 >getMonthLength(month)){
                returnDayOfMonth = 1;
                returnMonth++;
            }else{
                returnDayOfMonth += 1;
            }
        }else{
            if (dayOfMonth+1 > getMonthLength(month)){
                returnDayOfMonth = 1;
                returnMonth = 1;
                returnYear++;
            }else{
                returnDayOfMonth++;
            }
        }
        GregorianDate dateToReturn = new GregorianDate(returnYear, returnMonth, returnDayOfMonth);
        return dateToReturn;
    }

    private static int getMonthLength(int m) {
        return MONTH_LENGTHS[m - 1];
    }


}