package net.trashelemental.death_worm_fix.interfaces.ice_and_fire;

import net.minecraft.world.entity.player.Player;

public interface IGroundMount {
    Player getRidingPlayer();

    double getRideSpeedModifier();
}
