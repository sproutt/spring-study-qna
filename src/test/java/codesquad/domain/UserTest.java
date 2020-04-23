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
        final String changedPassword = "asd";
        final String name = "kim";
        final String email = "minsub@gmail.com";
        user.changeUserInfo(changedPassword, name, email);

        final User user1 = User.builder()
                .userId("qwe")
                .password("asd")
                .name("kim")
                .email("minsub@gmail.com")
                .build();

        assertEquals(user.getPassword(), user1.getPassword());
        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getEmail(), user1.getEmail());
    }
}