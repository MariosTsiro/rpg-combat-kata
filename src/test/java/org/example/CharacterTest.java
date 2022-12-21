package org.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CharacterTest {

    //1. Test the Health.
    @Test
    public void check_health(){
        Character ch = new Character();
        assertThat(ch.getHealth()).isEqualTo(1000);
    }


    //2. Test, what happens, if we deduct health
    @Test
    public void character_takes_damage(){
        Character ch = new Character();
        ch.receiveDamage(100);
        assertThat(ch.getHealth()).isEqualTo(900);
    }


    @Test
    public void character_health_should_not_be_less_than_zero(){
        Character ch = new Character();
        ch.receiveDamage(1001);
        assertThat(ch.getHealth()).isZero();
    }

    @Test
    public void character_can_be_healed(){
        Character ch = new Character();
        ch.receiveDamage(200);
        ch.heal(100);
        assertThat(ch.getHealth()).isEqualTo(900);
    }

    @Test
    public void character_health_should_not_be_greater_than_1000(){
        Character ch = new Character();
        ch.heal(100);
        assertThat(ch.getHealth()).isEqualTo(1000);
    }

    @Test
    public void character_can_die(){
        Character ch = new Character();
        ch.receiveDamage(1000);
        assertThat(ch.isAlive()).isFalse();
    }

    @Test
    public void character_should_be_alive_when_spawned(){
        Character ch = new Character();
        assertThat(ch.isAlive()).isTrue();
    }

    @Test
    public void dead_character_should_not_be_healed(){
        Character ch = new Character();
        ch.receiveDamage(1000);
        ch.heal(10);
        assertThat(ch.isAlive()).isFalse();
    }


    @Test
    public void character_should_have_level(){
        Character ch = new Character();
        assertThat(ch.getLevel()).isEqualTo(1);
        ch.levelUp(1);
        assertThat(ch.getLevel()).isEqualTo(2);
    }

    @Test
    public void character_can_deal_damage(){
        Character ch = new Character();
        Character enemy = new Character();
        ch.dealDamageTo(enemy);
        assertThat(enemy.getHealth()).isNotEqualTo(1000);
    }

    @Test
    public void character_cannot_deal_damage_to_itself(){
        Character ch = new Character();
        ch.dealDamageTo(ch);
        assertThat(ch.getHealth()).isEqualTo(1000);
    }

    @Test
    public void enemy_should_take_50_percent_more_damage(){
        Character ch = new Character();
        Character enemy = new Character();
        ch.levelUp(6);
        ch.dealDamageTo(enemy);
        assertThat(enemy.getHealth()).isEqualTo(850);
    }

    @Test
    public void enemy_should_take_50_percent_less_damage(){
        Character ch = new Character();
        Character enemy = new Character();
        enemy.levelUp(6);
        ch.dealDamageTo(enemy);
        assertThat(enemy.getHealth()).isEqualTo(950);
    }

    @Test
    public void character_should_have_attack_range(){
        Character ch = new Character("melee");
        assertThat(ch.getAttackRange()).isEqualTo(2);
        Character ch2  = new Character("ranged");
        assertThat(ch2.getAttackRange()).isEqualTo(20);
    }

    @Test
    public void character_should_have_position(){
        Character ch = new Character("melee");
        assertThat(ch.getPosition()).isZero();
        ch.moveRight();
        assertThat(ch.getPosition()).isEqualTo(1);
        ch.moveLeft();
        assertThat(ch.getPosition()).isZero();
    }

    @Test
    public void character_can_only_attack_inRange(){
        Character ch = new Character("melee"); //Range 2
        Character enemy = new Character("ranged"); // Range 20
        enemy.moveRight();
        enemy.moveRight();
        enemy.moveRight();
        ch.dealDamageTo(enemy);
        assertThat(enemy.getHealth()).isEqualTo(1000);
        enemy.dealDamageTo(ch);
        assertThat(ch.getHealth()).isEqualTo(900);
    }

    @Test
    public void player_can_join_faction(){
        Faction ally = new Faction("ally");
        assertThat(ally).isNotNull();
        Character ch = new Character();
        ch.joinFaction(ally);
        assertThat(ally.getPlayers()).contains(ch);
        Character enemy = new Character();
        assertThat(ally.getPlayers()).doesNotContain(enemy);
    }

    @Test
    public void players_from_same_faction_cannot_attack_each_other(){
        Faction ally = new Faction("ally");
        Character ch = new Character();
        Character allied = new Character();
        ch.joinFaction(ally);
        allied.joinFaction(ally);
        ch.dealDamageTo(allied);
        assertThat(allied.getHealth()).isEqualTo(1000);
    }

    @Test
    public void multiple_factions(){
        Faction ally = new Faction("ally");
        Faction horde = new Faction("horde");
        Faction marios = new Faction("marios");
        Character ch = new Character();
        Character allied = new Character();
        ch.joinFaction(ally);
        ch.joinFaction(horde);

        allied.joinFaction(ally);
        allied.joinFaction(marios);
        ch.dealDamageTo(allied);
        assertThat(allied.getHealth()).isEqualTo(1000);
    }

    @Test
    public void players_can_leave_factions(){
        Faction ally = new Faction("ally");
        Character ch = new Character();
        ch.joinFaction(ally);
        ch.leaveFaction(ally);
        assertThat(ally.getPlayers()).isEmpty();
    }

    @Test
    public void players_of_same_faction_can_heal_each_other(){
        Faction ally = new Faction("ally");
        Character ch = new Character();
        ch.joinFaction(ally);
        Character enemy = new Character();
        enemy.dealDamageTo(ch);
        Character ally_character = new Character();
        ally_character.joinFaction(ally);
        ally_character.heal(ch,50);
        assertThat(ch.getHealth()).isEqualTo(950);
    }

    @Test
    public void enemies_cannot_heal_each_other(){
        Faction ally = new Faction("ally");
        Character ch = new Character();
        ch.joinFaction(ally);
        Character enemy = new Character();
        ch.dealDamageTo(enemy);
        ch.heal(enemy, 50);
        assertThat(enemy.getHealth()).isEqualTo(900);
    }

    @Test
    public void player_can_attack_things(){
        Character  ch = new Character();
        Thing tree = new Thing();
        ch.dealDamageTo(tree);
        assertThat(tree.getHealth()).isEqualTo(900);
    }

    @Test
    public void thing_can_have_individual_health(){
        Thing house = new Thing(2000);
        assertThat(house.getHealth()).isEqualTo(2000);
    }


    // Play can damage other THINGS. that are not characters. -> House, Tree, anything..
    // Players and  Things do have some things in common. like health, like take damage...
    // Superclass with basics things. Character and Things extends this.


}
