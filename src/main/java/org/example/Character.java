package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Character extends GameObject {

    private int level;

    private int range;

    List<Faction> joinedFanctions = new ArrayList<>();

    public Character() {
        super();
        this.level = 1;
        this.range = 0;
    }

    public Character(String type) {
        this();
        if(type.equalsIgnoreCase("melee")) range=2;
        if(type.equalsIgnoreCase("ranged"))range=20;
    }


    public void heal(Character other, int i) {
        if(this.isInSameFactionThan(other)) other.heal(i);
    }
    public void heal(int i) {
        if (!isAlive()) return;
        this.health += i;
        if (this.health > 1000) {
            this.health = 1000;
        }
    }

    public int getLevel() {
        return this.level;
    }

    public void dealDamageTo(Character other) {
        if (this == other) return;
        if (isNotInRange(other)) return;
        if (isInSameFactionThan(other)) return;
        int damage = 100;
        other.receiveDamage((int) (damage * getMultiplicator(other)));
    }

    private boolean isInSameFactionThan(Character other) {
        if (joinedFanctions == null) return false;
        long count = joinedFanctions.stream()
                .map(Faction::getPlayers)
                .filter(list -> new HashSet<>(list).containsAll(List.of(this,other)))
                .count();
        return count > 0;
    }

    private boolean isNotInRange(GameObject other) {
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

    public void leaveFaction(Faction ally) {
        ally.getPlayers().remove(this);
        joinedFanctions.remove(this);
    }
}
