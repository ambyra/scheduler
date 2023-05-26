package sample.model;
import java.time.YearMonth;

/**
 * class for tableview display, organizes data in to month of year and total
 * @return
 */

public class MonthTotal{
    private YearMonth yearMonth;
    private String yearMonthString;
    private int total;



    public YearMonth getYearMonth() {return yearMonth;}
    public int getTotal(){return total;}
    public void setTotal(int total){this.total = total;}

    /**
     * get year and month in a string format
     * @return
     */
    public String getYearMonthString(){
        int year = yearMonth.getYear();
        String month = yearMonth.getMonth().toString();
        return month + " " + year;
        }

    public MonthTotal(YearMonth yearMonth, int total){
        this.yearMonth = yearMonth;
        this.total = total;
    }

}