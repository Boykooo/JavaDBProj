package Interfaces;

public interface IMainController {
    void addCortege(Object... params);
    void deleteCortege(Object... params);
    void updateCortege(Object... params);

    void closeConnection();
}
