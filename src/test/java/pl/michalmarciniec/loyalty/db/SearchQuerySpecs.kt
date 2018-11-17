package pl.michalmarciniec.loyalty.db

import pl.michalmarciniec.loyalty.domain.entity.QMember
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import com.querydsl.core.types.Predicate
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import org.mockito.Mockito.`when` as _when

@Suppress("ALWAYS_NULL", "CAST_NEVER_SUCCEEDS")
@RunWith(JUnitPlatform::class)
class SearchQuerySpecs : Spek({
    describe("Represents DB search query wrapping QueryDSL builder") {
        val repository = mock<MembersRepository>(MembersRepository::class.java)
        val mockedMember = mockMemberWithNoPermissions()
        _when(repository.findAll(any<Predicate>(Predicate::class.java))).thenReturn(listOf(mockedMember))

        it("Build query with null and non null predicates") {
            val searchQuery = SearchQuery(repository)
            searchQuery.addPredicate(1L, { builder, value -> builder.and(QMember.member.id.eq(value)) })
            searchQuery.addPredicate(null, { builder, value -> builder.and(QMember.member.createdAt.after(value as LocalDateTime)) })

            val queryResult = searchQuery.find()

            assertThat(queryResult).containsExactly(mockedMember)
        }

        it("Build query with no predicates") {
            val searchQuery = SearchQuery(repository)
            val queryResult = searchQuery.find()

            assertThat(queryResult).isEmpty()
        }


    }
})

