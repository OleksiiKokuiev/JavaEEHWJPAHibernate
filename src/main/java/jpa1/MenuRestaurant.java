package jpa1;

import javax.persistence.*;

@Entity
@Table(name="Menu")
public class MenuRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="name", nullable=false)
    private String nameOfDish;
    private int price;
    private double weight;
    private int hasDiscount;

    public MenuRestaurant(String nameOfDish, int price, double weight, int hasDiscount) {
        this.nameOfDish = nameOfDish;
        this.price = price;
        this.weight = weight;
        this.hasDiscount = hasDiscount;
    }

    public MenuRestaurant() {
    }

    public String getNameOfDish() {
        return nameOfDish;
    }

    public void setNameOfDish(String nameOfDish) {
        this.nameOfDish = nameOfDish;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int isHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(int hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    @Override
    public String toString() {
        return "menuRestaurant{" +
                "nameOfDish='" + nameOfDish + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", hasDiscount=" + hasDiscount +
                '}';
    }
}
