import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;

import java.io.IOException;

public class TestRunner {

    @Test
    public void doLogin() throws ConfigurationException, IOException {
        Customer customer=new Customer();
        customer.callingLoginAPI();
    }
    @Test
    public void getUserData() throws IOException {
        Customer customer=new Customer();
        customer.callingGetAPI();
    }
}
