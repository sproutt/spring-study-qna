package codesquad.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void isSamePassword() {
        final User user = User.builder()
                .userId("qwe")
                .password("123")
                .name("seob")
                .email("seob@hmail.com")
                .build();
        assertTrue(user.isSamePassword("123"));
    }

    @Test
    public void changeUserInfo() {
        final User user = User.builder()
                .userId("qwe")
                .password("123")
                .name("seob")
                .email("seob@hmail.com")
                .build();
        String changedPassword = "asd";
        String name = "kim";
        String email = "minsub@gmail.com";
        user.changeUserInfo(changedPassword, name, email);

        assertEquals(user.getPassword(), changedPassword);
        assertEquals(user.getName(), name);
        assertEquals(user.getEmail(), email);
    }
}