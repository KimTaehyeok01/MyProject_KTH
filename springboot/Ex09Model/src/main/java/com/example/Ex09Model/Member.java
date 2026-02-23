package com.example.Ex09Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

// ORM(Object Relation Mapping) : 객체와 Request 파라미터 맵핑

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    String username; // input태그 name속성과 이름 동일함. ORM매핑.
    String password;
}
