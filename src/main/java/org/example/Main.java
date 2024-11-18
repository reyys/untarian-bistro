package org.example;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Untarian Bistro Project!");
            Menu m = new Menu();
            Item nasi = new Item();
            nasi.setName("Nasi Goreng");
            nasi.setPrice(15000);
            nasi.setDescription("Nasi goreng spesial");
            nasi.setImageUrl("https://example.com/nasi-goreng.jpg");
            nasi.setStock(10);

            m.insertMenuItem(nasi);
            int id = m.getLastId();
            System.out.println("Inserted item with ID: " + id);

            System.out.println("Selected item:");
            Item item = m.selectMenuItemById(id);
            System.out.printf("ID: %d - Name: %s - Price: %.0f\n", item.getId(), item.getName(), item.getPrice());

            item.setPrice(20000);
            m.updateMenuItem(item);

            System.out.println("Updated item:");
            Item updatedItem = m.selectMenuItemById(id);
            System.out.printf("ID: %d - Name: %s - Price: %.0f\n", updatedItem.getId(), updatedItem.getName(), updatedItem.getPrice());

            Item teh = new Item();
            teh.setName("Teh Manis");
            teh.setPrice(5000);
            teh.setDescription("Teh manis hangat");
            teh.setImageUrl("https://example.com/teh-manis.jpg");
            teh.setStock(20);
            m.insertMenuItem(teh);

            System.out.println("All menu items:");
            List<Item> items = m.selectAllMenuItems();
            for (Item i : items) {
                System.out.printf("ID: %d - Name: %s - Price: %.0f\n", i.getId(), i.getName(), i.getPrice());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}