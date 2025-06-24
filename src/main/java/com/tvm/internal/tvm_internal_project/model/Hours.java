package com.tvm.internal.tvm_internal_project.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Embeddable
@Data
public class Hours {

    @Enumerated(EnumType.STRING)
    private WorkMode monday;
    @Enumerated(EnumType.STRING)
    private WorkMode tuesday;
    @Enumerated(EnumType.STRING)
    private WorkMode wednesday;
    @Enumerated(EnumType.STRING)
    private WorkMode thursday;
    @Enumerated(EnumType.STRING)
    private WorkMode friday;
}
