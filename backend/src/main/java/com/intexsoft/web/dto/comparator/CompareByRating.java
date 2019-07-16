package com.intexsoft.web.dto.comparator;

import com.intexsoft.web.dto.response.BookResponseShortVersionDTO;

import java.util.Comparator;

public class CompareByRating implements Comparator<BookResponseShortVersionDTO> {

    /**
     * if rating of the first book is null Comparator return 1 if rating of the second book lower then 3
     *
     * @param o1 first BookDTO
     * @param o2 second BookDTO
     * @return 1, 0 or -1
     */
    @Override
    public int compare(BookResponseShortVersionDTO o1, BookResponseShortVersionDTO o2) {
        Float rating1 = o1.getRating(), rating2 =o2.getRating();

        if (rating1 == null) {
            if (rating2 == null) {
                return 0;
            } else if (rating2 < 3) {
                return 1;
            } else {
                return -1;
            }
        }
        if (rating2 == null) {
            if (rating1 < 3) {
                return -1;
            } else {
                return 1;
            }
        }
        return Float.compare(rating1,rating2);
    }
}
