package ma.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.cfg.Environment;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.ClassUtils;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;



/**
 * ApplicationConfig.java
 * 
 * @author mtobori Date: 27 sept. 2018
 */
@Configuration
@PropertySources({
        // En local, utilisation du properties dans le jar
        @PropertySource({"classpath:application.properties"}),
        // Sur l'environnement, utilisation du properties sur le FileSystem
        @PropertySource(value = "file:${appli.conf.root}/application.properties", ignoreResourceNotFound = true),
        // Properties hibernate
        @PropertySource({"classpath:hibernate-webapp.properties"})
})
@ComponentScan(basePackages = "fr.gouv.impots.appli.topad")
public class ApplicationConfig
{
    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.format_sql}")
    private String formatSql;

    @Value("${hibernate.use_sql_comments}")
    private String useSqlComments;

    @Value("${hibernate.cache.use_second_level_cache}")
    private String useSecondLevelCache;

    @Value("${hibernate.cache.use_query_cache}")
    private String useQueryCache;

    @Value("${hibernate.cache.region.factory_class}")
    private String regionFactoryClass;

    /**
     * Constructeur de la classe ApplicationConfig.java
     */
    public ApplicationConfig()
    {
        super();
        // Constructeur vide
    }

    /**
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * @param dataSource
     * @return
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);

        String entities = ClassUtils.getPackageName(Domaine.class);
        String converters = ClassUtils.getPackageName(Jsr310JpaConverters.class);
        entityManagerFactoryBean.setPackagesToScan(entities, converters);

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        Properties jpaProperties = new Properties();
        jpaProperties.put(Environment.DIALECT, dialect);
        jpaProperties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(Environment.SHOW_SQL, showSql);
        jpaProperties.put(Environment.FORMAT_SQL, formatSql);
        jpaProperties.put(Environment.USE_SQL_COMMENTS, useSqlComments);
        jpaProperties.put(Environment.USE_SECOND_LEVEL_CACHE, useSecondLevelCache);
        jpaProperties.put(Environment.CACHE_REGION_FACTORY, regionFactoryClass);
        jpaProperties.put(Environment.USE_QUERY_CACHE, useQueryCache);
        jpaProperties.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    /**
     * @param autowireCapableBeanFactory
     * @return
     */
    @Bean
    public Validator validator(final AutowireCapableBeanFactory autowireCapableBeanFactory)
    {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
            .constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory)).buildValidatorFactory();

        return validatorFactory.getValidator();
    }

}