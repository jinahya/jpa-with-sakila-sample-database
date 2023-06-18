package com.github.jinahya.sakila.data.jpa.repository;

import com.github.jinahya.sakila.persistence.Rental;
import com.github.jinahya.sakila.persistence.Rental_;
import com.github.jinahya.sakila.persistence.util.TemporalUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

@Repository
public interface RentalRepository
        extends _BaseEntityRepository<Rental, Integer> {

    Page<Rental> findAllByRentalDate(LocalDateTime dateOfRentalDate, Pageable pageable);

    Page<Rental> findAllByRentalDateBetweenOrderByRentalIdAsc(LocalDateTime rentalDateMinInclusive,
                                                              LocalDateTime rentalDateMaxInclusive,
                                                              Pageable pageable);

    default Page<Rental> findAllByRentalDateIn(final YearMonth monthOfRentalDate, final Pageable pageable) {
        return findAll(
                (r, q, b) -> TemporalUtils.applyStartEndTimeOf(
                        monthOfRentalDate,
                        (s, e) -> b.and(
                                b.greaterThanOrEqualTo(r.get(Rental_.rentalDate), s),
                                b.lessThan(r.get(Rental_.rentalDate), e)
                        )
                ),
                pageable
        );
    }

    default Page<Rental> findAllByRentalDateIn(final Year yearOfRentalDate, final Pageable pageable) {
        return findAll(
                (r, q, b) -> TemporalUtils.applyStartEndTimeOf(
                        yearOfRentalDate,
                        (s, e) -> b.and(
                                b.greaterThanOrEqualTo(r.get(Rental_.rentalDate), s),
                                b.lessThan(r.get(Rental_.rentalDate), e)
                        )
                ),
                pageable
        );
    }
}
