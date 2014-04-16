package ru.ttk.baloo.remote.core.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ttk.baloo.remote.core.model.Account;


/**
 * Core Account Repository
 */
public interface AccountRepository extends CrudRepository<Account, String> {

    Account findByPrincipalName(String principalName);
    Account findByPrincipalNameAndPassword(String principalName, String password);
}
