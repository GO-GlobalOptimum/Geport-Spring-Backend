package go.glogprototype.domain.notification.dao;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepositoryWrite {
    void save(Long id, SseEmitter emitter);
    void deleteById(Long userId);
}
