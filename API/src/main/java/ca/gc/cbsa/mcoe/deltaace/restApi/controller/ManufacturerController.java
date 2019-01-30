package ca.gc.cbsa.mcoe.deltaace.restApi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Manufacturer;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.ManufacturerRepository;


@RestController
@RequestMapping("/manufacturers")
public class ManufacturerController {
	
	@Autowired 
	ManufacturerRepository manufacturerRepository;
	
	@GetMapping()
    public List<Manufacturer> all() {
        return manufacturerRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<Manufacturer> id(@PathVariable Long id) {
        return manufacturerRepository.findById(id);
    }

}
