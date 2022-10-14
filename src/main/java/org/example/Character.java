package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Character {

    private int health;
    private boolean isAlive;
    private int level;

    private int range;
    private int position;

    List<Faction> joinedFanctions = new ArrayList<>();

    public Character() {
        this.health = 1000;
        this.isAlive = true;
        this.level = 1;
        this.range = 0;
        this.position =0;
    }

    public Character(String type) {
        this();
        if(type.equalsIgnoreCase("melee")) range=2;
        if(type.equalsIgnoreCase("ranged"))range=20;
    }


    public int getHealth() {
        return this.health;
    }

    public void receiveDamage(int damage) {
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

    public void dealDamage(Character other) {
        if (this == other) return;
        if(isNotInRange(other))return;
        int damage = 100;
        other.receiveDamage((int) (damage * getMultiplicator(other)));
    }

    private boolean isNotInRange(Character other) {
        int difference = other.position - this.position;
        return Math.abs(difference) > this.range;
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

    public int getAttackRange() {
        return this.range;
    }

    public int getPosition() {
        return this.position;
    }

    public void moveRight() {
        this.position++;
    }

    public void moveLeft() {
        this.position--;
    }

    public void joinFaction(Faction faction) {
        faction.take(this);
        joinedFanctions.add(faction);
    }
}
