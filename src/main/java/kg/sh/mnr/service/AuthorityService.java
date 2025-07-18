package kg.sh.mnr.service;

import kg.sh.mnr.entity.dict.Authority;
import kg.sh.mnr.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepository repository;

    public Authority createAuthority(Authority authority) {
        return repository.save(authority);
    }

    public List<Authority> getAllAuthorities() {
        return repository.findAll();
    }

    public Authority getAuthorityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteAuthority(Long id) {
        repository.deleteById(id);
    }
}
