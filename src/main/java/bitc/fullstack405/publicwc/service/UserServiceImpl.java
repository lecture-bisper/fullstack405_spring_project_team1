package bitc.fullstack405.publicwc.service;

import bitc.fullstack405.publicwc.entity.Users;
import bitc.fullstack405.publicwc.entity.WcInfo;
import bitc.fullstack405.publicwc.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void writeWc(WcInfo wcInfo) {
        // 구현이 필요한 경우 구현하세요.
    }

    @Override
    public Optional<Users> findById(String userId) {
        return usersRepository.findById(userId);
    }

    @Override
    public void saveUser(Users user) {
        usersRepository.save(user); // 엔티티를 저장하거나 업데이트합니다.
    }

    @Override
    public boolean checkPassword(Users user, String rawPassword) {
        return usersRepository.correctLogin(user.getId(), rawPassword) > 0;
    }

    @Override
    public void deleteUser(String userId) {
        usersRepository.deleteUsers(userId);
    }

    @Override
    public boolean useKeyForLevel3(String userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (user.getPasskey() > 0) {
                user.setPasskey(user.getPasskey() - 1);
                usersRepository.save(user);
                return true;
            }
        }
        return false;
    }
}