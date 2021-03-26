import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.vertx.ext.auth.User;

public class UserTest {
    private User user;

    @Before
    public void setup(){
        this.user = new User();
    }

    @After
    public void finish(){}

    @Test
    public void getUser(){
        assertNotNull("name is null",user());
    }
    
}
