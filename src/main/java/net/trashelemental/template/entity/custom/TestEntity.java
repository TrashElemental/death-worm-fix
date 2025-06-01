package net.trashelemental.template.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.trashelemental.template.interfaces.citadel.ICustomCollisions;
import net.trashelemental.template.interfaces.ice_and_fire.ICustomMoveController;
import net.trashelemental.template.interfaces.ice_and_fire.IGroundMount;
import net.trashelemental.template.interfaces.ice_and_fire.ISyncMount;
import org.jetbrains.annotations.Nullable;

/**
 * This is a test entity for the DeathWorm move control.
 */

public class TestEntity extends TamableAnimal implements ISyncMount, ICustomCollisions, ICustomMoveController, IGroundMount {

    protected TestEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false, false) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isTame();
            }
        });
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }


    /**
     * Starts riding the entity when the player interacts with one that they own.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (this.isOwnedBy(player)) {
            player.startRiding(this);
            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }
        return super.mobInteract(player, hand);
    }



    /**
     * Methods required by TamableAnimal and the implementations.
     */
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean canPassThrough(BlockPos mutablePos, BlockState blockstate, VoxelShape voxelshape) {
        return false;
    }

    @Override
    public void up(boolean up) {

    }

    @Override
    public void down(boolean down) {

    }

    @Override
    public void attack(boolean attack) {

    }

    @Override
    public void strike(boolean strike) {

    }

    @Override
    public void dismount(boolean dismount) {

    }

    @Override
    public void setControlState(byte state) {

    }

    @Override
    public byte getControlState() {
        return 0;
    }

    @Override
    public Player getRidingPlayer() {
        return null;
    }

    @Override
    public double getRideSpeedModifier() {
        return 0;
    }


    public class SandMoveHelper extends MoveControl {
        private final TestEntity worm = TestEntity.this;

        public SandMoveHelper() {
            super(TestEntity.this);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                double d1 = this.wantedY - this.worm.getY();
                double d2 = this.wantedZ - this.worm.getZ();
                Vec3 Vector3d = new Vec3(this.wantedX - worm.getX(), this.wantedY - worm.getY(), this.wantedZ - worm.getZ());
                double d0 = Vector3d.length();
                if (d0 < (double) 2.5000003E-7F) {
                    this.mob.setZza(0.0F);
                } else {
                    this.speedModifier = 1.0F;
                    worm.setDeltaMovement(worm.getDeltaMovement().add(Vector3d.scale(this.speedModifier * 0.05D / d0)));
                    Vec3 Vector3d1 = worm.getDeltaMovement();
                    worm.setYRot(-((float) Mth.atan2(Vector3d1.x, Vector3d1.z)) * (180F / (float) Math.PI));
                }

            }
        }
    }


}
