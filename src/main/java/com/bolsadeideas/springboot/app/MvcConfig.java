/* 
 * 
 */
package com.bolsadeideas.springboot.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Sobreescribimos el metodo addResourceHandlers para agregar directorios recursos a nuestro proyecto
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//usamos registry para registrar la nueva ruta como recurso estatico, que es la misma en ver.html
		//doble asterisco para mapear al nombre de la imagen y se pueda cargar
		registry.addResourceHandler("/uploads/**")
		.addResourceLocations("file:/C:/Temp/uploads/"); //directorio fisico
	}

	
}
