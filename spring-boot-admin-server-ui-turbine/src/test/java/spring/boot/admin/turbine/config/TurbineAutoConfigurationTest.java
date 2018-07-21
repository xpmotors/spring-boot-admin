package spring.boot.admin.turbine.config;

import de.codecentric.boot.admin.config.AdminServerCoreConfiguration;
import de.codecentric.boot.admin.config.AdminServerWebConfiguration;
import de.codecentric.boot.admin.config.RevereseZuulProxyConfiguration;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration.RestTemplateConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.cloud.commons.util.UtilAutoConfiguration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import spring.boot.admin.turbine.web.TurbineController;
import spring.boot.admin.turbine.zuul.filters.TurbineRouteLocator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public class TurbineAutoConfigurationTest {

    private AnnotationConfigWebApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void test_disabled() {
        load("spring.boot.admin.turbine.enabled:false",
                "spring.boot.admin.turbine.url:http://turbine.server:8989/turbine.stream");
        assertThat(context.getBeansOfType(TurbineController.class).values(), empty());
    }

    @Test
    public void test_enabled() {
        load("spring.boot.admin.turbine.url:http://turbine.server:8989/turbine.stream");
        assertThat(context.getBean(TurbineController.class), instanceOf(TurbineController.class));
    }

    @Test
    public void test_useStaticStreamUrl() {
        load("spring.boot.admin.turbine.enabled:true",
                "spring.boot.admin.turbine.useStaticStreamUrl:true",
                "spring.boot.admin.turbine.url:http://turbine.server:8989/turbine.stream");
        assertThat(context.getBean(TurbineController.class), instanceOf(TurbineController.class));
        assertThat(context.getBeansOfType(TurbineRouteLocator.class).values(), empty());
    }

    private void load(String... environment) {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(PropertyPlaceholderAutoConfiguration.class);
        applicationContext.register(RestTemplateConfiguration.class);
        applicationContext.register(ServerPropertiesAutoConfiguration.class);
        applicationContext.register(UtilAutoConfiguration.class);
        applicationContext.register(SimpleDiscoveryClientAutoConfiguration.class);
        applicationContext.register(AdminServerCoreConfiguration.class);
        applicationContext.register(AdminServerWebConfiguration.class);
        applicationContext.register(RevereseZuulProxyConfiguration.class);
        applicationContext.register(TurbineAutoConfiguration.class);

        EnvironmentTestUtils.addEnvironment(applicationContext, environment);
        applicationContext.refresh();
        this.context = applicationContext;
    }
}
