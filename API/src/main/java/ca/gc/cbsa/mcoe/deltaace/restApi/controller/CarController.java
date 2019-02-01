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

import ca.gc.cbsa.mcoe.deltaace.restApi.model.Car;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.CarRepository;

@RestController
@RequestMapping("/cars")
public class CarController {
	
	@Autowired 
	CarRepository carRepository;
	
	@GetMapping()
    public List<Car> all() {
        return carRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<Car> id(@PathVariable Long id) {
        return carRepository.findById(id);
    }
	
	@GetMapping(value = "/mnf/{manufacturerid}/mod/{modelid}/yr/{yearid}")
	public Car getCarByManfIdModIdYrId
	  (@PathVariable long manufacturerid, @PathVariable long modelid,@PathVariable long yearid) {
		return carRepository.findCarByManfIdModIdYrId(manufacturerid,modelid,yearid);
	}
	
	@PostMapping(value="/add", headers = {"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Car addVehicle(@RequestBody Car car) {
		return carRepository.save(car);
	}

}
