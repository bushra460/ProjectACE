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
@Table(name="hotspot_loc")
public class HotspotLocation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="hotspot_id")
	private Long hotspotId;
	
	@Column(name="x_loc")
	private double xLoc;
	
	@Column(name="y_loc")
	private double yLoc;

	@Column(name="hotspot_loc_desc")
	private String hotspotDesc;
	
	@Column(name="active_ind")
	private boolean active;
	
	@JsonBackReference(value="car-image-hotspot-location")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_image_id", nullable=false)
    public CarImage carImage;

	@JsonManagedReference(value="hotspot-location-hotspot-detail")
	@OneToMany(mappedBy="hotspotLocation", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<HotspotDetail> hotspotDetails = new ArrayList<>();
	
	//no-arg constructor for Hibernate
	public HotspotLocation() {};
	
	public HotspotLocation(Long hotspotId, CarImage carImage, double xLoc, double yLoc, String hotspotDesc, boolean active){
		this.hotspotId = hotspotId;
		this.carImage = carImage;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		this.hotspotDesc = hotspotDesc;
		this.active = active;
	}
	 
	public CarImage getCarImage() {
		return carImage;
	}

	public void setCarImage(CarImage carImage) {
		this.carImage = carImage;
	}

	
	/**
	 * @return the hotspotId
	 */
	public Long getHotspotId() {
		return hotspotId;
	}

	/**
	 * @param hotspotId the hotspotId to set
	 */
	public void setHotspotId(Long hotspotId) {
		this.hotspotId = hotspotId;
	}

	/**
	 * @return the xLoc
	 */
	public double getxLoc() {
		return xLoc;
	}

	/**
	 * @param xLoc the xLoc to set
	 */
	public void setxLoc(double xLoc) {
		this.xLoc = xLoc;
	}

	/**
	 * @return the yLoc
	 */
	public double getyLoc() {
		return yLoc;
	}

	/**
	 * @param yLoc the yLoc to set
	 */
	public void setyLoc(double yLoc) {
		this.yLoc = yLoc;
	}

	/**
	 * @return the hotspotDesc
	 */
	public String getHotspotDesc() {
		return hotspotDesc;
	}

	/**
	 * @param hotspotDesc the hotspotDesc to set
	 */
	public void setHotspotDesc(String hotspotDesc) {
		this.hotspotDesc = hotspotDesc;
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
	 * @return the hotspotDetail
	 */
	public List<HotspotDetail> getHotspotDetails() {
		return hotspotDetails;
	}

	/**
	 * @param hotspotDetails the hotspotDetails to set
	 */
	public void setHotspotDetails(List<HotspotDetail> hotspotDetails) {
		this.hotspotDetails = hotspotDetails;
	}

	public void addHotspotDetail(HotspotDetail hotspotDetail) {
        hotspotDetails.add(hotspotDetail);
        hotspotDetail.setHotspotLocation(this);
    }
 
    public void removeHotspotLocations(HotspotDetail hotspotDetail) {
        hotspotDetails.remove(hotspotDetail);
        hotspotDetail.setHotspotLocation(null);
    }
    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotspotLocation )) return false;
        return hotspotId != null && hotspotId.equals(((HotspotLocation) o).hotspotId);
    }
	
}