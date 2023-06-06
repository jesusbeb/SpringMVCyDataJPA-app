package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;

import jakarta.validation.Valid;

//Marcamos y configuramos la clase como un controlador
@Controller
public class ClienteController {

	//atributo del cliente DAO para realizar la consulta
	@Autowired
	private IClienteDao clienteDao;
	
	//Metodo para listar los clientes
	//RequestMapping para validar, en value damos la ruta, method es el tipo de peticion, en este caso GET
	//si no se especifica el method, por defecto es GET, o sea que aqui se podria omitir
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	//Se importa la clase Model para pasar datos a la vista
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}
	
	//Metodo Request del tipo get, el segundo parametro que seria .GET lo omitimos que por defecto lo tiene
	@RequestMapping(value="/form")
	//Pasamos un mapa de java, nombre del parametro es del tipo String y el objeto que se guarda es un Object (objeto generico)
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();
		model.put("cliente", cliente); //pasamos el objeto cliente a la vista
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}
	
	//Metodo que procesa los datos del form. Tipo de metodo es POST
	@RequestMapping(value="/form", method=RequestMethod.POST)
	//Este metodo recibe el objeto cliente del formulario @Valid como argumento habilita la validacion
	//BindingResult siempre va enseguida del objeto del formulario
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model) {
		
		//si el BindingResult contiene errores
		if(result.hasErrors()) {
			//no se pasa el objeto cliente aqui, ya que se pasa automaticamente cuando falla, siempre y cuando el
			//nombre de la clase se llame igual con el nombre que se pasa a la vista
			//volvemos a pasar el titulo
			model.addAttribute("titulo", "Formulario de Cliente");
			//volvemos a mostrar el formulario ahora con los mensajes de error para ser corregidos por el usuario
			return "form";
		}
		
		clienteDao.save(cliente);
		return "redirect:listar";
	}
}
