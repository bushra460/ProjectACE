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

import ca.gc.cbsa.mcoe.deltaace.restApi.model.HotspotDetail;
import ca.gc.cbsa.mcoe.deltaace.restApi.repository.HotspotDetailRepository;

@RestController
@RequestMapping("/hotspot-details")
public class HotspotDetailController {
	
	@Autowired 
	HotspotDetailRepository hotspotDetailRepository;
	
	@GetMapping()
    public List<HotspotDetail> all() {
        return hotspotDetailRepository.findAll();
    }
	
	@GetMapping("/{id}")
    public Optional<HotspotDetail> id(@PathVariable Long id) {
        return hotspotDetailRepository.findById(id);
    }
	
	@PostMapping(value="/add", headers = {"content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HotspotDetail addHotspotDetail(@RequestBody HotspotDetail hotspotDetail) {
		return hotspotDetailRepository.save(hotspotDetail);
	}

}
