package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.app.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner{

	//inyectamos la clase IUploadFileService ei instanciamos para usar sus metodos
	@Autowired
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Metodos de UploadFileService
		uploadFileService.deleteAll(); //Elimina la carpeta uploads con su contenido al bajar el proyecto
		uploadFileService.init(); //Crea la carpeta uploads al levantar el proyecto
	}

}
