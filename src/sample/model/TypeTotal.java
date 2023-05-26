package sample.model;
/**
 * class for tableview display, organizes data in to type and total
 *
 */
public class TypeTotal{
    private String type;
    private int total;

    /**
     * get type
     * @return
     */
    public String getType() {return type;}

    /**
     * get total
     * @return
     */
    public int getTotal(){return total;}

    /**
     * set total
     * @param total
     */
    public void setTotal(int total){this.total = total;}

    /**
     * create new typetotal
     * @param type
     * @param total
     */

    public TypeTotal(String type, int total){
        this.type = type;
        this.total = total;
    }
}