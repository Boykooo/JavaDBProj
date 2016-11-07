package DBController.BaseClasses;

/**
 * Created by Andrey on 06.11.2016.
 */
public class Agreement {
    public Agreement(int ID_Agreement, String Order_Date, String Last_Return_Date, String Client_Name, String Client_Phone_Number, int Total_Price, int ID_Employee) {
        this.ID_Agreement = ID_Agreement;
        this.Order_Date = Order_Date;
        this.Last_Return_Date = Last_Return_Date;
        this.Client_Name = Client_Name;
        this.Client_Phone_Number = Client_Phone_Number;
        this.Total_Price = Total_Price;
        this.ID_Employee = ID_Employee;
    }

    private int ID_Agreement;
    private String Order_Date;
    private String Last_Return_Date;
    private String Client_Name;
    private String Client_Phone_Number;
    private int Total_Price;
    private int ID_Employee;

    public int getID_Agreement() {
        return ID_Agreement;
    }

    public void setID_Agreement(int ID_Agreement) {
        this.ID_Agreement = ID_Agreement;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getLast_Return_Date() {
        return Last_Return_Date;
    }

    public void setLast_Return_Date(String last_Return_Date) {
        Last_Return_Date = last_Return_Date;
    }

    public String getClient_Name() {
        return Client_Name;
    }

    public void setClient_Name(String client_Name) {
        Client_Name = client_Name;
    }

    public String getClient_Phone_Number() {
        return Client_Phone_Number;
    }

    public void setClient_Phone_Number(String client_Phone_Number) {
        Client_Phone_Number = client_Phone_Number;
    }

    public int getTotal_Price() {
        return Total_Price;
    }

    public void setTotal_Price(int total_Price) {
        Total_Price = total_Price;
    }

    public int getID_Employee() {
        return ID_Employee;
    }

    public void setID_Employee(int ID_Employee) {
        this.ID_Employee = ID_Employee;
    }
}

