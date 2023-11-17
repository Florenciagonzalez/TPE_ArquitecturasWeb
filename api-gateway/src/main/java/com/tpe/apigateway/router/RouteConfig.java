package com.tpe.apigateway.router;

import com.tpe.apigateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder, AuthenticationFilter authFilter){
        return builder.routes()
                .route("registro", r -> r.path("/usuarios/registrarse")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8001"))

                .route("autenticacion", r -> r.path("/usuarios/autenticar")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8001"))

                .route("validar", r -> r.path("/usuarios/**")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8001"))

                .route("monopatines", r -> r.path("/monopatines/**")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8004"))

                .route("mantenimiento", r -> r.path("/mantenimiento/**")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8003"))

                .route("paradas", r -> r.path("/paradas/**")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8005"))

                .route("administracion", r -> r.path("/administradores/**")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8002"))

                .route("tarifas", r -> r.path("/tarifas/**")
                .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                .uri("http://localhost:8002"))
                .build();
    }
}
