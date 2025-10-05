package org.ivione93.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ivione93.dto.record.ExternalDto;
import org.ivione93.dto.record.InternalDto;
import org.ivione93.services.converters.RecordConverterService;
import org.ivione93.services.providers.ExternalRecordService;

@ApplicationScoped
public class RecordService {

  @Inject
  ExternalRecordService externalRecordService;

  @Inject
  RecordConverterService recordConverterService;

  public InternalDto getRecords() {
    ExternalDto externalResponse = externalRecordService.getExternalResponse();
    return recordConverterService.mapToInternalDto(externalResponse);
  }

}
