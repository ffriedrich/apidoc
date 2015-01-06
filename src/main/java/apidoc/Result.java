package apidoc;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * User: frank
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Result {

    Class<? extends Serializable> value();
}
