package com.markbromell.manhunt.persistence;

public interface RolePersistence {
    void push(RoleManager roleManager);

    void pull(RoleManager roleManager);
}
