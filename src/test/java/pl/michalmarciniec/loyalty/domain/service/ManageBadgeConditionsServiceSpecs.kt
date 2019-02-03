package pl.michalmarciniec.loyalty.domain.service

import pl.michalmarciniec.loyalty.db.BonusesCategoriesRepository
import pl.michalmarciniec.loyalty.db.BadgeConditionsRepository
import pl.michalmarciniec.loyalty.db.BadgesRepository
import pl.michalmarciniec.loyalty.domain.command.CreateBadgeConditionCommand
import pl.michalmarciniec.loyalty.domain.entity.*
import pl.michalmarciniec.loyalty.test.commons.mockBadge
import pl.michalmarciniec.loyalty.test.commons.mockNoLimitCategory
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*
import org.mockito.Mockito.`when` as _when

@RunWith(JUnitPlatform::class)
class ManageBadgeConditionsServiceSpecs : Spek({
    describe("Handles creation and deletion of conditions, at which badges should be automatically assigned to members") {
        val badgesRepository = mock<BadgesRepository>(BadgesRepository::class.java)
        val badgeConditionsRepository = mock<BadgeConditionsRepository>(BadgeConditionsRepository::class.java)
        val bonusesCategoriesRepository = mock<BonusesCategoriesRepository>(BonusesCategoriesRepository::class.java)
        val manageBadgeConditionsService = ManageBadgeConditionsService(
                badgesRepository,
                badgeConditionsRepository,
                bonusesCategoriesRepository
        )

        it("Creates new condition with bonus category") {

            val badgeId = 1L
            val badgeConditionType = BadgeConditionType.MEMBER_OF_THE_YEAR
            val badge = mockBadge()
            val bonusCategory = mockNoLimitCategory()
            val createBadgeConditionCommand = CreateBadgeConditionCommand(badgeId, bonusCategory.name, badgeConditionType)

            _when(badgesRepository.findById(badgeId)).thenReturn(Optional.of(badge))
            _when(badgeConditionsRepository.save(ArgumentMatchers.any(BadgeCondition::class.java))).then(returnsFirstArg<BadgeCondition>())
            _when(bonusesCategoriesRepository.findByName(bonusCategory.name)).thenReturn(Optional.of(bonusCategory))

            val badgeCondition = manageBadgeConditionsService.createBadgeCondition(createBadgeConditionCommand)

            assertThat(badgeCondition.badge).isEqualTo(badge)
            assertThat(badgeCondition.bonusCategory.isPresent).isEqualTo(true)
            assertThat(badgeCondition.bonusCategory.get()).isEqualTo(bonusCategory)
            assertThat(badgeCondition.conditionType).isEqualTo(badgeConditionType)
            Mockito.verify(badgeConditionsRepository).save(badgeCondition)
        }

        it("Creates new condition without bonus category") {

            val badgeId = 1L
            val badgeConditionType = BadgeConditionType.MEMBER_OF_THE_YEAR
            val badge = mockBadge()
            val createBadgeConditionCommand = CreateBadgeConditionCommand(badgeId, null, badgeConditionType)

            _when(badgesRepository.findById(badgeId)).thenReturn(Optional.of(badge))
            _when(badgeConditionsRepository.save(ArgumentMatchers.any(BadgeCondition::class.java))).then(returnsFirstArg<BadgeCondition>())

            val badgeCondition = manageBadgeConditionsService.createBadgeCondition(createBadgeConditionCommand)

            assertThat(badgeCondition.badge).isEqualTo(badge)
            assertThat(badgeCondition.bonusCategory.isPresent).isEqualTo(false)
            assertThat(badgeCondition.conditionType).isEqualTo(badgeConditionType)
            Mockito.verify(badgeConditionsRepository).save(badgeCondition)
        }

        it("Deletes existing condition") {
            val badgeConditionId = 1L

            manageBadgeConditionsService.deleteBadgeCondition(badgeConditionId)

            Mockito.verify(badgeConditionsRepository).delete(badgeConditionId)
        }
    }
})