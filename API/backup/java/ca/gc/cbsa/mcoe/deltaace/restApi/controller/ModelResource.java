package ca.gc.cbsa.mcoe.deltaace.restApi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Model;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.ModelRepository;

@RestController
@RequestMapping("/models")
public class ModelResource {
	
	@Autowired 
	ModelRepository modelRepository;
	
	@GetMapping()
    public List<Model> all() {
        return modelRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<Model> id(@PathVariable Long id) {
        return modelRepository.findById(id);
    }

}
