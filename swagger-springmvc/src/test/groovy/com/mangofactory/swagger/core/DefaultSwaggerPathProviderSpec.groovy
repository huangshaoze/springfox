package com.mangofactory.swagger.core

import com.mangofactory.swagger.mixins.RequestMappingSupport
import spock.lang.Specification

@Mixin(RequestMappingSupport)
class DefaultSwaggerPathProviderSpec extends Specification {

   def "Swagger url formats"() {
    given:
      AbsoluteSwaggerPathProvider defaultSwaggerPathProvider = new AbsoluteSwaggerPathProvider(apiResourceSuffix: "/api/v1/");
      defaultSwaggerPathProvider.servletContext = servletContext()

    expect:
      defaultSwaggerPathProvider."${method}"() == expected
    where:
      method                            | expected
      "getAppBasePath"                  | "http://127.0.0.1:8080/context-path"
      "getApiResourcePrefix"            | "/api/v1/"
   }

   def "should generate request mapping endpoints"() {
    given:

      AbsoluteSwaggerPathProvider defaultSwaggerPathProvider = new AbsoluteSwaggerPathProvider(apiResourceSuffix: "/api/v1/");
      defaultSwaggerPathProvider.servletContext = servletContext()

    expect:
      defaultSwaggerPathProvider.sanitizeRequestMappingPattern(mappingPattern) == expected

    where:
      mappingPattern             | expected
      ""                         | "/"
      "/"                        | "/"
      "/businesses"              | "/businesses"
      "/{businessId:\\w+}"       | "/{businessId}"
      "/businesses/{businessId}" | "/businesses/{businessId}"
      "/foo/bar:{baz}"           | "/foo/bar:{baz}"
      "/foo/bar:{baz:\\w+}"      | "/foo/bar:{baz}"

   }
}
