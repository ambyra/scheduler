package sample.model;
/**
 * class for tableview display, organizes data in to type and total
 * @return
 */
public class TypeTotal{
    private String type;
    private int total;

    public String getType() {return type;}
    public int getTotal(){return total;}
    public void setTotal(int total){this.total = total;}

    public TypeTotal(String type, int total){
        this.type = type;
        this.total = total;
    }
}