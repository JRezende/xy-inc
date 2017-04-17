package br.com.xy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    //List<Poi> listaPois = poiService.findAllPoi();
    //return new ResponseEntity<List<Poi>>(listaPois, HttpStatus.OK);
    return poiService.findAllPoi();
  }
  
  @PostMapping
  public ResponseEntity<String> savePoi(@RequestBody Poi poi) {
    return poiService.savePoi(poi);
  }
  
  @PostMapping(path = "/findPoiByDistanceAndPoint")
  public ResponseEntity<List<Poi>> findPoisByDistanceAndPoint(@RequestBody PoiFilter poiFilter) {
    //List<Poi> listaPois = null;
    //listaPois =  poiService.findPoisByDistanceAndPoint(poiFilter);
    //return new ResponseEntity<List<Poi>>(listaPois, HttpStatus.OK);
    return poiService.findPoisByDistanceAndPoint(poiFilter);
  }
  
}

