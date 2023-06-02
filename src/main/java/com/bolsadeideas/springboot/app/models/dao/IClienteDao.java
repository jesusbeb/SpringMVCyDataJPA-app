/*
 * Interface es la clase que contiene los metodos que tienen que implementar la clase DAO
 * Clase DAO (Data Access Object) es una clase de persistencia que se encarga de acceder a los datos
 */

package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

public interface IClienteDao {

	//metodo que retonar una lista de Cliente, que es la clase que esta mapeada a la tabla
	public List<Cliente> findAll();
}
