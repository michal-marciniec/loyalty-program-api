package pl.michalmarciniec.loyalty.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MemberTest {

    @Test
    fun shouldReceiveBonus() {
        val member = Member()

        member.receiveBonus(10, 2)

        assertThat(member.bonuses).hasSize(1)
        assertThat(member.bonuses[0].giverId).isEqualTo(10)
        assertThat(member.bonuses[0].points).isEqualTo(2)
    }
}