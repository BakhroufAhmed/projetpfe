package erp.bll.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.core.env.Environment;
import erp.bll.entities.CustomLdapUserDetails;
//import org.sid.service.UserDetailsServiceLdapAuthoritiesPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{	
	    @Autowired
	    private Environment env;
	    	

	@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
auth
.ldapAuthentication().userDetailsContextMapper(userDetailsContextMapper())
.userDnPatterns("uid={0},ou=people")
.userSearchBase("ou=people")
.userSearchFilter("uid={0}")
.groupSearchBase("ou=group")
.groupSearchFilter("uniqueMember={0}")
.contextSource(contextSource());

}
@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.csrf().disable();	
	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	//http.formLogin();
	http.authorizeRequests().antMatchers("/login/**","/register/**","/tasks","/").permitAll();
	http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**");
	http.authorizeRequests().anyRequest().authenticated();
	http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
	http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);	
	}
@Bean
public LdapUserDetailsMapper userDetailsContextMapper() {
    return new LdapUserDetailsMapper() {
        @Override
        public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
            UserDetails details = super.mapUserFromContext(ctx, username, authorities);
            return new CustomLdapUserDetails((LdapUserDetails) details, env);
        }
    };
}

@Bean
public DefaultSpringSecurityContextSource contextSource() {
    return  new DefaultSpringSecurityContextSource(
            Collections.singletonList(env.getRequiredProperty("spring.ldap.urls")), env.getRequiredProperty("spring.ldap.base"));
}
}
