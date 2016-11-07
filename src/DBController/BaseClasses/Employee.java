package DBController.BaseClasses;

public class Employee {
    public  Employee(int ID_Employee, String name, String phone_number){
        this.ID_Employee = ID_Employee;
        this.Name = name;
        this.Phone_Number = phone_number;
    }
    private int ID_Employee;
    private String Name;
    private String Phone_Number;

    public int getID_Employee() {
        return ID_Employee;
    }
    public void setID_Employee(int id_Employee) {
        this.ID_Employee = id_Employee;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }
    public void setPhone_Number(String phone_number) {
        this.Phone_Number = phone_number;
    }
}
