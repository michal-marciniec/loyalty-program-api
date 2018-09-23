package pl.michalmarciniec.loyalty.domain;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationService authenticationService;
    private final MembersRepository membersRepository;

    public List<MemberDto> getAllMembers() {
        return membersRepository.findAll().stream()
                .map(MemberDto::of)
                .collect(Collectors.toList());
    }

    public Optional<MemberDto> getCurrentMember() {
        return authenticationService.getCurrentUser()
                .flatMap((user) -> membersRepository.findOneByEmail(user.getEmail()))
                .map(MemberDto::of);
    }
}
