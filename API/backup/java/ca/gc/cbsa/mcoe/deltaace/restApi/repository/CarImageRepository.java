package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.CarImage;

//@RepositoryRestResource(collectionResourceRel = "carImage", path = "carImage")
public interface CarImageRepository extends JpaRepository<CarImage,Long> {

	//Add methods for queries against


}