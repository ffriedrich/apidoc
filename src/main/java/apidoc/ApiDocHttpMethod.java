package apidoc;

import java.util.Set;

/**
 * User: frank
 */
public class ApiDocHttpMethod {
    private String name;
    private String path;
    private Set<ApiDocParam> params;

    public ApiDocHttpMethod(String name) {

        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setParams(Set<ApiDocParam> params) {
        this.params = params;
    }

    public Set<ApiDocParam> getParams() {
        return params;
    }

    @Override
    public String toString() {
        String out = "ApiDocHttpMethod{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", params=";

        for (ApiDocParam param : params) {
            out += "\n" + param.toString();
        }
        out += "}\n";
        return out;
    }
}
