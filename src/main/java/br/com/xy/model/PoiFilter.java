package br.com.xy.model;

import java.io.Serializable;

/*
 * 
 * Serviço para cadastrar pontos de interesse, com 3 atributos: Nome do POI, coordenada X(inteiro
 * não negativo) e coordenada Y (inteiro não negativo). Os POIs devem ser armazenadosem uma base de
 * dados.
 * 
 * Serviço para listar todos os POIs cadastrados.
 * 
 * Serviço para listar POIs por proximidade. Este serviço receberá uma coordenada X e uma coordenada
 * Y, especificando um ponto de referência, em como uma distância máxima (dmax)em metros. O serviço
 * deverá retornar todos os POIs da base de dados que estejam a uma distância menor ou igual a d-max
 * a partir do ponto de referência.
 * 
 */

public class PoiFilter implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long coordX;
  private Long coordY;
  private Long distance;

  /*public PoiFilter() {
    // TODO Auto-generated constructor stub
  }

  public PoiFilter(Long id, String name, Long coordX, Long coordY, Long distance) {
    super();
    this.coordX = coordX;
    this.coordY = coordY;
    this.distance = distance;
  }
*/
  public Long getCoordX() {
    return coordX;
  }

  public void setCoordX(Long coordX) {
    this.coordX = coordX;
  }

  public Long getCoordY() {
    return coordY;
  }

  public void setCoordY(Long coordY) {
    this.coordY = coordY;
  }

  public Long getDistance() {
    return distance;
  }

  public void setDistance(Long distance) {
    this.distance = distance;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PoiFilter [coordX=");
    builder.append(coordX);
    builder.append(", coordY=");
    builder.append(coordY);
    builder.append(", distance=");
    builder.append(distance);
    builder.append("]");
    return builder.toString();
  }

  
}
