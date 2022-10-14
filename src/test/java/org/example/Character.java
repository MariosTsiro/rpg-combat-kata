package org.example;

public class Character {

    private int health;
    private boolean isAlive;
    private int level;

    public Character() {
        this.health = 1000;
        this.isAlive = true;
        this.level = 1;
    }

    public int getHealth() {
        return this.health;
    }

    public void damage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public void heal(int i) {
        if (!isAlive()) return;
        this.health += i;
        if (this.health > 1000) {
            this.health = 1000;
        }
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public int getLevel() {
        return this.level;
    }

    public void attack(Character other) {
        if (this == other) return;

        int damage = 100;
        if (this != other) {
            other.damage((int) (damage*getMultiplicator(other)));
        }
    }

    private double getMultiplicator(Character other) {
        if (isAtLeastFiveLevelLower(other)) {
            return 1.5;
        } else if (isAtLeastFiveLevelHigherThan(other)) {
            return 0.5;
        }
        return 1;
    }

    private boolean isAtLeastFiveLevelHigherThan(Character other) {
        return this.level <= other.getLevel() - 5;
    }

    private boolean isAtLeastFiveLevelLower(Character other) {
        return this.level >= other.getLevel() + 5;
    }

    public void levelUp(int level) {
        this.level += level;
    }
}
