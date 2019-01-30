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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SuppressWarnings("serial")
@Entity
//@Table(name="CAR", 
//       uniqueConstraints=
//            @UniqueConstraint(columnNames={"MANUFACTURER_ID", "MODEL_ID", "MODEL_YEAR_ID"})
//    )
@Table(name="car")
public class Car implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_id")
	private Long carId;
	
	@Column(name="active_ind")
	private boolean active;
	
	@JsonBackReference(value="manufacturer-car")
	@OneToOne
    @JoinColumn(name = "manufacturer_id", nullable=false)
    public Manufacturer manufacturer;
	
	@JsonBackReference(value="model-car")
	@OneToOne
    @JoinColumn(name = "model_id", nullable=false)
    public Model model;
	
	@JsonBackReference(value="model-year-car")
	@OneToOne
    @JoinColumn(name = "model_year_id", nullable=false)
    public ModelYear modelYear;
	
	@JsonManagedReference(value="car-car-image")
	@OneToMany(mappedBy="car", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<CarImage> carImage = new ArrayList<>();
	
	//no-arg constructor for Hibernate
	public Car() {};
	
	public Car(Long carId, boolean activeInd, Manufacturer manufacturer, Model model, ModelYear modelYear){
		this.carId = carId;
		this.active = activeInd;
		this.manufacturer = manufacturer;
		this.model = model;
		this.modelYear = modelYear;
	}
	 
    public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	/**
	 * @return the active indicator
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active indicator to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public ModelYear getModelYear() {
		return modelYear;
	}

	public void setModelYear(ModelYear modelYear) {
		this.modelYear = modelYear;
	}
	
    /**
	 * @return the carImage
	 */
	public List<CarImage> getCarImage() {
		return carImage;
	}

	/**
	 * @param carImage the carImage to set
	 */
	public void setCarImage(List<CarImage> carImage) {
		this.carImage = carImage;
	}

	public void addCarImage(CarImage carImage) {
        this.carImage.add(carImage);
        carImage.setCar(this);
    }
 
    public void removeCarImage(CarImage carImage) {
        this.carImage.remove(carImage);
        carImage.setCar(null);
    }
	

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car )) return false;
        return carId != null && carId.equals(((Car) o).carId);
    }
	
}