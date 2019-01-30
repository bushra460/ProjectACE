package ca.gc.cbsa.mcoe.deltaace.restApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Model;

//@RepositoryRestResource(collectionResourceRel = "model", path = "model")
public interface ModelRepository extends JpaRepository<Model,Long> {

    //Add methods for queries against
}