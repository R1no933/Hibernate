package hibernate.service;

import hibernate.dao.UserRepository;
import hibernate.dto.UserReadDto;
import hibernate.entity.User;
import hibernate.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.graph.GraphSemantic;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    public Optional<UserReadDto> findById(Long id) {
        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(),
                userRepository.getEntityManager().getEntityGraph("WithCompany")
        );
        return userRepository.findById(id, properties).
                map(userReadMapper::mapFrom);
    }

    public boolean delete(Long id) {
        Optional<User> userById = userRepository.findById(id);
        userById.ifPresent(user -> userRepository.delete(user.getId()));
        return userById.isPresent();
    }
}
