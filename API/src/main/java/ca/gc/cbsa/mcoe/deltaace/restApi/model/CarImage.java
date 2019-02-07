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
@Table(name="car_images")
public class CarImage implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="car_image_id")
	private Long carImageId;
	
	@Column(name="uri")
	private String uri;

	@Column(name="int_ext_image_ind")
	private boolean exteriorImage;
	
	@Column(name="active_ind")
	private boolean active;
	
	@JsonBackReference(value="car-car-image")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id", nullable=false)
    public Car car;
	
	@JsonManagedReference(value="car-image-hotspot-location")
	@OneToMany(mappedBy="carImage", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<HotspotLocation> hotspotLocations = new ArrayList<>();
	
	
	//no-arg constructor for Hibernate
	public CarImage() {};
	
	public CarImage(Long carImageId, Car car, String uri, String exteriorImage, boolean active){
		this.carImageId = carImageId;
		this.car = car;
		this.uri = uri;
		this.exteriorImage = "1".equals(exteriorImage);;
		this.active = active;
	}
	//used for Post - for Add Hotspot Location
	public CarImage(Long carImageId){
		this.carImageId = carImageId;
	}
	 
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	
	/**
	 * @return the carImageId
	 */
	public Long getCarImageId() {
		return carImageId;
	}

	/**
	 * @param carImageId the carImageId to set
	 */
	public void setCarImageId(Long carImageId) {
		this.carImageId = carImageId;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the exteriorImage
	 */
	public boolean isExteriorImage() {
		return exteriorImage;
	}

	/**
	 * @param exteriorImage the exteriorImage to set
	 */
	public void setExteriorImage(String exteriorImage) {
		this.exteriorImage = "1".equals(exteriorImage);
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	/**
	 * @return the hotspotLoc
	 */
	public List<HotspotLocation> getHotspotLocations() {
		return hotspotLocations;
	}

	/**
	 * @param hotspotLocation the hotspotLocation to set
	 */
	public void setHotspotLocations(List<HotspotLocation> hotspotLocations) {
		this.hotspotLocations = hotspotLocations;
	}

	public void addHotspotLocations(HotspotLocation hotspotLocation) {
        hotspotLocations.add(hotspotLocation);
        hotspotLocation.setCarImage(this);
    }
 
    public void removeHotspotLocations(HotspotLocation hotspotLocation) {
        hotspotLocations.remove(hotspotLocation);
        hotspotLocation.setCarImage(null);
    }
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarImage )) return false;
        return carImageId != null && carImageId.equals(((CarImage) o).carImageId);
    }
	
}