package com.markbromell.manhunt.persistence;

public interface RolePersistence {
    /**
     * Updates the persistent storage of the player roles from the role manager.
     *
     * @param roleManager The role manager that will have its data persisted.
     */
    void push(RoleManager roleManager);

    /**
     * Updates the role manager with role data if there is any stored for the server.
     *
     * @param roleManager The role manager to update.
     */
    void pull(RoleManager roleManager);
}
