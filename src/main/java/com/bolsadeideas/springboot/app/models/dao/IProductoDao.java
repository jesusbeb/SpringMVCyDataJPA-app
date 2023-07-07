/**
 * Interface para producto Dao
 */

package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Producto;


//La clase extiende de CrudRepository<Tipo de la clase entity, tipo de dato del id>
public interface IProductoDao extends CrudRepository<Producto, Long>{

	//Metodo que realiza una consulta a traves de un parametro
	//Creamos el metodo findByNombre sin implementar que retorna un List de tipo Producto y recibe un
	//parametro que es el term
	//Realizamos la consulta con la anotacion Query. Se usa select a nivel de objeto entity no a nivel de tabla
	//p es un alias para producto donde p.nombre se igual a ?1 que hace referencia al primer parametro que recibe la clase (term)
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
}
