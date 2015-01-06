package apidoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ClassUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * User: frank
 */
public class JsonHelper {

    public static String toJson(Class<?> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Object instance = createInstance(clazz);
            objectMapper.writeValue(baos, instance);
            return baos.toString("utf-8");
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Object createInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        if (shouldCreateInstance(clazz)) {
            Object instance = clazz.newInstance();

            for (Field field : clazz.getFields()) {
                if (shouldCreateInstance(field.getType())) {
                    field.set(instance, createInstance(field.getType()));
                }
            }
            return instance;
        }
        return null;
    }


    private static boolean shouldCreateInstance(Class<?> type) {
        return !ClassUtils.isPrimitiveOrWrapper(type) && !(type == String.class);
    }
}
