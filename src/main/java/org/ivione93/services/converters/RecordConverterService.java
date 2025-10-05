package org.ivione93.services.converters;

import jakarta.enterprise.context.ApplicationScoped;
import org.ivione93.dto.record.ExternalDto;
import org.ivione93.dto.record.InternalDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class RecordConverterService {

    public InternalDto mapToInternalDto(final ExternalDto externalDto) {
        AtomicReference<BigDecimal> pendingAmount = new AtomicReference<>(externalDto.initBalanceAmount());

        List<InternalDto.InternalMovement> movements = externalDto.movements().stream()
            .map(mov -> {
                BigDecimal newPending = pendingAmount.get().add(mov.amount());
                pendingAmount.set(newPending);
                return new InternalDto.InternalMovement(mov.creationDate(), mov.name(), mov.amount(), newPending);
            })
            .toList().reversed();

        return new InternalDto(externalDto.initBalanceAmount(), externalDto.endBalanceAmount(), movements);
    }
}
