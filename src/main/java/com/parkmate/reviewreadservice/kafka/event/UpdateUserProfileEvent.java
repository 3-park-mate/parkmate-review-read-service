package com.parkmate.reviewreadservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UpdateUserProfileEvent {

    private String userUuid;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timestamp;

    @Builder
    private UpdateUserProfileEvent(String userUuid,
                                   String name,
                                   LocalDateTime timestamp) {

        this.userUuid = userUuid;
        this.name = name;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }
}