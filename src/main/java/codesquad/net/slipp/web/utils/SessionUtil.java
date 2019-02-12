package codesquad.net.slipp.web.utils;


import codesquad.net.slipp.web.domain.User;
import codesquad.net.slipp.web.domain.UserRepository;
import codesquad.net.slipp.web.exception.SessionNotFoundException;
import codesquad.net.slipp.web.exception.SessionNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtil {

    @Autowired
    UserRepository userRepository;
    public boolean hasSession(HttpSession session){
        Object sessionedUser = session.getAttribute("userSession");
        if(sessionedUser == null){

            return false;
        }

        return true;
    }
    public User getSessionedUser(HttpSession session) {
        if (hasSession(session)) {

            throw new SessionNotFoundException();
        }

        return (User)session.getAttribute("userSession");
    }

    public void isSessionMatch(HttpSession session, User user){
        User sessiondUser = getSessionedUser(session);
        if(!sessiondUser.getUserId().equals(user.getUserId())){

            throw new SessionNotMatchException();
        }
    }
}
