package com.intexsoft.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Configuration of Servlets
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Specify Configuration and/or Component classes for the
     * createRootApplicationContext() root application context.
     *
     * @return the configuration for the root application context
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfiguration.class};
    }

    /**
     * Specify Configuration and/or Component classes for the
     * {@linkplain #createServletApplicationContext() Servlet application context}.
     *
     * @return the configuration for the Servlet application context, or
     * {@code null} if all configuration is specified through root config classes.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    /**
     * Specify the servlet mapping(s) for the {@code DispatcherServlet}
     * for example {@code "/"}, {@code "/app"}, etc.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}

