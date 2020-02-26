package jpa1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add dish");
                    System.out.println("2: view dishes");
                    System.out.println("3: select dishes with price from to");
                    System.out.println("4: select dishes with discount only");
                    System.out.println("5: select dishes with total weight less than 1 KG");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            viewMenu();
                            break;
                        case "3":
                            selectDishesInRange(sc);
                            break;
                        case "4":
                            selectWithDiscount();
                            break;
                        case "5":
                            selectSetOfDishesTotalWeightLessThan1Kg();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void addDish(Scanner sc) {
        System.out.print("Enter name of dish: ");
        String nameOfDish = sc.nextLine();
        System.out.print("Enter price for dish: ");
        String priceStr = sc.nextLine();
        int price = Integer.parseInt(priceStr);
        System.out.print("Enter weight of dish: ");
        String weightStr = sc.nextLine();
        double weight = Double.parseDouble(weightStr);
        System.out.print("Does dish have a discount? 1 if true, 0 if false: ");
        String discountStr = sc.nextLine();
        int hasDiscount = Integer.parseInt(discountStr);

        em.getTransaction().begin();
        try {
            MenuRestaurant menu = new MenuRestaurant(nameOfDish, price, weight, hasDiscount);
            em.persist(menu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void selectDishesInRange(Scanner sc) {
        System.out.print("Enter from: ");
        String fromStr = sc.nextLine();
        int from = Integer.parseInt(fromStr);
        System.out.print("Enter to: ");
        String toStr = sc.nextLine();
        int to = Integer.parseInt(toStr);

        Query query = em.createQuery(
                "SELECT c FROM MenuRestaurant c WHERE c.price BETWEEN " +
                        from + " AND " + to, MenuRestaurant.class);
        List<MenuRestaurant> list = (List<MenuRestaurant>) query
                .getResultList();

        for (MenuRestaurant c : list)
            System.out.println(c);
    }

    private static void viewMenu() {
        Query query = em.createQuery(
                "SELECT c FROM MenuRestaurant c", MenuRestaurant.class);
        List<MenuRestaurant> list = (List<MenuRestaurant>) query
                .getResultList();

        for (MenuRestaurant c : list)
            System.out.println(c);
    }

    private static void selectWithDiscount(){
        Query query = em.createQuery(
                "SELECT c FROM MenuRestaurant c WHERE c.hasDiscount=1", MenuRestaurant.class
        );

        List<MenuRestaurant> list = (List<MenuRestaurant>) query.getResultList();

        for (MenuRestaurant c : list){
            System.out.println(c);
        }
    }

    private static void selectSetOfDishesTotalWeightLessThan1Kg() {

        double totalWeight = 0.00;

        Query query = em.createQuery(
                "SELECT c FROM MenuRestaurant c", MenuRestaurant.class
        );

        List<MenuRestaurant> list = (List<MenuRestaurant>) query.getResultList();

        for (MenuRestaurant c : list) {
            if ((c.getWeight() > 1.00) || (totalWeight + c.getWeight() > 1.00)) continue;
            totalWeight = totalWeight + c.getWeight();
            System.out.println(c);
        }
    }
}


