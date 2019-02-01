package ca.gc.cbsa.mcoe.deltaace.restApi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.gc.cbsa.mcoe.deltaace.restApi.model.HotspotLocation;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.HotspotLocationRepository;

@RestController
@RequestMapping("/hotspot-locations")
public class HotspotLocationController {
	
	@Autowired 
	HotspotLocationRepository hotspotLocationRepository;
	
	@GetMapping()
    public List<HotspotLocation> all() {
        return hotspotLocationRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<HotspotLocation> id(@PathVariable Long id) {
        return hotspotLocationRepository.findById(id);
    }

}
