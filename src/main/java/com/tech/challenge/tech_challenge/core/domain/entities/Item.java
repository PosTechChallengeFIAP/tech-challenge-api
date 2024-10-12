package com.tech.challenge.tech_challenge.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
    private String id;
    private String name;
    private EItemType type;

    public Error validate() {
        if (this.name == "" || this.name == null) {
            return new Error("Invalid name");
        }

        if (this.type == null) {
            return new Error("Invalid type");
        }

        return null;
    }
}
