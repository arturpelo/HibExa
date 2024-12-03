package pl.com.pelo;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
//import javax.persistence.*;

// Encja reprezentująca tabelę w bazie danych
@Entity
@Table(name = "students")
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private int age;

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class Main {

    public static void main(String[] args) {
        // Konfiguracja Hibernate
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Student.class);

        // Tworzenie "fabryki" sesji
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Operacje CRUD
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Tworzenie nowego studenta
            Student student = new Student();
            student.setName("Jan Kowalski");
            student.setAge(22);
            session.save(student);
            System.out.println("Zapisano studenta: " + student);

            // Odczyt studenta
            Student retrievedStudent = session.get(Student.class, student.getId());
            System.out.println("Odczytano studenta: " + retrievedStudent);

            // Aktualizacja studenta
            retrievedStudent.setAge(23);
            session.update(retrievedStudent);
            System.out.println("Zaktualizowano studenta: " + retrievedStudent);

            // Usunięcie studenta
            session.delete(retrievedStudent);
            System.out.println("Usunięto studenta: " + retrievedStudent);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
