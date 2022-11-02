package fastcampus.projectboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPage, int totalPages) {
        int start = max(currentPage - (BAR_LENGTH / 2), 0);
        int end = min(start + BAR_LENGTH, totalPages);
        return IntStream.range(start, end).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }

}
