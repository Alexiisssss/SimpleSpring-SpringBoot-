package edu.spring;

import edu.spring.dao.PersonDao;
import edu.spring.domain.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import java.util.List;

@SpringBootApplication
@EntityScan("edu.spring.domain")
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);

        PersonDao dao = context.getBean(PersonDao.class);

        System.out.println("................................");
        System.out.println("Count Person on List: " + dao.count());

        //добавление людей
        dao.insert(new Person(2, "Aleksey"));
        dao.insert(new Person(3, "Yuri"));
        dao.insert(new Person(4, "Aleksandr"));
        dao.insert(new Person(5, "Ekaterina"));
        dao.insert(new Person(6, "Evgeniy"));

        System.out.println("................................");

        //получить всех людей
        List<Person> people = dao.getAll();
        System.out.println("List Persons:");
        for (Person p : people) {
            System.out.println("Person ID: " + p.getId() + " , Name: " + p.getName());
        }

        System.out.println("................................");
        System.out.println("Count Person on List: " + dao.count());
        System.out.println("................................");

        //получить человека по id
        Person person = dao.getById(2);
        if (person != null) {
            System.out.println("Get person with ID: " + person.getId());
            System.out.println("Person found: " + "ID:" + person.getId() + ", Name:" + person.getName());

        } else {
            System.out.println("Person not found");
        }

        System.out.println("................................");


        //удаляем человека по id
        int idToDelete = 2;
        Person personToDelete = dao.getById(idToDelete);

        if (personToDelete != null) {
            dao.deleteById(idToDelete);
            System.out.println("Deleted person with ID: " + personToDelete.getId());
            System.out.println("Name: " + personToDelete.getName());
        } else {
            System.out.println("Person with ID " + idToDelete + " not found, no deletion performed.");
        }

        System.out.println("................................");
        System.out.println("Count Person on List: " + dao.count());
    }
}
