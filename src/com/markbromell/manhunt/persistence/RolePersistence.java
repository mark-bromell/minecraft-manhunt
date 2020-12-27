package com.markbromell.manhunt.persistence;

import com.markbromell.manhunt.RoleManager;

public interface RolePersistence {
    void push(RoleManager roleManager);

    RoleManager pull();
}
