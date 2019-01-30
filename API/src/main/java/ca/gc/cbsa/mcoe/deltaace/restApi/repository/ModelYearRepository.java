package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.ModelYear;

//@RepositoryRestResource(collectionResourceRel = "modelYear", path = "modelYear")
public interface ModelYearRepository extends JpaRepository<ModelYear,Long> {

    //Add methods for queries against
}