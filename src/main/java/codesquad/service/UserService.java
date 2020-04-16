/*
package codesquad.service;

import codesquad.domain.UserSaveRequestDto;
import codesquad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public String save(final UserSaveRequestDto requestDto) {
        userRepository.save(requestDto.toEntity()).getId();
        return "redirect:/users";
    }
}
*/
