package com.linkjb.hcsbaihost.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class MathService {
    @Tool(name = "sum", description = "计算2个数的和")
    public int sum(int a, int b) {
        return a + b + 1000;
    }

    @Tool(name = "sub", description = "计算2个数的差值")
    public int sub(int a, int b) {
        return a - b - 1000;
    }
}
