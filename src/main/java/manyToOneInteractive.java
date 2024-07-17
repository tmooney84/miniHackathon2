import jakarta.persistence.TypedQuery;
import model.Department;
import org.example.ManageDept;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Scanner;

public class manyToOneInteractive {
    public static void main(String[] args) {
        manyToOneInteractive();
    }

    public static void manyToOneInteractive() {
        System.out.println("Welcome to ManyToOneInteractive!");
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
//        Transaction transaction = session.beginTransaction();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n0. Exit");
                System.out.println("1. Manage Departments");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Assign Teacher to Department");
                System.out.println("4. List Teachers");
                System.out.println("5. List Department");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        manageDepartments(scanner, factory);
                        break;
                    case 2:
                        manageTeachers(scanner, factory);
                        break;
                    case 3:
                        assignTeacherToDepartment(scanner, session);
                        break;
                    case 4:
                        listTeachers(session);
                        break;
                    case 5:
                        listDepts(session);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
            factory.close();

        }



    }

    private static void manageDepartments(Scanner scanner, SessionFactory factory) {

        // YOUR CODE HERE
        //create a seperate method for each of these four methods in org.example.ManageDept

//tester scanner???
        try (scanner) {
            while (true) {
                System.out.println("\n1. Add Departments");
                System.out.println("2. Delete Department");
                System.out.println("3. Modify Department");
                System.out.println("4. Go back to menu");
                System.out.println("\n0. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        ManageDept.addDepartment(scanner, factory);
                        break;
                    case 2:
                        ManageDept.deleteDepartment(scanner, factory);
                        break;
                    case 3:
                        ManageDept.renameDepartment(scanner, factory);
                        break;
                    case 4:
                    //go back to main menu
                        default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }



    }

    private static void manageTeachers(Scanner scanner, SessionFactory factory) {

        // YOUR CODE HERE
        //create a seperate method for each of these four methods in org.example.ManageTeacher
        System.out.println("\n1. Add Teachers");
        System.out.println("2. Delete Teacher");
        System.out.println("3. Modify Teacher");
        System.out.println("4. Go back to menu");



        try (scanner) {
            while (true) {
                System.out.println("\n1. Add Departments");
                System.out.println("2. Delete Department");
                System.out.println("3. Modify Department");
                System.out.println("4. Go back to menu");
                System.out.println("\n0. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                        ManageDept.addDepartment(scanner, factory);
                        break;
                    case 2:
                        ManageDept.deleteDepartment(scanner, factory);
                        break;
                    case 3:
                        ManageDept.renameDepartment(scanner, factory);
                        break;
                    case 4:
                        //go back to main menu
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    private static void assignTeacherToDepartment(Scanner scanner, Session session) {

        // Grab the user input by the Id of teaecher and department
        // Fetch the entities (Teacher and Department)
        // Somehow update them?
        // Error handling

        // YOUR CODE
        System.out.println("Which Teacher would you like to modify: ");

        //call listDepts
        System.out.println("Which department would you like to assign to Teacher: ");
//change foreign key for Teacher...toupper function that is insensitive and while loop until correct
        //enter foreign key change for Teacher


    }

    private static void listDepts(Session session) {
        String deptQuery = "FROM Department";
        TypedQuery<Department> query = session.createQuery(deptQuery, Department.class);
        List<Department> results = query.getResultList();

        System.out.println("Department Id:               Department Name: ");
        for (Department d : results) {
            System.out.println(d.getDeptId() + "       " + d.getDeptName());
        }
    }
    private static void listTeachers(Session session) {

public List<Teacher> getTeacherList() {
   String hqlSelect2 = "select teachers, department from Teacher join Department on Teacher.deptId =
     Department.deptId";
    */
            TypedQuery<Department> query = session.createQuery(hqlSelect2, Department.class);
            List<Department> results = query.getResultList();

            System.out.println("Department Id:               Department Name: ");
            for (Department d : results) {
                System.out.println(d.getDeptId() + "       " + d.getDeptName());
    }
}}