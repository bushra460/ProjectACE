package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.HotspotLocation;

//@RepositoryRestResource(collectionResourceRel = "hotspotLocation", path = "hotspotLocation")
public interface HotspotLocationRepository extends JpaRepository<HotspotLocation,Long> {

    //Add methods for queries against
	List<HotspotLocation> findByCarImageCarImageId(long carImageId);
}