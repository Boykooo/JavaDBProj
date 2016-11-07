package DBController.BaseClasses;

/**
 * Created by Andrey on 06.11.2016.
 */
public class Cassette {
    public Cassette(int ID_Cassette, String Genre, String Name, String Producer, String Price, boolean Exist){
        this.ID_Cassette = ID_Cassette;
        this.Genre = Genre;
        this.Name = Name;
        this.Producer = Producer;
        this.Price = Price;
        this.Exist = Exist;
    }

    private int ID_Cassette;
    private String Genre;
    private String Name;
    private String Producer;
    private String Price;
    private boolean Exist;

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

    public String getProducer() {
        return Producer;
    }
    public void setProducer(String producer) {
        Producer = producer;
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
}
