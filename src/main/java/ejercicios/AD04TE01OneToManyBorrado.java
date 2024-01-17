package ejercicios;

import entidades.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AD04TE01OneToManyBorrado {

	/**
	 * OneToMany bidireccional entre entidades Student y University
	 * Borra una Universidad sin sus estudiantes.
	 */
	public static void main(String[] args) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("AD04");
		EntityManager entityManager = factory.createEntityManager();
		
		try {			
			// crea un objeto Student
			System.out.println("Borrando una universidad sin borrar sus estudiantes");
			
			int university_id = 4;
			
			University tempUniversity = entityManager.find(University.class, university_id);
			// comienza la transacción
			entityManager.getTransaction().begin();
		
			// borra la universidad pero no el estudiante. "on delete set null" en BD
			entityManager.remove(tempUniversity);
			
			// hace commit de la transaccion
			entityManager.getTransaction().commit();
					
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
	
}




