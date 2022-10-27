package com.toolkit.inventory.Configuration;

import com.toolkit.inventory.Domain.ButcheryReceivingItem;
import com.toolkit.inventory.Projection.ButcheryReceivingItemView;
import com.toolkit.inventory.Projection.CustomerPageView;
import com.toolkit.inventory.Projection.CustomerSingleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class DataRestConfiguration implements RepositoryRestConfigurer {

  @Value("${allowed.origins}")
  private String[] theAllowedOrigins;

  private EntityManager entityManager;

  @Autowired
  public DataRestConfiguration(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @Override
  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

    config.getProjectionConfiguration().addProjection(CustomerPageView.class);
    config.getProjectionConfiguration().addProjection(CustomerSingleView.class);
    config.getProjectionConfiguration().addProjection(ButcheryReceivingItemView.class);

    // call an internal helper method
    exposeIds(config);

    cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);

  }

   private void exposeIds(RepositoryRestConfiguration config) {

    // expose entity ids

    // - get a list of all entity classes from the entity manager
    Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

    // - create an array of the entity types
    List<Class> entityClasses = new ArrayList<>();

    // - get the entity types for the entities
    for (EntityType tempEntityType : entities) {
      entityClasses.add(tempEntityType.getJavaType());
    }

    // - expose the entity ids for the array of entity/domain types
    Class[] domainTypes = entityClasses.toArray(new Class[0]);
    config.exposeIdsFor(domainTypes);
  }

}









