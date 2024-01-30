package kg.kstu.production.service.impl;

import kg.kstu.production.entity.Position;
import kg.kstu.production.repository.PositionRepository;
import kg.kstu.production.service.PositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PositionServiceImpl implements PositionService {
    final PositionRepository positionRepository;

    @Override
    public List<Position> getAll() {
        return positionRepository.findAll();
    }
}
