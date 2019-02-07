package ca.gc.cbsa.mcoe.deltaace.restApi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SuppressWarnings("serial")
@Entity 
@Table(name="model_year")
public class ModelYear implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="model_year_id")
	private Long modelYearId;
	
	@Column(name="year_value")
	private int yearValue;
	
	@JsonBackReference(value="model-model-year")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="model_id", nullable=false)
    private Model model;
	
	@JsonManagedReference(value="model-year-car")
	@OneToOne(mappedBy = "modelYear")
	private Car car;

	//no-arg constructor for Hibernate
	public ModelYear() {};
	
	public ModelYear(Long modelYearId, int yearValue, Model model){
		this.modelYearId=modelYearId;
		this.yearValue=yearValue;
		this.model=model;
	}
	
	//used for Post - for Add Vehicle
	public ModelYear(Long modelYearId){
		this.modelYearId = modelYearId;
	}
	 
    public Long getModelYearId() {
		return modelYearId;
	}

	public void setModelYearId(Long modelYearId) {
		this.modelYearId = modelYearId;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * @return the year
	 */
	public int getYearValue() {
		return yearValue;
	}

	/**
	 * @param year the year to set
	 */
	public void setYearValue(int yearValue) {
		this.yearValue = yearValue;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelYear )) return false;
        return modelYearId != null && modelYearId.equals(((ModelYear) o).modelYearId);
    }
	
    @Override
    public int hashCode() {
        return 31;
    }
	
    /**
	 * @return the car
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * @param car the car to set
	 */
	public void setCar(Car car) {
		this.car = car;
	}
	
}