package br.com.xy.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import br.com.xy.model.Poi;

public class PoiExtractor implements ResultSetExtractor<Poi> {

  public static final String TABLE_NAME = "xy_inc_db.POI";

  public static final String ALIAS_ID = "POI_ID";
  public static final String ID_COLUMN = "ID";
  public static final String NAME_COLUMN = "NAME";
  public static final String COORD_X_COLUMN = "COORD_X";
  public static final String COORD_Y_COLUMN = "COORD_Y";

  public Poi extractData(ResultSet rs) throws SQLException, DataAccessException {
    Poi poi = new Poi();
    try {
      poi.setId(rs.getLong(ALIAS_ID));
    } catch (Exception e) {
      poi.setId(rs.getLong(ID_COLUMN));
    }
    poi.setName(rs.getString(NAME_COLUMN));
    poi.setCoordX(rs.getLong(COORD_X_COLUMN));
    poi.setCoordY(rs.getLong(COORD_Y_COLUMN));

    return poi;
  }

  public class PoiMapper implements RowMapper<Poi> {
    public Poi mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
      PoiExtractor extractor = new PoiExtractor();
      return extractor.extractData(rs);
    }
  }
}
