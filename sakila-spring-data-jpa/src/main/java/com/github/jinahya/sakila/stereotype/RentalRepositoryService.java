package com.github.jinahya.sakila.stereotype;

import com.github.jinahya.sakila.data.jpa.repository.RentalRepository;
import com.github.jinahya.sakila.persistence.Rental;
import com.github.jinahya.sakila.persistence.Rental_;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Service
class RentalRepositoryService
        extends _BaseEntityRepositoryService<RentalRepository, Rental, Integer> {

    void findAllByRentalDate(
            @NotNull final LocalDateTime dateOfRentalDate, @Positive final int size,
            @NotNull final Consumer<? super Page<Rental>> function) {
        final var sort = Sort.by(Sort.Order.asc(Rental_.rentalId.getName()));
        for (var p = PageRequest.of(0, size, sort); ; p = p.next()) {
            final var pageable = p;
            final Page<Rental> page = applyRepositoryInstance(r -> r.findAllByRentalDate(dateOfRentalDate, pageable));
            if (page.isEmpty()) {
                break;
            }
            function.accept(page);
        }
    }
}
