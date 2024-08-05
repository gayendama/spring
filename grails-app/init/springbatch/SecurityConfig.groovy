package springbatch

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.KeycloakDeployment
import org.keycloak.adapters.KeycloakDeploymentBuilder
import org.keycloak.adapters.spi.HttpFacade
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.session.HttpSessionEventPublisher
import sn.sensoft.springbatch.RuntimeConfigService

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(keycloakAuthenticationProvider())
    }

    @Autowired
    RuntimeConfigService runtimeConfigService

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl())
    }

    @Bean
    ServletListenerRegistrationBean<HttpSessionEventPublisher> getHttpSessionEventPublisher() {
        new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher())
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure http
        http
            .csrf().disable()
            .logout()
            .logoutSuccessUrl("/sso/login") // Override Keycloak's default '/'
            .and()
            .authorizeRequests()
            .antMatchers("/healthCheck/**").permitAll()
            .antMatchers("/health-check/**").permitAll()
            .antMatchers("/assets/*").permitAll()
            .antMatchers("/index").fullyAuthenticated()
            .anyRequest().fullyAuthenticated()
    }


    // keycloak.json working...
    /**
     * Overrides default keycloak config resolver behaviour (/WEB-INF/keycloak.json) by a simple mechanism.
     * @return keycloak config resolver
     */
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakConfigResolver() {

            private KeycloakDeployment keycloakDeployment;

            @Override
            public KeycloakDeployment resolve(HttpFacade.Request facade) {
                if (keycloakDeployment != null) {
                    return keycloakDeployment
                }
                String path  = runtimeConfigService.getKeycloackConfigurationFile()
                InputStream configInputStream = new FileInputStream(path)

                if (configInputStream == null) {
                    throw new RuntimeException("Could not load Keycloak deployment info: " + path)
                } else {
                    keycloakDeployment = KeycloakDeploymentBuilder.build(configInputStream)
                }
                return keycloakDeployment
            }
        }
    }

}