package pl.michalmarciniec.loyalty.api;

import pl.michalmarciniec.loyalty.domain.Member;
import pl.michalmarciniec.loyalty.service.db.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@LoyaltyProgramApi
@RequestMapping("/members")
public class MembersEndpoint {

    @Autowired
    private MembersRepository membersRepository;

    @RequestMapping(method = GET)
    public List<Member> getAllMembers() {
        return membersRepository.findAll();
    }

}
