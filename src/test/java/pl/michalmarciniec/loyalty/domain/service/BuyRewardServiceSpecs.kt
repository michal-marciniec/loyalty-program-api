package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.RewardsRepository
import pl.michalmarciniec.loyalty.domain.entity.RewardStatus
import pl.michalmarciniec.loyalty.domain.entity.exceptions.RewardSoldOutException
import pl.michalmarciniec.loyalty.domain.entity.exceptions.PointsAlreadySpentException
import pl.michalmarciniec.loyalty.security.AuthenticationService
import pl.michalmarciniec.loyalty.test.commons.mockReward
import pl.michalmarciniec.loyalty.test.commons.mockMemberWithNoPermissions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertFailsWith
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class BuyRewardServiceSpecs : Spek({
    describe("Responsible for handling buying reward process") {
        val rewardsRepository = mock<RewardsRepository>(RewardsRepository::class.java)
        val authenticationService = mock<AuthenticationService>(AuthenticationService::class.java)
        val buyRewardService = BuyRewardService(rewardsRepository, authenticationService)

        it("Buy a reward") {
            val currentMember = mockMemberWithNoPermissions()
            _when(authenticationService.currentMember).thenReturn(currentMember)
            val reward = mockReward(5, 1, LocalDateTime.MAX)
            _when(rewardsRepository.findById(anyLong())).thenReturn(Optional.of(reward))
            val claimedReward = buyRewardService.buyReward(1)

            assertThat(currentMember.wallet.gainedPoints).isEqualTo(5)
            assertThat(reward.amount).isEqualTo(0)
            assertThat(currentMember.rewards).containsExactly(claimedReward)
            assertThat(claimedReward.status).isEqualTo(RewardStatus.PENDING)
        }

        it("Reject buying a reward, because of invalid reward amount") {
            val currentMember = mockMemberWithNoPermissions()
            _when(authenticationService.currentMember).thenReturn(currentMember)
            val reward = mockReward(5, 0, LocalDateTime.MAX)
            _when(rewardsRepository.findById(anyLong())).thenReturn(Optional.of(reward))

            assertFailsWith<RewardSoldOutException> {
                buyRewardService.buyReward(1)
            }
        }

        it("Reject buying a reward, because of reward expiration date exceeded") {
            val currentMember = mockMemberWithNoPermissions()
            _when(authenticationService.currentMember).thenReturn(currentMember)
            val reward = mockReward(5, 0, LocalDateTime.MIN)
            _when(rewardsRepository.findById(anyLong())).thenReturn(Optional.of(reward))

            assertFailsWith<RewardSoldOutException> {
                buyRewardService.buyReward(1)
            }
        }

        it("Reject buying a reward, because of member has insufficient points") {
            val currentMember = mockMemberWithNoPermissions()
            _when(authenticationService.currentMember).thenReturn(currentMember)
            val reward = mockReward(500, 0, LocalDateTime.MAX)
            _when(rewardsRepository.findById(anyLong())).thenReturn(Optional.of(reward))

            assertFailsWith<PointsAlreadySpentException> {
                buyRewardService.buyReward(1)
            }
        }
    }
})