package com.codestates.edusync.model.study.plancalendar.dto;

import com.codestates.edusync.model.common.dto.TimeRangeDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

public class CalendarDto {

    @NoArgsConstructor
    @Getter
    public static class Post {
        @NotNull
        private List<TimeScheduleDto.Post> timeSchedules;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        @NotNull
        private TimeScheduleDto.Patch timeSchedule;
    }

    public static class TimeScheduleDto {

        @NoArgsConstructor
        @Getter
        public static class Post extends TimeRangeDto.Post {
            @NotNull
            private String title;

            @Nullable
            private String content;
        }

        @NoArgsConstructor
        @Getter
        public static class Patch extends TimeRangeDto.Patch {
            private Long id;
            private String title;
            private String content;
        }

    }
}