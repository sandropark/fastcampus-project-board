package fastcampus.projectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@DisplayName("비즈니스 로직 - 페이징")
@SpringBootTest(webEnvironment = NONE, classes = PaginationService.class)
class PaginationServiceTest {

    @Autowired PaginationService sut;

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어 준다.")
    @MethodSource
    @ParameterizedTest
    void givenCurrentPageAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(
            int currentPage,
            int totalPages,
            List<Integer> expected) throws Exception {
        // Given

        // When
        List<Integer> actual = sut.getPaginationBarNumbers(currentPage, totalPages);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenCurrentPageAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {
        return Stream.of(
                Arguments.of(0, 13, List.of(0, 1, 2, 3, 4)),
                Arguments.of(1, 13, List.of(0, 1, 2, 3, 4)),
                Arguments.of(2, 13, List.of(0, 1, 2, 3, 4)),
                Arguments.of(3, 13, List.of(1, 2, 3, 4, 5)),
                Arguments.of(4, 13, List.of(2, 3, 4, 5, 6)),
                Arguments.of(5, 13, List.of(3, 4, 5, 6, 7)),
                Arguments.of(8, 13, List.of(6, 7, 8, 9, 10)),
                Arguments.of(9, 13, List.of(7, 8, 9, 10, 11)),
                Arguments.of(10, 13, List.of(8, 9, 10, 11, 12)),
                Arguments.of(11, 13, List.of(8, 9, 10, 11, 12)),
                Arguments.of(12, 13, List.of(8, 9, 10, 11, 12))
        );
    }

    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() throws Exception {
        // When
        int actual = sut.currentBarLength();

        // Then
        assertThat(actual).isEqualTo(5);
    }

}