package kg.kstu.production.service.impl;

import kg.kstu.production.entity.UnitOfMeasurement;
import kg.kstu.production.repository.UnitOfMeasurementRepository;
import kg.kstu.production.service.UnitOfMeasurementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {
    final UnitOfMeasurementRepository unitOfMeasurementRepository;

    @Override
    public List<UnitOfMeasurement> getAll() {
        return unitOfMeasurementRepository.findAll();
    }
}
