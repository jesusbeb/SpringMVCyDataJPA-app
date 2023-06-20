/*
 * Interface es la clase que contiene los metodos que tienen que implementar la clase DAO
 * Clase DAO (Data Access Object) es una clase de persistencia que se encarga de acceder a los datos
 */

package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

//Se elimino ClienteDaoImpl

//En el generic indicamos <tipo de dato, tipo de id>
//no tenemos anotacion de @Component o Repository sin embargo en ClienteServiceImpl se inyecta con Autowired
//ya que es una interface especial que hereda de CrudRepository y por debajo ya es un componente Spring
public interface IClienteDao extends CrudRepository<Cliente, Long>{

	/* Eliminamos los metodos que estaban contenidos, ya que ahora se estan implementado
	 * por detras en CrudRepository
	 * Se pueden tener metodos personalizados
	 */
	

}
