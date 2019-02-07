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

import ca.gc.cbsa.mcoe.deltaace.restApi.model.CarImage;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.CarImageRepository;

@RestController
@RequestMapping("/car-images")
public class CarImageController {
	
	@Autowired 
	CarImageRepository carImageRepository;
	
	@GetMapping()
    public List<CarImage> all() {
        return carImageRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<CarImage> id(@PathVariable Long id) {
        return carImageRepository.findById(id);
    }
	
	@PostMapping(value="/add", headers = {"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody CarImage addCarImage(@RequestBody CarImage carImage) {
		return carImageRepository.save(carImage);
	}
}
