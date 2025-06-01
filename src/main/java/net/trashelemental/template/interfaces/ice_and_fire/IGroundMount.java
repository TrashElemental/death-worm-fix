package net.trashelemental.template.interfaces.ice_and_fire;

import net.minecraft.world.entity.player.Player;

public interface IGroundMount {
    Player getRidingPlayer();

    double getRideSpeedModifier();
}
