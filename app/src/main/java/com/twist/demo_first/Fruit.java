package com.twist.demo_first;

/**
 * File description
 *
 * @author twist
 * @date 2019/10/15 00 42
 * @email twistonidea@gmail.com
 */
public class Fruit {
    private int fruitId;
    private String fruitName;

    public Fruit(int fruitId, String fruitName) {
        this.fruitId = fruitId;
        this.fruitName = fruitName;
    }

    public int getFruitId() {
        return fruitId;
    }

    public String getFruitName() {
        return fruitName;
    }
}
