package apidoc;

import org.reflections.Reflections;

import javax.ws.rs.*;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: frank
 */
public class HttpMethodFinder {
    private Reflections reflections;

    public HttpMethodFinder(Reflections reflections) {
        this.reflections = reflections;
    }

    public List<ApiDocHttpMethod> findGetMethods() {
        return getApiDocHttpMethods(GET.class);
    }

    public List<ApiDocHttpMethod> findPutMethods() {
        return getApiDocHttpMethods(PUT.class);
    }

    public List<ApiDocHttpMethod> findPostMethods() {
        return getApiDocHttpMethods(POST.class);
    }

    public List<ApiDocHttpMethod> findDeleteMethods() {
        return getApiDocHttpMethods(DELETE.class);
    }

    private List<ApiDocHttpMethod> getApiDocHttpMethods(Class<? extends Annotation> httpMethod) {
        Set<Method> getMethods = reflections.getMethodsAnnotatedWith(httpMethod);
        List<ApiDocHttpMethod> apiDocHttpMethods = new ArrayList<>(getMethods.size());

        for (Method method : getMethods) {
            ApiDocHttpMethod apiDocHttpMethod = new ApiDocHttpMethod(method.getName());

            apiDocHttpMethod.setReturnParam(getReturnParameter(method));

            Set<ApiDocParam> params = new HashSet<>();
            StringBuilder pathBuilder = new StringBuilder();

            getPathOnMethodAndClass(method, pathBuilder, params);
            getPathOnFactoryMethodOrClass(method, pathBuilder, params);

            apiDocHttpMethod.setPath(pathBuilder.toString());
            apiDocHttpMethod.setInputParams(params);
            apiDocHttpMethods.add(apiDocHttpMethod);
        }

        return apiDocHttpMethods;
    }

    private Class<? extends Serializable> getReturnParameter(Method method) {
        if (method.isAnnotationPresent(Result.class)) {
            return method.getAnnotation(Result.class).value();
        }
        return null;
    }

    private void getPathOnMethodAndClass(Method method, StringBuilder pathBuilder, Set<ApiDocParam> params) {
        if (method.isAnnotationPresent(Path.class)) {
            String path = method.getAnnotation(Path.class).value();
            if (path.startsWith("/")) {
                pathBuilder.insert(0, "/" + path);
            } else {
                pathBuilder.insert(0, path);
            }
        }

        if (method.getDeclaringClass().isAnnotationPresent(Path.class)) {
            pathBuilder.insert(0, method.getDeclaringClass().getAnnotation(Path.class).value());
        }
        params.addAll(getParamsOfKind(method, PathParam.class));
        params.addAll(getParamsOfKind(method, QueryParam.class));
        params.addAll(getRegularParams(method));
    }

    private void getPathOnFactoryMethodOrClass(Method method, StringBuilder pathBuilder, Set<ApiDocParam> params) {
        for (Method factoryMethod : this.reflections.getMethodsReturn(method.getDeclaringClass())) {
            getPathOnMethodAndClass(factoryMethod, pathBuilder, params);
            getPathOnFactoryMethodOrClass(factoryMethod, pathBuilder, params);
        }
    }

    private Set<ApiDocParam> getRegularParams(Method method) {
        Set<ApiDocParam> apiDocParams = new HashSet<>();

        //TODO find all params that are not annotated with PathParam or QueryParam
        return apiDocParams;
    }

    private <T> Set<ApiDocParam> getParamsOfKind(Method method, Class<T> httpParamType) {
        Set<ApiDocParam> apiDocParams = new HashSet<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int parameterId = 0; parameterId < parameterAnnotations.length; parameterId++) {
            Annotation[] annotations = parameterAnnotations[parameterId];
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().equals(httpParamType)) {
                    Class<?> parameterType = method.getParameterTypes()[parameterId];

                    String parameterName = "";
                    if (annotation instanceof PathParam) {
                        parameterName = (PathParam.class.cast(annotation)).value();
                    } else if (annotation instanceof QueryParam) {
                        parameterName = (QueryParam.class.cast(annotation)).value();
                    }

                    apiDocParams.add(new ApiDocParam(parameterName, parameterType, httpParamType));
                }
            }
        }
        return apiDocParams;
    }

}
