package org.example;

import model.Department;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Scanner;

public class ManageTeacher {


    public static void addTeachers(Scanner scanner, SessionFactory factory) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\n1. Enter name of teacher to add: ");
        scanner.nextLine();

        String userInputTeacher = scanner.nextLine();

        System.out.println("\n1. Enter id of department: ");
        int userInputDept = scanner.nextInt();

        //TODO if else statement to check if dept id exists

        Department dept = new Department();
        dept.setDeptId(userInputDept);

        Teacher teacher = new Teacher(userInputTeacher, dept);
        session.persist(teacher);
        transaction.commit();
        session.close();
    }

    public static void deleteTeacher(Scanner scanner, SessionFactory factory) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\n1. Enter id of teacher to remove: ");
        scanner.nextLine();

        int userInputTeacherId = scanner.nextInt();

        //TODO if else statement to check if teacher id exists

        Teacher teacher = new Teacher();
        teacher.setTeacherId(userInputTeacherId);
        session.remove(teacher);
        transaction.commit();
        session.close();
    }

    public static void renameTeacher(Scanner scanner, SessionFactory factory) {
        //TODO ADD CODE
    }


}