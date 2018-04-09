package erp.dal.repositories;

import org.springframework.data.jpa.repository.*;

import erp.bll.entities.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
