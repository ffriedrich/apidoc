package apidoc;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.util.ClasspathHelper;

/**
 * User: frank
 */
public class ApiDoc {

    public static void generate(String packageToScan) {
        Reflections reflections = new Reflections(ClasspathHelper.forPackage(packageToScan), new MethodAnnotationsScanner(), new MethodParameterScanner());

        System.out.println("GET methods:");
        System.out.println(new HttpMethodFinder(reflections).findGetMethods().toString());

        System.out.println("PUT methods:");
        System.out.println(new HttpMethodFinder(reflections).findPutMethods().toString());

        System.out.println("POST methods:");
        System.out.println(new HttpMethodFinder(reflections).findPostMethods().toString());

        System.out.println("DELETE methods:");
        System.out.println(new HttpMethodFinder(reflections).findDeleteMethods().toString());
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
