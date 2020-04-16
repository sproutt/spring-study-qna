/*
package codesquad.service;

import codesquad.domain.User;
import codesquad.domain.UserSaveRequestDto;
import codesquad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Long save(final UserSaveRequestDto requestDto){
        return userRepository.save(requestDto.toEntity()).getId();
    }
}
*/
