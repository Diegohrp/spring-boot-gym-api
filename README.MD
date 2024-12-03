# Gym Management System API

This project is a Gym Management System. It uses a PostgreSQL database which can be run using Docker. The main application is executed via `Main.java` in IntelliJ IDEA.

## Prerequisites

- Docker
- IntelliJ IDEA

## Getting Started

### Setting up the Database with Docker

1. Clone the repository to your local machine.

    ```bash
    git clone <repository-url>
    cd <repository-directory>
    ```

2. Create and start the Docker containers.

    ```bash
    docker-compose up -d
    ```

   This command will start two services:
    - **db**: A PostgreSQL database with the following configuration:
        - **POSTGRES_USER**: dev
        - **POSTGRES_PASSWORD**: devadmin123
        - **POSTGRES_DB**: gym_db
    - **pgadmin**: pgAdmin 4 for managing the PostgreSQL database. It will be accessible at [http://localhost:8080](http://localhost:8080) with the following credentials:
        - **Email**: dev@mail.com
        - **Password**: devpswd123

3. Verify that the database is running by accessing pgAdmin 4 at [http://localhost:8080](http://localhost:8080).

### Running the Application with IntelliJ IDEA

1. Open IntelliJ IDEA.

2. Open the project by navigating to `File` > `Open` and selecting the project directory.

3. Configure the project's SDK if it's not already set. Go to `File` > `Project Structure` > `Project` and set the Project SDK to your installed JDK version.

4. Ensure that the `Main.java` file is correctly located. It should be in the `src/main/java/com/example` (or similar) directory.

5. Right-click on `Main.java` and select `Run 'Main.main()'`.

6. The application should start, and you'll see the output in the IntelliJ console.

### Stopping the Docker Containers

When you're done working with the project, you can stop the Docker containers with the following command:

```bash
docker-compose down
