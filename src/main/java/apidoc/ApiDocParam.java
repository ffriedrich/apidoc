package apidoc;

/**
 * User: frank
 */
public class ApiDocParam {
    private final String parameterName;
    private final Class<?> parameterType;
    private final Class<?> httpParamType;

    public <T> ApiDocParam(String parameterName, Class<?> parameterType, Class<T> httpParamType) {
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.httpParamType = httpParamType;
    }

    public String getAsJson() {
        return JsonHelper.toJson(parameterType);
    }

    @Override
    public String toString() {
        return "ApiDocParam{" +
                "parameterName='" + parameterName + '\'' +
                ", parameterType=" + parameterType +
                ", httpParamType=" + httpParamType +
                ", example=" + getAsJson() +
                '}';
    }
}
