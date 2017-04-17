package br.com.xy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.xy.dao.PoiDao;
import br.com.xy.model.Poi;
import br.com.xy.model.PoiFilter;

@Service
public class PoiService {

  private static final Logger log = LoggerFactory.getLogger(PoiService.class);

  @Autowired
  private PoiDao poiDao;

  public ResponseEntity<List<Poi>> findAllPoi() {
    List<Poi> pois = null;
    HttpStatus status = null;
    try {
      pois = poiDao.findAll();
      status = HttpStatus.OK;
    } catch (Exception e) {
      log.error("Erro! Sera retornado um INTERNAL_SERVER_ERROR da requisicao {}. ERROR: ", e);
    }
    //return pois;
    return new ResponseEntity<List<Poi>>(pois, status);
  }

  public ResponseEntity<String> savePoi(Poi poi) {
    HttpStatus status = null;
    log.debug("\n\n\n###### poi [{}]\n\n\n", poi.getId());
    String msg = "";
    try {
      validatesAttributesPoi(poi);
      validatesDuplicatePoi(poi);
      Long idPoi = poiDao.saveAndReturnId(poi);
      status = HttpStatus.CREATED;
      poi.setId(idPoi);
      msg = "Poi criado com sucesso. " + poi;
    } catch (IllegalArgumentException e) {
      log.error("Erro! Nao foi possivel criar o poi: {}", poi, e);
      status = HttpStatus.FORBIDDEN;
      msg = "Erro: " + e + ". " + poi.toString();
    } catch (Exception e) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
      msg = "Erro: INTERNAL_SERVER_ERROR da requisicao. " + poi.toString();
      log.error("Erro! Sera retornado um INTERNAL_SERVER_ERROR da requisicao {}. ERROR: ", poi, e);
    }
    // return new ResponseEntity<String>(status);
    return new ResponseEntity<String>(msg, status);
  }

  public ResponseEntity<List<Poi>> findPoisByDistanceAndPoint(PoiFilter poiFilter) {
    List<Poi> pois = null;
    HttpStatus status = null;
    try {
      validatesAttributesPoiFilter(poiFilter);
      pois = poiDao.findPoiByDistanceAndPoint(poiFilter.getCoordX(), poiFilter.getCoordY(),
          poiFilter.getDistance());
      status = HttpStatus.OK;
    } 
    catch (IllegalArgumentException e) {
      log.error("Erro! Nao foi possivel filtrar os poi: {}", poiFilter, e);
      status = HttpStatus.FORBIDDEN;
    }
    catch (Exception e) {
      log.error("Erro! Sera retornado um INTERNAL_SERVER_ERROR da requisicao {}. ERROR: ",
          poiFilter, e);
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    //return pois;
    return new ResponseEntity<List<Poi>>(pois, status);
  }

  private Exception validatesAttributesPoi(Poi poi) {
    if (poi.getCoordX() == null || poi.getCoordX() < 0 || poi.getCoordY() == null
        || poi.getCoordY() < 0) {
      throw new IllegalArgumentException("As coordenadas x e y não podem ser nulas ou negativas");
    }
    if (poi.getName() == null || poi.getName().isEmpty()) {
      throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
    }
    return null;
  }
  
  private Exception validatesDuplicatePoi(Poi poi) {
    if (poiDao.findByNameAndCoordinates(poi) != null) {
      throw new IllegalArgumentException("Já existe um Poi com mesmo nome e coordenadas iguais");
    }
    return null;
  }

  private Exception validatesAttributesPoiFilter(PoiFilter poiFilter) {
    if (poiFilter.getCoordX() == null || poiFilter.getCoordX() < 0) {
      throw new IllegalArgumentException("Coordenada X não pode ser nula ou negativa");
    }
    if (poiFilter.getCoordY() == null || poiFilter.getCoordY() < 0) {
      throw new IllegalArgumentException("Coordenada Y não pode ser nula ou negativa");
    }
    if (poiFilter.getDistance() == null || poiFilter.getDistance() < 0) {
      throw new IllegalArgumentException("Distância não pode ser nula ou negativa");
    }
    return null;
  }
  
  

}
