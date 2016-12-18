package Interfaces;

public interface IMainController {
    void addCortege(Object... params);
    void deleteCortege(Object... params);
    void updateCortege(Object... params);
    void showSpecificAgreement(String phone);
    void searchData(Object... params);
    void income(String startDate, String endDate);

    void closeConnection();
}
