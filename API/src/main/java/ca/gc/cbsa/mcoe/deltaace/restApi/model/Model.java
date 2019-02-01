package ca.gc.cbsa.mcoe.deltaace.restApi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SuppressWarnings("serial")
@Entity
@Table(name="model")
public class Model implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="model_id")
	private Long modelId;
	
	@Column(name="name")
	private String name;
	
	@JsonBackReference(value="manufacturer-model")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manufacturer_id", nullable=false)
    public Manufacturer manufacturer;
	
	@JsonManagedReference(value="model-model-year")
	@OneToMany(mappedBy="model", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<ModelYear> modelYears = new ArrayList<>();

	//no-arg constructor for Hibernate
	public Model() {};
	
	public Model(Long modelId, String modelName, Manufacturer manufacturer){
		this.modelId=modelId;
		this.name=modelName;
		this.manufacturer=manufacturer;
	}
	
	//used for Post - for Add Vehicle
	public Model(Long modelId){
		this.modelId = modelId;
	}
	 
    public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
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

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model )) return false;
        return modelId != null && modelId.equals(((Model) o).modelId);
    }
	
    @Override
    public int hashCode() {
        return 31;
    }
	
	/**
	 * @return the modelYear
	 */
	public List<ModelYear> getModelYears() {
		return modelYears;
	}

	/**
	 * @param modelYear the modelYear to set
	 */
	public void setModelYears(List<ModelYear> modelYears) {
		this.modelYears = modelYears;
	}

	public void addModelYears(ModelYear modelYear) {
        modelYears.add(modelYear);
        modelYear.setModel(this);
    }
 
    public void removeModelYears(ModelYear modelYear) {
        modelYears.remove(modelYear);
        modelYear.setModel(null);
    }

}