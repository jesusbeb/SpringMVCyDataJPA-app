package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//Repository es una anotacion de Spring para anotar la clase como componente de persistencia de accedo a datos
@Repository
public class ClienteDaoImpl implements IClienteDao {

	//EntityManager maneja las Clases de identidades. Las persiste dentro del contexto, actualiza, elimina, hace consultas
	//lo mismo que las BD pero a nivel de objetos. Las consultas son de JPA, son consultas que van a la clase Entity, no a la tabla
	//@PersistenceContext contiene la unidad de persistencia. De forma automatica inyectara el IdentityManager segun la configuracion de la unidad de persistencia
	@PersistenceContext
	private EntityManager em;
	
	//SuppressWarnings("unchecked") omite los warning. En este caso el que se genera abajo en createQuery
	//Transactional marca el metodo como transaccional y se coloca como solo lectura ya que es solo una consulta, si
	//fuera un insert, se omite el readOnly
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		//Consulta con createQuery a la clase Cliente. Retorna el listado de clientes 
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		//toma el objeto cliente y lo guarda dentro del contexto de persistencia (JPA). Va a sincronizar con
		//la BD y hara el insert en la tabla, automaticamente
		if(cliente.getId() != null && cliente.getId() >0 ) { //si el id es distinto de nulo y mayor que cero
			em.merge(cliente); //actualiza los datos existentes
		} else {
			em.persist(cliente); //crea un nuevo cliente
		}
	}

	@Override
	public Cliente findOne(Long id) {
		//metodo find del JPA, pasamos el nombre de la clase con Cliente.class y el id
		//Con esto JPA automaticamente va a la base de datos y nos da el objeto Cliente
		return em.find(Cliente.class, id);
	}

}
