package go.glogprototype.domain.notification.dao;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface EmitterRepository {
    SseEmitter get(Long userId);
}