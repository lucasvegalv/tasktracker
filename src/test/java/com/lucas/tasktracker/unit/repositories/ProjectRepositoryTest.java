package com.lucas.tasktracker.unit.repositories;

import com.lucas.tasktracker.repositories.ProjectRepository;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@EntityScan("com.lucas.tasktracker.entities")
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;

    // Create



    // Read All

    // Read By ID

    // Read by Title -> Query Method

    // Delete

    // Update


}
