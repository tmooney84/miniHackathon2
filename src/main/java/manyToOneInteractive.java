import jakarta.persistence.TypedQuery;
import model.Department;
import model.Teacher;
import org.example.ManageDept;
import org.example.ManageTeacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class manyToOneInteractive {
    public static void main(String[] args) {
        manyToOneInteractive();
    }

    public static void manyToOneInteractive() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        System.out.println("\nWelcome to ManyToOneInteractive!");
        try (factory; Session session = factory.openSession(); Scanner scanner = new Scanner(System.in)) {
            boolean keepRunning = true;
            while (keepRunning) {
                System.out.println("\n0. Exit");
                System.out.println("1. Manage Departments");
                System.out.println("2. Manage Teachers");
                System.out.println("3. Assign Teacher to Department");
                System.out.println("4. List Teachers");
                System.out.println("5. List Departments");
                System.out.println("6. List Teachers By Department");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Exiting...");
                        keepRunning = false;
                        break;
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
                        listDepartments(session);  // Renamed from listDepts to listDepartments
                        break;
                    case 6:
                        listTeachersByDepartment(session);  // Renamed from getTeacherList to listTeachersByDepartment
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

    private static void listTeachers(Session session) {
        String hql = "FROM Teacher";  // Query to fetch all teachers
        TypedQuery<Teacher> query = session.createQuery(hql, Teacher.class);
        List<Teacher> results = query.getResultList();

        System.out.printf("%-15s %-20s %-15s%n", "Teacher Id", "Teacher Name", "Department Id");
        for (Teacher teacher : results) {
            // Print teacher ID, teacher name, and department ID
            int departmentId = (teacher.getDepartment() != null) ? teacher.getDepartment().getDeptId() : 0;
            System.out.printf("%-15s %-20s %-15s%n", teacher.getTeacherId(), teacher.getTeacherName(), departmentId);
        }
    }

    private static void listDepartments(Session session) {
        String hql = "FROM Department";  // Query to fetch all departments
        TypedQuery<Department> query = session.createQuery(hql, Department.class);
        List<Department> results = query.getResultList();

        System.out.println("Department Id:   Department Name:");
        for (Department department : results) {
            System.out.printf("%d                %s%n", department.getDeptId(), department.getDeptName());
        }
    }

    private static void listTeachersByDepartment(Session session) {
        String hql = "SELECT t.teacherName, d.deptName FROM Teacher t JOIN t.department d";
        List<Object[]> results = session.createQuery(hql).getResultList();

        System.out.println("Teacher Name:       Department Name:");
        for (Object[] result : results) {
            String teacherName = (String) result[0];
            String deptName = (String) result[1];
            System.out.printf("%-20s%-20s%n", teacherName, deptName);
        }
    }

    private static void manageDepartments(Scanner scanner, SessionFactory factory) {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n1. Add Department");
            System.out.println("2. Delete Department");
            System.out.println("3. Rename Department");  // Renamed from Modify Department to Rename Department
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    ManageDept.addDepartment(scanner, factory);
                    break;
                case 2:
                    ManageDept.deleteDepartment(scanner, factory);
                    break;
                case 3:
                    ManageDept.renameDepartment(scanner, factory);
                    break;
                case 0:
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void manageTeachers(Scanner scanner, SessionFactory factory) {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n1. Add Teacher");
            System.out.println("2. Delete Teacher");
            System.out.println("3. Rename Teacher");  // Renamed from Modify Teacher to Rename Teacher
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    ManageTeacher.addTeachers(scanner, factory);
                    break;
                case 2:
                    ManageTeacher.deleteTeacher(scanner, factory);
                    break;
                case 3:
                    ManageTeacher.renameTeacher(scanner, factory);
                    break;
                case 0:
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void assignTeacherToDepartment(Scanner scanner, Session session) {
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("\n1. Enter Teacher Id to assign: ");
            int teacherId = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            System.out.println("\n2. Enter Department Id: ");
            int deptId = scanner.nextInt();

            // Fetch the Teacher and Department from the database
            Teacher teacher = session.get(Teacher.class, teacherId);
            Department department = session.get(Department.class, deptId);

            if (teacher != null && department != null) {
                // Print current department if exists
                Department currentDept = teacher.getDepartment();
                System.out.println("Current Department Id: " +
                        (currentDept != null ? currentDept.getDeptId() : "None"));

                // Assign new department
                teacher.setDepartment(department);
                session.merge(teacher);
                transaction.commit();

                // Print new department
                System.out.println("New Department Id: " + department.getDeptId());
            } else {
                System.out.println("Teacher or Department not found!");
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
