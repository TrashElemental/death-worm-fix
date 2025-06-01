package net.trashelemental.death_worm_fix.interfaces.ice_and_fire;

public interface ICustomMoveController {
    void up(boolean up);

    void down(boolean down);

    void attack(boolean attack);

    void strike(boolean strike);

    void dismount(boolean dismount);

    void setControlState(byte state);

    byte getControlState();
}
