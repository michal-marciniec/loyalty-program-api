package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.db.MembersRepository;
import pl.michalmarciniec.loyalty.domain.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@LoyaltyProgramApi
@RequestMapping("/members")
public class MembersEndpoint {

    @Autowired
    private MembersRepository membersRepository;

    @RequestMapping(method = GET)
    public List<MemberDto> getAllMembers() {
        return membersRepository.findAll().stream()
                .map(MemberDto::of)
                .collect(Collectors.toList());
    }

}
