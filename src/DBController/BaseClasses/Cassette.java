package DBController.BaseClasses;


public class Cassette {
    public Cassette(int ID_Cassette, String Genre, String Name, String Director, String Price, boolean Exist, int Year){
        this.ID_Cassette = ID_Cassette;
        this.Genre = Genre;
        this.Name = Name;
        this.Director = Director;
        this.Price = Price;
        this.Exist = Exist;
        this.Year = Year;
    }

    private int ID_Cassette;
    private String Genre;
    private String Name;
    private String Director;
    private String Price;
    private boolean Exist;
    private int Year;

    public int getID_Cassette() {
        return ID_Cassette;
    }
    public void setID_Cassette(int ID_Cassette) {
        this.ID_Cassette = ID_Cassette;
    }

    public String getGenre() {
        return Genre;
    }
    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getDirector() {
        return Director;
    }
    public void setDirector(String director) {
        Director = director;
    }

    public String getPrice() {
        return Price;
    }
    public void setPrice(String price) {
        Price = price;
    }

    public boolean isExist() {
        return Exist;
    }
    public void setExist(boolean exist) {
        Exist = exist;
    }

    public int getYear() { return Year; }
    public void setYear(int year) { Year = year; }
}
