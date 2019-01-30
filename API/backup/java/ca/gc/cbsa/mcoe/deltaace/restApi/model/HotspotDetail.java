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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SuppressWarnings("serial")
@Entity
@Table(name="hotspot_details")
public class HotspotDetail implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="hotspot_detail_id")
	private Long hotspotDetailId;
	
	@Column(name="uri")
	private String uri;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="active_ind")
	private boolean active;
	
	@JsonBackReference(value="hotspot-location-hotspot-detail")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hotspot_id", nullable=false)
    public HotspotLocation hotspotLocation;
	
	
	//no-arg constructor for Hibernate
	public HotspotDetail() {};
	
	public HotspotDetail(Long hotspotDetailId, HotspotLocation hotspotLocation, String uri, String notes, boolean active){
		this.hotspotDetailId = hotspotDetailId;
		this.hotspotLocation = hotspotLocation;
		this.uri = uri;
		this.notes = notes;
		this.active = active;
	}
	 
	public HotspotLocation getHotspotLocation() {
		return hotspotLocation;
	}

	public void setHotspotLocation(HotspotLocation hotspotLocation) {
		this.hotspotLocation = hotspotLocation;
	}

	
	/**
	 * @return the hotspotDetailId
	 */
	public Long getHotspotDetailId() {
		return hotspotDetailId;
	}

	/**
	 * @param hotspotDetailId the hotspotDetailId to set
	 */
	public void setHotspotDetailId(Long hotspotDetailId) {
		this.hotspotDetailId = hotspotDetailId;
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
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotspotDetail )) return false;
        return hotspotDetailId != null && hotspotDetailId.equals(((HotspotDetail) o).hotspotDetailId);
    }
	
}