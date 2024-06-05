package go.glogprototype.domain.notification.application;

import go.glogprototype.domain.notification.dao.EmitterRepository;
import go.glogprototype.domain.notification.dao.EmitterRepositoryWrite;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Qualifier("emitterRepositoryImpl")
    private final EmitterRepository emitterRepository;

    @Qualifier("emitterRepositoryWriteImpl")
    private final EmitterRepositoryWrite emitterRepositoryWrite;

    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = createEmitter(userId);
        sendToClient(userId, "EventStream Created. [userId=" + userId + "]", "sse 접속 성공", "sse");
        return emitter;
    }

    public <T> void customNotify(Long userId, T data, String comment, String type) {
        sendToClient(userId, data, comment, type);
    }

    public void notify(Long userId, Object data, String comment) {
        sendToClient(userId, data, comment, "sse");
    }

    private <T> void sendToClient(Long userId, T data, String comment, String type) {
        SseEmitter emitter = emitterRepository.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(userId))
                        .name(type)
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                emitterRepositoryWrite.deleteById(userId);
                emitter.completeWithError(e);
            }
        }
    }

    private SseEmitter createEmitter(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepositoryWrite.save(userId, emitter);
        emitter.onCompletion(() -> emitterRepositoryWrite.deleteById(userId));
        emitter.onTimeout(() -> emitterRepositoryWrite.deleteById(userId));
        return emitter;
    }
}
