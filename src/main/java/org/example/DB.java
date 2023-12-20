package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import org.hibernate.*;
import org.hibernate.query.*;
import java.util.List;



public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root";
    private static final String PASSWORD = "perfect1488";


    public static void con() throws RuntimeException {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = con.createStatement();
            statement.execute("DROP SCHEMA  SchoolDB;");
            statement.execute("CREATE SCHEMA SchoolDB;");
            statement.execute(
                    "CREATE TABLE  SchoolDB.Courses(id INT NOT NULL AUTO_INCREMENT, title VARCHAR(45) NULL, duration INT NULL, PRIMARY KEY (id));");
            statement.execute("INSERT INTO SchoolDB.Courses(id, title, duration) VALUES(id,'ВведениеГит', 10);");
            statement.execute("INSERT INTO SchoolDB.Courses(id, title, duration) VALUES(id, 'Исключения', 20);");
            statement.execute("INSERT INTO SchoolDB.Courses(id, title, duration) VALUES(id, 'Линукс', 50);");
            ResultSet set = statement.executeQuery("SELECT * FROM SchoolDB.Courses;");
            while (set.next()) {
                System.out.println(set.getInt("id") + " " + set.getString("title") + " " + set.getInt("duration"));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateData() {
        Connector connector = new Connector();
        Session session = connector.getSession();
        Course course = new Course("Алгоритмы", 30);
        session.beginTransaction();
        session.save(course);
        course = new Course("C++", 100);
        session.save(course);

        session.close();
    }
    /*
     * Вывов данных
     */
    public static void getAllData() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            List<Course> books = session.createQuery("FROM Course", Course.class).getResultList();
            books.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * изменяем данные
     */
    public static void setTestData() {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            String hql = "FROM Course where id = :id";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("id", 3);
            Course course = query.getSingleResult();
            System.out.println(course);
            course.setTitle("Java");
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
            System.out.println("\nЗамена записи курса выполнена");
            getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Удаляет данные
     */
    public static void deleteData(Object identifier) {
        Connector connector = new Connector();
        try (Session session = connector.getSession()) {
            Transaction transaction = session.beginTransaction();

            if (identifier instanceof Integer) {
                // Если передан Integer, значит, удаляем по id
                int id = (int) identifier;
                Course course = session.get(Course.class, id);
                if (course != null) {
                    session.delete(course);
                    System.out.println("Курс с id=" + id + "  удален");
                } else {
                    System.out.println("Курс с id=" + id + " не найден");
                }
            } else if (identifier instanceof String) {
                // Если передан String, значит, удаляем по title
                String title = (String) identifier;
                String hql = "FROM Course WHERE title = :title";
                Query<Course> query = session.createQuery(hql, Course.class);
                query.setParameter("title", title);
                List<Course> courses = query.getResultList();

                if (!courses.isEmpty()) {
                    courses.forEach(session::delete);
                    System.out.println("Курс '" + title + "' удален");
                } else {
                    System.out.println("Курс  '" + title + "' не найден");
                }
            } else {
                System.out.println("Неверный тип данных");
            }

            transaction.commit();
            getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

