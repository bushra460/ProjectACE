package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Car;

public interface CarRepository extends JpaRepository<Car,Long> {

    //Add methods for queries against
	@Query("select c from Car c where c.manufacturer.manufacturerId = :manufacturerId and c.model.modelId = :modelId and c.modelYear.modelYearId = :modelYearId and c.active = true")
	Car findCarByManfIdModIdYrId(@Param("manufacturerId") long manufacturerId, 
								 @Param("modelId") long modelId,
								 @Param("modelYearId") long modelYearId);
}