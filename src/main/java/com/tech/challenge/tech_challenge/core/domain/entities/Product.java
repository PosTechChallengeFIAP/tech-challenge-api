package com.tech.challenge.tech_challenge.core.domain.entities;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String description;
    private EProductCategory category;
    private ArrayList<Item> items;
    private double price;

    public Error validate() {
        if (this.name.isEmpty() || this.name.equals("")) {
            return new Error("Invalid name");
        }

        if (this.price <= 0) {
            return new Error("Invalid price");
        }

        if (this.items.size() <= 0) {
            return new Error("Invalid product");
        }

        if (this.category == EProductCategory.CUSTOM_SNAK) {
            boolean hasBread =  this.hasBread();
            if (!hasBread) return new Error("Invalid custom snak");
        }

        return null;
    }

    public void addItem(Item item) {
        if (this.category == EProductCategory.CUSTOM_SNAK) {
            boolean hasBread = this.hasBread();
            
            if (item.getType() == EItemType.BREAD && hasBread) {
                for (int idx = 0; idx < this.items.size(); idx++) {
                    if (item.getType() == EItemType.BREAD) {
                        this.items.set(idx, item);
                        break;
                    }
                }
            } else {
                this.items.add(item);
            }
        }
    }

    private boolean hasBread() {
        boolean hasBread = false;
        
        for (Item item : this.items) {
            if (item.getType() == EItemType.BREAD) {
                hasBread = true;
                break;
            }
        }

        return hasBread;
    } 
}
