package com.example.todo.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
    SELECT DISTINCT t.* 
    FROM tasks t
    JOIN tasks_categories tc ON t.id = tc.tasks_id
    JOIN category c ON c.id = tc.category_id
    WHERE LOWER(c.catname) IN (:names)
    """, nativeQuery = true)
List<Task> findByCategoryNamesIgnoreCase(@Param("names") List<String> names);

    List<Task> findByArchivedTrue();
    List<Task> findByArchivedFalse();
        
}

//adding case insensitvity 