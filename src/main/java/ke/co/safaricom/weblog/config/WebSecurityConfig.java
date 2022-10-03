package ke.co.safaricom.weblog.config;

import ke.co.safaricom.weblog.middleware.JwtTokenFilter;
import ke.co.safaricom.weblog.user.service.UserDetailsExtra;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsExtra userDetailsExtra;

    private final JwtTokenFilter jwtTokenFilter;
    public WebSecurityConfig(UserDetailsExtra userDetailsExtra, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsExtra = userDetailsExtra;

        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsExtra).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().
                antMatchers("/user","/auth/login/**").permitAll()

//                .antMatchers("/user/add-role").hasAuthority("admin")
//                .antMatchers("/roles/**").hasAuthority("admin")
//                .antMatchers("/blogs/**").hasAuthority("member")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter,BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
