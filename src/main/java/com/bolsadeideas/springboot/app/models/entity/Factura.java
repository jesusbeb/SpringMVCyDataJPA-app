package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

//Toda clase Entity como buena practica debe implementar de Serializable
@Entity //clase entidad de persistencia 
@Table(name="facturas") //Mapeamos a la tabla facturas. Si la clase se llamara igual que la tabla se puede omitir la anotacion, 
//aunque por buena practica las tablas en BD son en minuscula y las clases en Java son camel case
public class Factura implements Serializable {

	@Id //Id para indicar que es una llave primaria 
	@GeneratedValue(strategy=GenerationType.IDENTITY) //Autoincrementable a traves de la estrategia IDENTITY
	private Long id;
	
	private String descripcion;
	private String observacion;
	
	@Temporal(TemporalType.DATE) //TemporalType es el formato en que se guarda la fecha. .DATE Para indicar que solo se guardara la fecha, no se necesita la hora
	@Column(name="create_at") //Mapeamos el atributo de la clase a la columna de la tabla
	private Date createAt;
	
	//Mapeamos factura con clientes. Muchas facturas a un cliente. Fetch del tipo carga perezosa (LAZY) ya que EAGER trae toda la consulta de una sola vez
	//LAZY solo realiza la consulta cuando se le llama
	@ManyToOne(fetch=FetchType.LAZY)
	private Cliente cliente;

	//Metodo que genera la fecha automaticamente
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	//como se implementa de serializable tenemos que agregar este atributo (lo dejamos abajo ya que es un atributo interno)
	private static final long serialVersionUID = 1L;
}
