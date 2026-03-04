package com.test.countDB;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
@Component
@Getter @Setter
public class Counter {
    private int count;

}