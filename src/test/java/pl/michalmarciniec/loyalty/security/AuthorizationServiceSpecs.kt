package pl.michalmarciniec.loyalty.security

import pl.michalmarciniec.loyalty.db.MembersRepository
import pl.michalmarciniec.loyalty.db.RolesRepository
import pl.michalmarciniec.loyalty.domain.command.GiveRoleCommand
import pl.michalmarciniec.loyalty.domain.entity.Role
import pl.michalmarciniec.loyalty.domain.entity.RoleName
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class AuthorizationServiceSpecs : Spek({
    describe("Responsible for managing user roles and permissions") {
        val rolesRepository = mock<RolesRepository>(RolesRepository::class.java)
        val membersRepository = mock<MembersRepository>(MembersRepository::class.java)
        val authorizationService = AuthorizationService(rolesRepository, membersRepository)

        it("Give role to a member") {
            _when(rolesRepository.findByName(RoleName.ROLE_ADMIN))
                    .thenReturn(Optional.of(Role.builder().permissions(Collections.emptyList()).build()))
            val member = mockMemberWithNoPermissions()
            _when(membersRepository.findById(1)).thenReturn(Optional.of(member))
            val giveRoleCommand = GiveRoleCommand(1, RoleName.ROLE_ADMIN)

            authorizationService.giveRole(giveRoleCommand)

            assertThat(member.hasAuthority(Role.builder().name(RoleName.ROLE_ADMIN).build()))
        }
    }
})