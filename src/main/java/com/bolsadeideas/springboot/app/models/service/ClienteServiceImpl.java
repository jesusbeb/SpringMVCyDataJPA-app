package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

/*
 * Anotamos con Service, una clase Service esta basada en el patron de diseño facade o fachada. Es un punto de acceso a
 * diferentes DAO o repositorios. Dentro de una clase servicio se puede operarar con diferentes clases Dao
 * Asi no se accede de manera directa a los Dao desde los controladores 
 */
@Service
public class ClienteServiceImpl implements IClienteService{

	//Inyectamos el cliente Dao
	@Autowired
	private IClienteDao clienteDao;
	
	//Movemos los Transactional de la clase Dao a la Service
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		
		//return clienteDao.findAll();
		//findAll retorna un iterable por lo que se hace un cast a List<Cliente>
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		
		//return clienteDao.findOne(id);
		//se cambia al metodo del Crud Repository llamado findById, el cual retorna un optional
		//un optional envuelve el resultado de la consulta
		//.orElse obtiene un cliente y si no lo encuentra crea uno nuevo o retorna un null, en este caso si no lo encuentra sera null
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		//clienteDao.delete(id);
		//cambiaos al metodo del CrudRepository
		clienteDao.deleteById(id);
		
	}

}
