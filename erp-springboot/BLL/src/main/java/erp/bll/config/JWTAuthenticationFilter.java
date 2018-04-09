package erp.bll.config;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import erp.bll.entities.AppUser;
import erp.bll.entities.CustomLdapUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	public Environment env;
	
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser appUser=null;
		
		
	
		try {
			appUser=new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println("user:"+appUser.getUsername());
		System.out.println("password"+appUser.getPassword());
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		UserDetails springUser=(UserDetails) authResult.getPrincipal();
		System.out.println(springUser);
		ZonedDateTime expirationTimeUtc=ZonedDateTime.now(ZoneOffset.UTC).plus(SecurityConstantes.EXPIRATION_TIME,ChronoUnit.MILLIS);
		String jwt = Jwts
	            .builder()
	            .setSubject(springUser.getUsername())
	           
	            .setExpiration(new Date(System.currentTimeMillis()+SecurityConstantes.EXPIRATION_TIME))
	            //algorithme HS512
	            .signWith(SignatureAlgorithm.HS256,SecurityConstantes.SECRET)
	            .claim("roles", springUser.getAuthorities())
	            .compact();
		response.addHeader(SecurityConstantes.HEADER_STRING,SecurityConstantes.TOKEN_PREFIX+jwt);
		//super.successfulAuthentication(request, response, chain, authResult);
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

	
	
	
}
