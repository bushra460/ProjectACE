package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Manufacturer;

//@RepositoryRestResource(collectionResourceRel = "manufacturers", path = "manufacturers")
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {

    //Add methods for queries against
	
}