package com.fredy.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.fredy.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.fredy.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		tercerosPasosJPA();
		
	}
	
	

	public void tercerosPasosJPA(){
		formatear();
		delete();
		formatear();
	}

	@Transactional
	public void delete(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Personas en la base de datos:");

		Iterable<Person> persons =  repository.findAll();
		persons.forEach(System.out::println);
		
		System.out.println("Ingrese el ID de la persona a eliminar:");
		Long id = scanner.nextLong();
		repository.deleteById(id);
		System.out.println("Persona con ID " + id + " eliminada.");

		
		System.out.println("Personas restantes en la base de datos:");
		persons =  repository.findAll();
		persons.forEach(System.out::println);
		
		scanner.close();
	}

	/*
	 * Segundo para que se ejecute la transaccion
		Si no ponemos la anotacion @Transactional, JPA no va a guardar los datos
		en la base de datos, porque no se va a ejecutar la transaccion
		y no se va a hacer el commit, entonces los datos no se van a guardar
		en la base de datos. Por eso es importante poner la anotacion @Transactional
		en los metodos que realizan operaciones de escritura en la base de datos.
		Si es solo de lectura, no es necesario poner la anotacion @Transactional
		pero es una buena practica ponerla con el parametro readOnly = true
		para que el motor de base de datos sepa que es una operacion de solo
		lectura y pueda optimizar el rendimiento. Asi que es una buena practica poner la anotacion
		@Transactional en todos los metodos que interactuan con la base de datos, ya sea de lectura o escritura.
		Si es de lectura, poner readOnly = true, si es de escritura, no poner nada.
	 */
	@Transactional 
	public void update(){
		Scanner scanner = new Scanner(System.in);
		if(repository.count() == 0) {
			System.out.println("No hay personas en la base de datos para actualizar.");
			scanner.close();
			return;
		}
		else{
			System.out.println("Personas en la base de datos:");
			List<Person> persons = (List<Person>) repository.findAll();
			persons.forEach(System.out::println);
			System.out.println("Ingrese el ID de la persona a actualizar:");
			Long id = scanner.nextLong();
			// Trae un Optional, si es True trae el id, si es false seguro trae null
			Optional<Person> optionalPerson = repository.findById(id);
			optionalPerson.ifPresent(
				person ->{
					System.out.println("Persona encontrada: " + person);
					System.out.println(
						"Ingrese 1 para actualizar el name, 2 para actualizar el lastname, 3 para actualizar el programmingLanguage o 4 para actualizar todo:");
					int opcion = scanner.nextInt();
					switch (opcion) {
						case 1:{
							System.out.println("Ingrese el nuevo name de la persona:");
							scanner.nextLine(); // Limpiar el buffer
							String name = scanner.nextLine();
							person.setName(name);
							break;
						}
						case 2:{
							System.out.println("Ingrese el nuevo lastname de la persona:");
							scanner.nextLine(); // Limpiar el buffer
							String lastname = scanner.nextLine();
							person.setLastname(lastname);
							break;
						}
						case 3:{
							System.out.println("Ingrese el nuevo programmingLanguage de la persona:");
							scanner.nextLine(); // Limpiar el buffer
							String programmingLanguage = scanner.nextLine();
							person.setProgrammingLanguage(programmingLanguage);
							break;
						}
						case 4:{
							// actualizar todo
							System.out.println("Ingrese el nuevo name, lastname y programmingLanguage de la persona:");
							scanner.nextLine(); // Limpiar el buffer
							String name = scanner.nextLine();
							String lastname = scanner.nextLine();
							String programmingLanguage = scanner.nextLine();
							person.setName(name);
							person.setLastname(lastname);
							person.setProgrammingLanguage(programmingLanguage);
							break;
						}
						default:{
							System.out.println("Opcion no valida");
							break;
						}
					}
					// El comando save devueleve un entity con el id generado
					Person personUpdated = repository.save(person);
					System.out.println("Persona actualizada: " + personUpdated);
				});
			scanner.close();
		}
	}
	

	@Transactional
	public void create(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese name, lastname y programmingLanguage de la persona a crear:");
		String name = scanner.nextLine();
		String lastname = scanner.nextLine();
		String programmingLanguage = scanner.nextLine();
		scanner.close();
		Person p = new Person(name, lastname, programmingLanguage);
		
		
		// El comando save devueleve un entity con el id generado
		Person personNew = repository.save(p);
		System.out.println("Persona creada: " + personNew);

		// nos aseguramos que se guardo
		System.out.println("Verificando que se guardo...");
		// habla de funcion de flecha o colk back, metodo de referencia
		repository.findById(personNew.getId()).ifPresent(System.out::println);
	}
	public void guardar(){
		Person eduardo = new Person("Eduardo", "Vargas", "Java");
		Person persona = this.repository.save(eduardo);
		System.out.println("Persona guardada: " + persona);
	}

	//bien
	public void segundosPasosJPA(){
		formatear();
		findOneContaining();	
		formatear();
	}
	@Transactional(readOnly = true) 
	public void findOne(){
		Person person = null;
		// Este metodo devuelve un optional que puede ser nulo
		Optional<Person> optionalPerson = repository.findById(20L);
		// El optional tiene el metodo Is Empty o Is Present
		// isEmpty es true si no tiene valor
		// isPresent es true si tiene valor
		// if(optionalPerson.isEmpty()) {
		if(optionalPerson.isPresent()) {
			person = optionalPerson.get();
			System.out.println("Persona encontrada: " + person);
		} else {
			System.out.println("Persona no encontrada");
		}
	}
	@Transactional(readOnly = true) 
	public void findOneDiferente(){
		repository.findById(1L).ifPresent(System.out::println);
	}
	@Transactional(readOnly = true) 
	public void findOnePropio(){
		repository.findOne(1L).ifPresent(System.out::println);
	}

	// Selecciona la clase, el objeto y no la tabla de la bd, es una consulta ORM.
	@Transactional(readOnly = true)  
	public void findOnePropioNombre(){
		// este metodo find one esta sobrecargado
		repository.findOne("Fredy").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true) // es una transaccion de solo lectura
	public void findOnePropioNombreLike(){
		// este metodo find one esta sobrecargado
		repository.findOneLikeName("Fre").ifPresent(System.out::println);
	}
	@Transactional(readOnly = true) 
	public void findOneContaining(){
		// metodo de CrudRepository
		repository.findByNameContaining("Fre").ifPresent(System.out::println);
	}

	public void formatear() {
		System.out.println();
		for (int i = 0; i < 70; i++) {
			System.out.print("* ");
		}
		System.out.println(); 
	}

	public void primerosPasosJPA() {
		formatear();
		System.out.println("\nTodas las personas");
		List<Person> persons = (List<Person>) repository.findAll();
		persons.stream().forEach(System.out::println);
		

		// Obteniendo un where por lenguaje de programacion
		System.out.println("Personas que programan en Java");
		List<Person> programadores = repository.findByProgrammingLanguage("Java");
		programadores.stream().forEach(System.out::println);
		

		// buscar por ID
		System.out.println("Persona con ID 1");
		Person person = repository.findById(1L).orElse(null);
		System.out.println(person);
	

		System.out.println("Buscando persona por nombre");
		Person fredy = repository.findByName("Fredy");
		System.out.println(fredy);

		// buscando por metodo propio
		System.out.println("Buscando por query personalizada");
		programadores = repository.buscarByProgrammingLanguage("Java", "Fredy");
		programadores.stream().forEach(System.out::println);
		

		System.out.println("Buscando por query estandar");
		programadores = repository.findByProgrammingLanguageAndName("Java", "Fredy");
		programadores.stream().forEach(System.out::println);
		
		System.out.println("Obteniendo data personalizada");
		List<Object[]> data = repository.obtenerPersonData();
		for (Object[] objects : data) {
			System.out.println("Name: " + objects[0] + ", Lenguaje: " + objects[1]);
		}

		System.out.println();
		data = repository.obtenerPersonData("Java","Fredy");
		System.out.println("\nOtra forma de imprimir PERO CON WHERE y con sobrecarga..");
		data.stream().forEach(persona -> {
			System.out.println(persona[0] + " es experto en " + persona[1]);
		});
		formatear();

		// Creo que obtiene todos los registros de la tabla persons
		/*for (int i = 0; i < 10; i++) {
			Person p = new Person();
			p.setName("Persona " + i);
			if(i % 2 == 0) {
				p.setProgrammingLanguage("Java");
			} else {
				p.setProgrammingLanguage("JavaScript");
			}
			repository.save(p);
		}*/
		// Voy a crear una persona, el id es pk
		/* 
		Person fredy = new Person();
		fredy.setName("Fredy");
		fredy.setLastname("Cañete");
		fredy.setProgrammingLanguage("Java");*/
		// realiza el codigo por debajo, el insert
		//repository.save(fredy);
	}


}
