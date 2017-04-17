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

public class Poi implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private Long coordX;
  private Long coordY;

  public Poi() {
    // TODO Auto-generated constructor stub
  }

  public Poi(String name, Long coordX, Long coordY) {
    super();
    this.name = name;
    this.coordX = coordX;
    this.coordY = coordY;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Poi other = (Poi) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Poi [id=");
    builder.append(id);
    builder.append(", name=");
    builder.append(name);
    builder.append(", coordX=");
    builder.append(coordX);
    builder.append(", coordY=");
    builder.append(coordY);
    builder.append("]");
    return builder.toString();
  }

}
