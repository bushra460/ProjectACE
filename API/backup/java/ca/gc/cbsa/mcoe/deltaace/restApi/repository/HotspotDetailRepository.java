package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.HotspotDetail;

//@RepositoryRestResource(collectionResourceRel = "hotspotDetail", path = "hotspotDetail")
public interface HotspotDetailRepository extends JpaRepository<HotspotDetail,Long> {

    //Add methods for queries against
}