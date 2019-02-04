package ca.gc.cbsa.mcoe.deltaace.restApi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.ModelYear;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.ModelYearRepository;

@RestController
@RequestMapping("/model-years")
public class ModelYearController {
	
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
	
	@PostMapping(value="/add", headers = {"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ModelYear addModelYear(@RequestBody ModelYear modelYear) {
		return modelYearRepository.save(modelYear);
	}

}
