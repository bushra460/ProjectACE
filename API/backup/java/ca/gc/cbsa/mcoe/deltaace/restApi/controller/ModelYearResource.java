package ca.gc.cbsa.mcoe.deltaace.restApi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.ModelYear;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.ModelYearRepository;

@RestController
@RequestMapping("/model_years")
public class ModelYearResource {
	
	@Autowired 
	ModelYearRepository modelYearRepository;
	
	@GetMapping()
    public List<ModelYear> all() {
        return modelYearRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<ModelYear> id(@PathVariable Long id) {
        return modelYearRepository.findById(id);
    }

}
