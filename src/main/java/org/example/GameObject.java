package org.example;

public class GameObject {
    protected int health;
    protected boolean isAlive;
    protected int position;

    public GameObject() {
        this.health = 1000;
        this.isAlive = true;
        this.position = 0;
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

    public boolean isAlive() {
        return this.isAlive;
    }

    public int getPosition() {
        return this.position;
    }
}
