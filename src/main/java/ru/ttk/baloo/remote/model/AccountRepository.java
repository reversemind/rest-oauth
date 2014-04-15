package ru.ttk.baloo.remote.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.PersistenceContext;
import java.util.List;


/**
 */
//public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
public interface AccountRepository extends CrudRepository<Account, String> {

    Account findByPrincipalName(String principalName);
}