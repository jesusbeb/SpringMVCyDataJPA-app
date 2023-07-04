package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.service.IClienteService;



@Controller //Controlador de Spring
@RequestMapping("/factura") //Url base de primer nivel del controlador
@SessionAttributes("factura") //Mantiene el objecto factura dentro de la sesion hasta que se envia a la BD
public class FacturaController {

	//Inyectamos el clienteService
	@Autowired
	private IClienteService clienteService;
	
	//Metodo crear y retorna el nombre de la vista
	@GetMapping("/form/{clienteId}") //url de la accion (metodo). /factura/form/1
	public String crear(@PathVariable(value="clienteId") Long clienteId, 
			Map<String, Object> model, 
			RedirectAttributes flash) {
		
		//Obtenemos el cliente por su id
		Cliente cliente = clienteService.findOne(clienteId);
		//Si el cliente es nulo mostramos un mensaje flash y lo redirigimos al listado de clientes
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos :(");
			return "redirect:/listar";
		}
		
		//Creamos una nueva factura y la asignamos a un cliente
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.put("factura", factura); //pasamos la factura a la vista
		model.put("titulo", "Crear Factura"); //pasamos un titulo
		
		//Las vistas de factura se crearan en la carpeta facturas
		return "factura/form";
	}
}
