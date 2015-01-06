package apidoc;

import java.io.Serializable;

/**
 * User: frank
 */
public class UserDto implements Serializable {

    public int userId;

    public String lastname;

    public String firstname;

    public AddressDto address;
}
