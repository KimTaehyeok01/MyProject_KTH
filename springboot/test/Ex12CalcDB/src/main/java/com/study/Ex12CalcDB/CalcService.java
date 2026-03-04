package com.study.Ex12CalcDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalcService {
    @Autowired
    private CalcRepository repository;

    public Integer calculateAndSave(Integer num1, Integer num2, String op){
        Integer result = 0;

        switch (op){
            case "add": result = num1 + num2; break;
            case "sub": result = num1 - num2; break;
            case "mul": result = num1 * num2; break;
            case "dev": if(num2 == 0) throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
                result = num1 / num2; break;
        }
        CalcEntity entity = new CalcEntity(null, op, num1, num2, result);
        repository.save(entity);
        return result;
    }
}
