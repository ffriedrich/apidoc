package apidoc;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ClasspathHelper;

import java.util.List;

/**
 * User: frank
 */
public class ApiDoc {

    public static void generate(String packageToScan) {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(packageToScan), new MethodAnnotationsScanner(), new MethodParameterScanner());

        System.out.println("GET methods:");
        List<ApiDocHttpMethod> getMethods = new HttpMethodFinder(reflections).findGetMethods();
        System.out.println(getMethods.toString());

        System.out.println("PUT methods:");
        System.out.println(new HttpMethodFinder(reflections).findPutMethods().toString());

        System.out.println("POST methods:");
        System.out.println(new HttpMethodFinder(reflections).findPostMethods().toString());

        System.out.println("DELETE methods:");
        System.out.println(new HttpMethodFinder(reflections).findDeleteMethods().toString());

        System.out.println("Return parameters");
        for (ApiDocHttpMethod getMethod : getMethods) {
            if (getMethod.getReturnParam() != null) {
                String returnParamAsJson = getMethod.getReturnParamAsJson();
                System.out.println("GET " + getMethod.getPath() + ": " + returnParamAsJson);
            }
        }


    }


    //@Path kann an FactoryMethod annotiert sein, die dann Resourcen unter diesem Pfad zurückliefert

    //Javasisst, ASM, annovention


    //PluggableAnnotationProcessing (Java6)
    //https://github.com/ronmamo/reflections / https://code.google.com/p/reflections/
    //
    // das Ganze rekursiv..
    // @Path an Methode in anderer Klasse, die Klasse der Methode erzeugt
    // @Path an Klasse
    // @Path an Methode
    //@GET, @PUT etc => uri-pfad hierhin bestimmen!

}
