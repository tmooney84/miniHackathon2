import jakarta.persistence.TypedQuery;
import model.Department;
import model.Teacher;
import org.example.ManageDept;
import org.example.ManageTeacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        //        Transaction transaction = session.beginTransaction();
        try (factory; Session session = factory.openSession(); Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n0. Exit");
                System.out.println("1. Manage Departments");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Assign Teacher to Department");
                System.out.println("4. List Teachers");
                System.out.println("5. List Department");
                System.out.println("6. List Teachers By Department");
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
                    case 6:
                        getTeacherList(session);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private static void getTeacherList(Session session) {
        //String hqlSelect2 = "select t from teachers t join fetch t.deparment";

        String hqlSelect2 = "select Teacher.teacherName, Department.deptName from Teacher join Department on Teacher.department_Id = Department.deptId";

        TypedQuery<Department> query = session.createQuery(hqlSelect2, Department.class);
        List<Department> results = query.getResultList();

        System.out.println("Department Id:               Department Name: ");
        for (Department d : results) {
            System.out.println(d.getDeptId() + "       " + d.getDeptName());
        }
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
        String deptQuery = "FROM Teacher";
        TypedQuery<Department> query = session.createQuery(deptQuery, Department.class);
        List<Department> results = query.getResultList();

        System.out.println("Department Id:               Department Name: ");
        for (Department d : results) {
            System.out.println(d.getDeptId() + "       " + d.getDeptName());
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
                    //go back to main menu **** try again
                        manyToOneInteractive();
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

        try (scanner) {
            while (true) {
                System.out.println("\n1. Add Teachers");
                System.out.println("2. Delete Teacher");
                System.out.println("3. Modify Teacher");
                System.out.println("4. Go back to menu");
                System.out.println("\n0. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    case 1:
                     ManageTeacher.addTeachers(scanner, factory);
                        break;
                    case 2:
                        ManageTeacher.deleteTeacher(scanner, factory);
                        break;
                    case 3:
                        ManageTeacher.renameTeacher(scanner, factory);
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



    /*
    ---- PREVOUS CODE ----
    private static void assignTeacherToDepartment(Scanner scanner, Session session) {

        // Grab the user input by the Id of teaecher and department
        // Fetch the entities (Teacher and Department)
        // Somehow update them?
        // Error handling
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println("Which Teacher ID would you like to modify:");
            int teacherId = scanner.nextInt();
            System.out.println("What department do you want to put Teacher ID #: " +  teacherId );
            int departmentId = scanner.nextInt();
            //  System.out.println("What is the new name you want to associate with  Teacher ID #: " +  teacherId );
            //  scanner.nextLine();
            //  String newTeacherName = scanner.nextLine();
            // teacher.setTeacherName(userInputNewTeachName);

            Teacher teacher = session.get(Teacher.class, teacherId);
            Department department = session.get(Department.class, departmentId);

            if (teacher != null && department != null) {
                //   teacher.setTeacherName(newTeacherName);
                teacher.setDepartment(department);
                session.merge(teacher);
                System.out.println("Teacher assigned to department successfully!");
            }
            else {
                System.out.println("Teacher or Department not found!");
            }
            transaction.commit();
        }catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
     */



    private static void assignTeacherToDepartment(Scanner scanner, Session session) {

        // Grab the user input by the Id of teaecher and department
        // Fetch the entities (Teacher and Department)
        // Somehow update them?
        // Error handling
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            System.out.println("Which Teacher ID would you like to modify:");
            int teacherId = scanner.nextInt();

            Teacher teacher = session.get(Teacher.class, teacherId);
            System.out.println("What department do you want to put Teacher ID # " +  teacherId );

            //print out list of departments with id
            int departmentId = scanner.nextInt();
            Department department = session.get(Department.class, departmentId);
            //
            teacher.setDepartment(department);
            session.merge(department); // copy state of given object onto persistent object with the same identifier
            System.out.println("Teacher assigned to department successfully!");

            transaction.commit();
        }catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }



}