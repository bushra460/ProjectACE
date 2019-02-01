package ca.gc.cbsa.mcoe.deltaace.restApi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name="manufacturer")
public class Manufacturer implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="manufacturer_id")
	public Long manufacturerId;
	
	@Column(name="name")
	public String name;
	
	@JsonManagedReference(value="manufacturer-model")
	@OneToMany(mappedBy="manufacturer", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Model> models = new ArrayList<>();
	
	//no-arg constructor for Hibernate
	public Manufacturer() {};
	
	public Manufacturer(Long manufacturerId, String name){
			this.manufacturerId = manufacturerId;
			this.name = name;
	}
	
	//used for Post - for Add Vehicle
	public Manufacturer(Long manufacturerId){
		this.manufacturerId = manufacturerId;
	}

	/**
	 * @return the manufacturerId
	 */
	public Long getManufacturerId() {
		return manufacturerId;
	}

	/**
	 * @param manufacturerId the manufacturerId to set
	 */
	public void setManufacturerId(Long manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the model
	 */
	public List<Model> getModels() {
		return models;
	}

	/**
	 * @param model the model to set
	 */
	public void setModels(List<Model> models) {
		this.models = models;
	}

	public void addModels(Model model) {
        models.add(model);
        model.setManufacturer(this);
    }
 
    public void removeModels(Model model) {
        models.remove(model);
        model.setManufacturer(null);
    }
	
}