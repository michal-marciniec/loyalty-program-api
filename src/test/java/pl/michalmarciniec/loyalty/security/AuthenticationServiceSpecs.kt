package pl.michalmarciniec.loyalty.security

import pl.michalmarciniec.loyalty.db.MembersRepository
import pl.michalmarciniec.loyalty.db.RolesRepository
import pl.michalmarciniec.loyalty.domain.entity.*
import pl.michalmarciniec.loyalty.security.AuthenticationServiceProd.*
import pl.michalmarciniec.loyalty.test.commons.mockMemberAsModerator
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.Mockito.*
import org.mockito.stubbing.Answer
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*
import kotlin.test.assertFailsWith
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class AuthenticationServiceSpecs : Spek({
    describe("Responsible for managing current member in session") {
        val membersRepository = mock<MembersRepository>(MembersRepository::class.java)
        val rolesRepository = mock<RolesRepository>(RolesRepository::class.java)
        val authenticationService = AuthenticationServiceProd(membersRepository, rolesRepository)

        it("Create member when extracting principal and no member in session found") {
            _when(membersRepository.findOneByLogin(any())).thenReturn(Optional.empty())
            _when(rolesRepository.findByName(Role.DEFAULT_ROLE_NAME)).thenReturn(Optional.of(Role.builder().build()))
            _when(membersRepository.save(any<Member>())).then(returnsFirstArg<Answer<Member>>())

            val principal = authenticationService.extractPrincipal(mockAuthDetails()) as Member
            assertThat(principal.name).isEqualTo("member")
            verify(membersRepository).save(any(Member::class.java))
        }

        it("Get member from session when extracting principal") {
            _when(membersRepository.findOneByLogin(any())).thenReturn(Optional.of(mockMemberAsModerator()))
            val principal = authenticationService.extractPrincipal(mockAuthDetails()) as Member
            assertThat(principal.name).isEqualTo("moderator")
        }

        it("Provide member's authorities") {
            _when(membersRepository.findOneByLogin(any())).thenReturn(Optional.of(mockMemberAsModerator()))
            val authorities = authenticationService.extractAuthorities(mockAuthDetails())
            assertThat(authorities).containsExactly(
                    Role.builder().name(RoleName.ROLE_MODERATOR).build(),
                    Permission.builder().name(PermissionName.MANAGE_OVERTIME).build())
        }

        it("Get current member") {
            val authentication = mock<Authentication>(Authentication::class.java)
            val securityCtx = mock<SecurityContext>(SecurityContext::class.java)
            _when(securityCtx.authentication).thenReturn(authentication)
            _when(authentication.principal).thenReturn(mockMemberWithNoPermissions())
            SecurityContextHolder.setContext(securityCtx)

            assertThat(authenticationService.currentMember).isNotNull()
        }

        it("Throw exception when trying to get current member, but no member in session") {
            val authentication = mock<Authentication>(Authentication::class.java)
            val securityCtx = mock<SecurityContext>(SecurityContext::class.java)
            _when(securityCtx.authentication).thenReturn(authentication)
            SecurityContextHolder.setContext(securityCtx)

            assertFailsWith<NoMemberInSessionException> {
                authenticationService.currentMember
            }
        }
    }
})

fun mockAuthDetails(): Map<String, String> {
    return mapOf(
            AUTH_DETAILS_NAME to "member",
            AUTH_DETAILS_EMAIL to "member@sample.com",
            AUTH_DETAILS_PICTURE to "default-avatar.png")
}