package com.supcon.orchid.SESECD.component;

import com.supcon.orchid.SESECD.model.dto.common.ChangeLogDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ChangeLogEvent extends ApplicationEvent {
    private ChangeLogDTO changeLogDTO;

    public ChangeLogEvent(Object source) {
        super(source);
    }

    public ChangeLogEvent(Object source, ChangeLogDTO changeLogDTO) {
        super(source);
        this.changeLogDTO = changeLogDTO;
    }
}
