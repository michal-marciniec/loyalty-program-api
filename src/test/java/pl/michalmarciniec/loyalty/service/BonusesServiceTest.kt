package pl.michalmarciniec.loyalty.service

import pl.michalmarciniec.loyalty.api.dto.BonusDto
import pl.michalmarciniec.loyalty.domain.Bonus
import pl.michalmarciniec.loyalty.domain.Member
import pl.michalmarciniec.loyalty.service.db.BonusesRepository
import pl.michalmarciniec.loyalty.service.db.MembersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*
import org.mockito.Mockito.`when` as _when

class BonusesServiceTest {

    @InjectMocks
    private lateinit var bonusesService: BonusesService
    @Mock
    private lateinit var membersRepo: MembersRepository
    @Mock
    private lateinit var bonusesRepo: BonusesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        _when(membersRepo.findById(2L)).thenReturn(Optional.of(Member()))
        _when(bonusesRepo.save(any(Bonus::class.java))).thenReturn(Bonus())
    }

    @Test
    fun shouldGiveBonus() {
        val bonusDto = BonusDto(1L, 2L, 10)

        val givenBonus = bonusesService.giveBonus(bonusDto)

        assertThat(givenBonus.isPresent)
        verify(bonusesRepo).save(any())
    }

}