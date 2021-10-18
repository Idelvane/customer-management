package io.github.idelvane.customermanagement.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

/**
 * Configuração da paginação
 * 
 */
@Configuration
public class PageableConfiguration {

	/**
	 * Permite configuração customizada do Pageable 
	 * 
	 * @return <code>PageableHandlerMethodArgumentResolverCustomizer</code> object
	 */
    @Bean
    PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {
        return pageableResolver -> pageableResolver.setOneIndexedParameters(true);
    }
    
    /**
	 * Permite configuração customização de Sort
	 * 
	 * @return <code>SortHandlerMethodArgumentResolverCustomizer</code> object
	 */
    @Bean
    SortHandlerMethodArgumentResolverCustomizer sortResolverCustomizer() {
        return sortResolver -> sortResolver.setSortParameter("sort");
    }
}