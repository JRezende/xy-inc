package br.com.xy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.xy.model.Poi;
import br.com.xy.model.PoiFilter;
import br.com.xy.service.PoiService;

@RestController
@RequestMapping("/poi")
public class PoiController {

  @Autowired
  private PoiService poiService;
  
  @GetMapping(path = "/pois")
  public ResponseEntity<List<Poi>> findAllPois() {
    return poiService.findAllPoi();
  }
  
  @PostMapping
  public ResponseEntity<String> savePoi(@RequestBody Poi poi) {
    return poiService.savePoi(poi);
  }
  
  @PostMapping(path = "/findPoiByDistanceAndPoint")
  public ResponseEntity<List<Poi>> findPoisByDistanceAndPoint(@RequestBody PoiFilter poiFilter) {
    return poiService.findPoisByDistanceAndPoint(poiFilter);
  }
  
  @GetMapping(path = "/{id}")
  public ResponseEntity<Poi>  getById(@PathVariable Long id) {
    return poiService.findPoiById(id);
  }
  
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String>  delete(@PathVariable Long id) {
    return poiService.deletePoi(id);
  }
}

