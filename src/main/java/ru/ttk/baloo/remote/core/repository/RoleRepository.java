package ru.ttk.baloo.remote.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ttk.baloo.remote.core.model.Role;

import java.util.List;

/**
 *
 */
public interface RoleRepository extends CrudRepository<Role, String> {

    @Query(value = "SELECT name\n" +
            "  FROM x_role\n" +
            "  where id in\n" +
            "  (\n" +
            "\tSELECT xrole_id\n" +
            "\tFROM x_roles_groups_map\n" +
            "\twhere TRIM(LEADING 'entity://CORE.DB/ru.ttk.baloo.core.portable.entities.security.PGroup/' FROM group_ref) in (\n" +
            "\t\tSELECT x_group_id\n" +
            "\t\t  FROM x_accounts_groups\n" +
            "\t\t  where x_account_id = ?1 \n" +
            "\t)\n" +
            "  )", nativeQuery = true)
    List<String> findRoleNamesByAccountId(String accountId);
}

/*

SELECT name
  FROM x_role
  where id in
  (
	SELECT xrole_id
	FROM x_roles_groups_map
	where TRIM(LEADING 'entity://CORE.DB/ru.ttk.baloo.core.portable.entities.security.PGroup/' FROM group_ref) in (
		SELECT x_group_id
		  FROM x_accounts_groups
		  where x_account_id = 'ce805a7c-08ca-44e6-afd6-ec56e31d09d0'
	)
  )

*/
