package org.example;

import model.Department;
import model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Scanner;

public class ManageDept {

    public static void addDepartment(Scanner scanner, SessionFactory factory) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\n1. Enter name of department to add: ");
        scanner.nextLine();

        String userInputDept = scanner.nextLine();

        Department dept = new Department(userInputDept);
        session.persist(dept);
        transaction.commit();
        session.close();
    }

    public static void deleteDepartment(Scanner scanner, SessionFactory factory) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\n1. Enter Id of department to remove: ");
        scanner.nextLine();

        int userInputDept = scanner.nextInt();

        Department dept = new Department();
        dept.setDeptId(userInputDept);
        session.remove(dept);
        transaction.commit();
        session.close();
    }

    public static void renameDepartment(Scanner scanner, SessionFactory factory) {
        //Rename department
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("\n1. Enter Id of department to modify: ");
        int userInputDept = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n1. Enter new department name: ");
        String userInputNewDeptName = scanner.nextLine();

        Department dept = session.get(Department.class, userInputDept);
        dept.setDeptName(userInputNewDeptName);
        session.merge(dept);
        transaction.commit();
        session.close();
    }



}
