package br.com.xy;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import br.com.xy.model.Poi;
import br.com.xy.model.PoiFilter;

@Configuration
@ComponentScan
public class TestConfiguration implements BeanFactoryPostProcessor {

  private static Poi poi;
  private static PoiFilter poiFilter;

  public TestConfiguration() {
    poi = new Poi();
    poiFilter = new PoiFilter();
  }

  @Bean
  public static DataSource dataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("db-schema.sql")
        .ignoreFailedDrops(true).build();
  }

  public static Poi initializeObjectPoi() {
    poi.setCoordX(5L);
    poi.setCoordY(10L);
    poi.setName("Teste Ponto");
    return poi;
  }

  public static PoiFilter initializeObjectPoiFilter() {
    poiFilter.setCoordX(20L);
    poiFilter.setCoordY(10L);
    poiFilter.setDistance(10L);
    return poiFilter;
  }

  //@Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    for (String beanName : beanFactory
        .getBeanNamesForType(ScheduledAnnotationBeanPostProcessor.class)) {
      ((DefaultListableBeanFactory) beanFactory).removeBeanDefinition(beanName);
    }
  }

}
