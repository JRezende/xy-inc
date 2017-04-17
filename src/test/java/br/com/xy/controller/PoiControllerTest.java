package br.com.xy.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.xy.TestConfiguration;
import br.com.xy.dao.PoiDao;
import br.com.xy.model.Poi;
import br.com.xy.model.PoiFilter;
import br.com.xy.service.PoiService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfiguration.class)
@SpringBootTest
public class PoiControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private PoiService poiService;

  @Autowired
  private PoiDao poiDao;

  private Gson gson;

  private List<Poi> listResultPoiByDistanceAndPoint;

  /*
   * @Before public void setUp() { this.mockMvc =
   * MockMvcBuilders.standaloneSetup(poiController).build(); gson = new Gson(); }
   */

  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    gson = new Gson();
    listResultPoiByDistanceAndPoint = new ArrayList<Poi>();
    /*
     * 1-Lanchonete; 2-Posto; 3-Joalheria; 4-Floricultura; 5-Pub; 6-Supermercado; 7-Churrascaria
     * 
     * 
     * Pontos com distancia 10 do ponto x=20, y=10 Lanchonete; Joalheria; Pub; Supermercado
     */
    listResultPoiByDistanceAndPoint.add(poiDao.findById(1L));
    listResultPoiByDistanceAndPoint.add(poiDao.findById(3L));
    listResultPoiByDistanceAndPoint.add(poiDao.findById(5L));
    listResultPoiByDistanceAndPoint.add(poiDao.findById(6L));
  }

  @Test
  public void testGETfindAllPois() throws Exception {
    List<Poi> listaPois = poiDao.findAll();
    // this.mockMvc.perform(MockMvcBuilders.get("/salarios")).andExpect(MockMvcBuilders.status().isOk());
    this.mockMvc.perform(get("/poi/pois")).andExpect(status().isOk())
        // .andExpect(content().string(gson.toJson(listaPois)));
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(gson.toJson(listaPois)));
    // .andDo(print());
  }

  @Test
  public void testPOSTsavePoi() throws Exception {
    this.mockMvc
        .perform(post("/poi").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(TestConfiguration.initializeObjectPoi())))
        .andExpect(status().isCreated())
        /*.andExpect(content().string(
            "Poi criado com sucesso. " + TestConfiguration.initializeObjectPoi().toString()))*/
        .andDo(print());
  }

  @Test
  public void testPOSTsavePoiWithNegativeCoordinate() throws Exception {
    Poi poi = TestConfiguration.initializeObjectPoi();
    poi.setCoordX(-10L);
    this.mockMvc
        .perform(post("/poi").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(poi)))
        .andExpect(status().isForbidden())
        .andExpect(content().string(
            "Erro: java.lang.IllegalArgumentException: As coordenadas x e y não podem ser nulas ou negativas. "
                + poi.toString()));
  }

  @Test
  public void testPOSTsavePoiWithNameNull() throws Exception {
    Poi poi = TestConfiguration.initializeObjectPoi();
    poi.setName(null);
    this.mockMvc
        .perform(post("/poi").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(poi)))
        .andExpect(status().isForbidden())
        .andExpect(content()
            .string("Erro: java.lang.IllegalArgumentException: Nome não pode ser nulo ou vazio. "
                + poi.toString()));
  }

  @Test
  public void testPOSTsavePoiWithNameAndCoordinatesDuplicates() throws Exception {
    Poi poi = TestConfiguration.initializeObjectPoi();
    poi.setName("Teste Poi duplicado");
    poiService.savePoi(poi);
    Poi poi2 = TestConfiguration.initializeObjectPoi();
    poi2.setName("Teste Poi duplicado");
    this.mockMvc
        .perform(
            post("/poi").contentType(MediaType.APPLICATION_JSON_UTF8).content(gson.toJson(poi2)))
        .andExpect(status().isForbidden())
        .andExpect(content().string(
            "Erro: java.lang.IllegalArgumentException: Já existe um Poi com mesmo nome e coordenadas iguais. "
                + poi2.toString()));
  }

  @Test
  public void testGETfindPoiByDistanceAndPoint() throws Exception {
    List<Poi> listaPoisDistance = listResultPoiByDistanceAndPoint;
    this.mockMvc
        .perform(post("/poi/findPoiByDistanceAndPoint")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(TestConfiguration.initializeObjectPoiFilter())))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(gson.toJson(listaPoisDistance)))
    .andDo(print());
  }
  
  @Test
  public void testGETfindPoiByDistanceAndPointWithDistanceNegative() throws Exception {
    PoiFilter poiFilter = TestConfiguration.initializeObjectPoiFilter();
    poiFilter.setDistance(-10L);
    this.mockMvc
        .perform(post("/poi/findPoiByDistanceAndPoint")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(poiFilter)))
        .andExpect(status().isForbidden())
    .andDo(print());
  }
  
  @Test
  public void testGETfindPoiByDistanceAndPointWithCoordinatesNegative() throws Exception {
    PoiFilter poiFilter = TestConfiguration.initializeObjectPoiFilter();
    poiFilter.setCoordX(-10L);
    this.mockMvc
        .perform(post("/poi/findPoiByDistanceAndPoint")
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .content(gson.toJson(poiFilter)))
        .andExpect(status().isForbidden())
    .andDo(print());
  }

}
