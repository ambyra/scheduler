package sample.model;

public class TypeTotal{
    private String type;
    private int total;

    public String getType() {return type;}
    public int getTotal(){return total;}
    public void setTotal(int total){this.total = total;}

    public TypeTotal(String type){
        this.type = type;
        this.total = 0;
    }
}