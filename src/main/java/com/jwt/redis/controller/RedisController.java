package com.jwt.redis.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.redis.service.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/save")
    public String save(@RequestParam String key,
                       @RequestParam String value) {

        redisService.save(key, value);
        return "Saved Successfully!";
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisService.get(key);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        redisService.delete(key);
        return "Deleted!";
    }

    @PostMapping("/save-expiry")
    public String saveWithExpiry(@RequestParam String key,
                                 @RequestParam String value,
                                 @RequestParam long seconds) {

        redisService.saveWithExpiry(key, value, seconds);
        return "Saved with expiry!";
    }
}