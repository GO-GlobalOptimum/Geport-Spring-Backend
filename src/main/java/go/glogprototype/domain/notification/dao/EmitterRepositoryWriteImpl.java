package go.glogprototype.domain.notification.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepositoryWriteImpl implements EmitterRepositoryWrite {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public void save(Long id, SseEmitter emitter) {
        emitters.put(id, emitter);
    }

    @Override
    public void deleteById(Long userId) {
        emitters.remove(userId);
    }
}