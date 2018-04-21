package dev3.bank;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "dev3.bank.impl",
        "dev3.bank.factory"})
public class AppContext {
}
