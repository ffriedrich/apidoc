package apidoc;

import java.io.Serializable;
import java.util.Set;

/**
 * User: frank
 */
public class ApiDocHttpMethod {
    private String name;
    private String path;
    private Set<ApiDocParam> inputParams;
    private Class<? extends Serializable> returnParam;

    public ApiDocHttpMethod(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setInputParams(Set<ApiDocParam> inputParams) {
        this.inputParams = inputParams;
    }

    public Set<ApiDocParam> getInputParams() {
        return inputParams;
    }

    public void setReturnParam(Class<? extends Serializable> returnParam) {
        this.returnParam = returnParam;
    }

    public Class<? extends Serializable> getReturnParam() {
        return returnParam;
    }

    public String getReturnParamAsJson() {
        if ((returnParam != null) && !returnParam.isPrimitive()) {
            return JsonHelper.toJson(returnParam);
        } else {
            return "";
        }

    }

    @Override
    public String toString() {
        String out = "ApiDocHttpMethod{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", inputParams=";

        for (ApiDocParam param : inputParams) {
            out += "\n" + param.toString();
        }
        out += "}\n";
        return out;
    }
}
