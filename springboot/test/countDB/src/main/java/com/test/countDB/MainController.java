package com.test.countDB;

import com.test.countDB.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private Counter counter;

    private final CountRepository countRepository;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("count", counter.getCount());
        List<CountEntity> optional = countRepository.findAll();
        return "index";
    }

    @GetMapping("/plus")
    public String plus(Model model) {
        counter.setCount(counter.getCount() + 1);
        model.addAttribute("count", counter.getCount());

        Optional<CountEntity> optional = countRepository.findById(1);
        optional.ifPresent(countEntity -> {
            Integer count = countEntity.getCount();
            countEntity.update(count + 1);
            countRepository.save(countEntity);
        });
        return "redirect:/";
    }

    @GetMapping("/minus")
    public String minus(Model model) {
        counter.setCount(counter.getCount() - 1);
        model.addAttribute("count", counter.getCount());

        Optional<CountEntity> optional = countRepository.findById(1);
        optional.ifPresent(countEntity -> {
            Integer count = countEntity.getCount();
            countEntity.update(count - 1);
            countRepository.save(countEntity);
        });
        return "redirect:/";
    }

    @GetMapping("/api/plus")
    @ResponseBody
    public String api_plus(Model model) {
        counter.setCount(counter.getCount() + 1);

        Optional<CountEntity> optional = Optional.of(countRepository.findById(1).get());
        optional.ifPresent(countEntity -> {
            Integer count = countEntity.getCount();
            countEntity.update(count + 1);
            countRepository.save(countEntity);
        });

        return String.valueOf(counter.getCount());
    }

    @GetMapping("/api/minus")
    @ResponseBody
    public String api_minus(Model model) {
        counter.setCount(counter.getCount() - 1);

        Optional<CountEntity> optional = Optional.of(countRepository.findById(1).get());

        optional.ifPresent(countEntity -> {
            Integer count = countEntity.getCount();
            countEntity.update(count - 1);
            countRepository.save(countEntity);
        });

        return String.valueOf(counter.getCount());
    }

}