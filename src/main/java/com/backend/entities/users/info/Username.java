package com.backend.entities.users.info;

import com.backend.entities.criteria.Criteria;
import com.backend.entities.criteria.conditions.ContainsAtleastTypeExpression;
import com.backend.entities.criteria.conditions.ContainsOnlyTypeExpression;
import com.backend.entities.criteria.conditions.SizeRangeExpression;
import com.backend.entities.criteria.generatable;

import java.util.ArrayList;
import java.util.List;

public class Username implements generatable {
    // Instance Variables
    public static final Criteria criteria = new Criteria(new ArrayList<>(List.of(
            new SizeRangeExpression(5, 20, null),
            new ContainsOnlyTypeExpression(new ArrayList<>(List.of("number", "letter")), null),
            new ContainsAtleastTypeExpression(new ArrayList<>(List.of("letter")), null)
    )));

    private String username;

    // Constructor
    public Username(String username) {
        this.username = username;

        if (username != null) {
            this.isValid(username, criteria);
        }
    }

    @Override
    public String toString() {
        return this.username;
    }

    public void generateUsername() {
        this.username = this.generate(criteria);
    }

    public String suggestUsername() {
        return this.generate(criteria);
    }

    public boolean isValid() {
        return this.isValid(this.username, criteria);
    }
}
