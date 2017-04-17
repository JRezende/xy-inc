package br.com.xy.dao;

import static br.com.xy.extractor.PoiExtractor.COORD_X_COLUMN;
import static br.com.xy.extractor.PoiExtractor.COORD_Y_COLUMN;
import static br.com.xy.extractor.PoiExtractor.ID_COLUMN;
import static br.com.xy.extractor.PoiExtractor.NAME_COLUMN;
import static br.com.xy.extractor.PoiExtractor.TABLE_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.xy.extractor.PoiExtractor;
import br.com.xy.model.Poi;

@Repository
public class PoiDao extends CustomJdbcDaoSupport {

  private final Logger log = LoggerFactory.getLogger(PoiDao.class);

  @Transactional
  public List<Poi> findAll() {
    List<Poi> pois = null;
    String sql = "select * from " + TABLE_NAME;
    pois = getJdbcTemplate().query(sql, new PoiExtractor().new PoiMapper());
    return pois;
  }

  @Transactional
  public List<Poi> findPoiByDistanceAndPoint(Long coordX, Long coordY, Long distance) {
    List<Poi> pois = null;
    String sql = "select point.* from " + TABLE_NAME + " point where " + "round(sqrt( power((point."
        + COORD_X_COLUMN + " - " + coordX + "), 2) + power((point." + COORD_Y_COLUMN + " - "
        + coordY + "),2)), 2) <= " + distance;

    try {
      pois = getJdbcTemplate().query(sql, new PoiExtractor().new PoiMapper());
    } catch (Exception e) {
      log.debug("Nao foi encontrado nenhum user com o id [{}]. ERROR -> ", coordX, coordY, distance,
          e);
    }
    return pois;
  }

  @Transactional
  public Boolean save(Poi poi) {
    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(poi);
    String sql = "insert into " + TABLE_NAME + " (" + NAME_COLUMN + "," + COORD_X_COLUMN + ","
        + COORD_Y_COLUMN + ") values (:name, :coordX, :coordY)";
    return namedParameterJdbcTemplate.update(sql, params) > 0 ? true : false;
  }

  @Transactional
  public Long saveAndReturnId(Poi poi) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    getJdbcTemplate().update(generateSQLInsert(poi), keyHolder);
    return keyHolder.getKey().longValue();
  }

  private PreparedStatementCreator generateSQLInsert(final Poi poi) {
    final String sql = "insert into " + TABLE_NAME + " (" + NAME_COLUMN + "," + COORD_X_COLUMN + ","
        + COORD_Y_COLUMN + ") values (?, ?, ?)";
    return new PreparedStatementCreator() {
      public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setString(1, poi.getName());
        ps.setLong(2, poi.getCoordX());
        ps.setLong(3, poi.getCoordY());
        return ps;
      }
    };
  }

  @Transactional
  public Poi findById(Long id) {
    String sql = "select * from " + TABLE_NAME + " where " + ID_COLUMN + " = ?";
    Poi poi = null;
    try {
      poi = getJdbcTemplate().queryForObject(sql, new PoiExtractor().new PoiMapper(), id);
    } catch (Exception e) {
      log.debug("Nao foi encontrado nenhum user com o id [{}]. ERROR -> ", id, e);
    }
    return poi;
  }

  @Transactional
  public Poi findByNameAndCoordinates(Poi poi) {
    Poi poiResult = null;
    String sql = "select * from " + TABLE_NAME + " where " + NAME_COLUMN + " = '" + poi.getName()
        + "' and " + COORD_X_COLUMN + " = " + poi.getCoordX() + " and " + COORD_Y_COLUMN + " = "
        + poi.getCoordY();
    try {
      poiResult = getJdbcTemplate().queryForObject(sql, new PoiExtractor().new PoiMapper());
    } catch (Exception e) {
      log.debug("Nao foi encontrado nenhum user com o id [{}]. ERROR -> ", poi, e);
    }
    return poiResult;
  }

  @Transactional
  public Boolean update(Poi poi) {
    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(poi);
    String sql = "update " + TABLE_NAME + " set " + NAME_COLUMN + " = :name," + COORD_X_COLUMN
        + " = :coordX," + COORD_Y_COLUMN + " = :coordY where " + ID_COLUMN + " = :id";
    return namedParameterJdbcTemplate.update(sql, params) > 0 ? true : false;
  }

  @Transactional
  public Boolean remove(Long id) {
    String sql = "delete from " + TABLE_NAME + " where " + ID_COLUMN + " = ?";
    return getJdbcTemplate().update(sql, id) > 0 ? true : false;
  }

}
