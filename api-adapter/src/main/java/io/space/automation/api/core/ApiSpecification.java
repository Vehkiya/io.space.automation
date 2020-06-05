package io.space.automation.api.core;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ApiSpecification {

    private String specification;

    private String key;

    private Object value;
}
