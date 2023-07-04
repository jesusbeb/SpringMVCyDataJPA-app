package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity //clase de persistencia
@Table(name="facuras_items") //mapeamos a la tabla
public class ItemFactura implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Integer cantidad;
	
	//Relacion con la clase Producto. Instanciamos un objeto de la clase Producto
	//Mapeamos muchos itemFactura a un producto. La relacion inversa no se necesita
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_id") //se podria omitir ya que por defecto, ya que al no indicar el JoinColumn, tomara el nombre del objeto de tipo Producto en la tabla de la BD
	private Producto producto;
		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio(); //cantidad convertida a longValue
	}



	private static final long serialVersionUID = 1L;
}
