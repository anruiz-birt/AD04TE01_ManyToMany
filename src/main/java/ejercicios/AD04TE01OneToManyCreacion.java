package ejercicios;

import java.time.LocalDate;

import entidades.Address;
import entidades.Student;
import entidades.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AD04TE01OneToManyCreacion {

	/**
	 * 1. OneToMany bidireccional entre entidades Student y University
	 */
	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("AD04");
		EntityManager entityManager = factory.createEntityManager();
		
		
		try {			
			// crea un objeto Student y University
			System.out.println("Creando un nuevo objeto Student y una Universidad");
		
			Student student = createStudent();
			University university = createUniversity();
					
			university.getStudents().add(student);
			student.setUniversity(university); //bidireccional
					
			// comienza la transacción
			entityManager.getTransaction().begin();
			
			// guarda el objeto University
			System.out.println("Guardando la universidad y en cascada el estudiante");
			entityManager.persist(university);
			
			// hace commit de la transaccion
			entityManager.getTransaction().commit();
			
			// prueba para acceder a la entidad source desde de la entidad target
			entityManager.getTransaction().begin();
			Student dbStudent = (Student)entityManager.find(Student.class, university.getStudents().get(0).getId());
			System.out.println(dbStudent.getUniversity().getName());
						
			System.out.println("Hecho!");
		}
		catch ( Exception e ) {
			// rollback ante alguna excepción
			System.out.println("Realizando Rollback");
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			entityManager.close();
			factory.close();
		}
	}
	private static Student createStudent() {
		Student tempStudent = new Student();
		Address tempAddress = new Address();
		
		tempStudent.setFirstName("Ane");
		tempStudent.setLastName("Ruiz");
		tempStudent.setEmail("anruiz@birt.eus");
		tempStudent.getPhones().add("666778899");
		tempStudent.getPhones().add("677990011");
		tempStudent.setBirthdate(LocalDate.parse("1981-01-01"));
		tempAddress.setAddressLine1("Pajares 34");
		tempAddress.setAddressLine2("1A");
		tempAddress.setCity("Santurtzi");
		tempAddress.setZipCode("48980");
		tempStudent.setAddress(tempAddress);
		return tempStudent;		
	}
	
	private static University createUniversity() {
		University tempUniversity = new University();
		Address uniAddress = new Address();
		
		tempUniversity.setName("EHU");
		uniAddress.setAddressLine1("Araba Kalea");
		uniAddress.setAddressLine2("5");
		uniAddress.setCity("Gasteiz");
		uniAddress.setZipCode("01155");
		tempUniversity.setAddress(uniAddress);
		return tempUniversity;		
	}
}