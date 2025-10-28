package net.cozystudios.cozystudioscore.block.entity;

import net.cozystudios.cozystudioscore.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;


public class TranquilLanternBlockEntity extends BlockEntity {

    @Unique
    private static Integer RADIUS = null;
    @Unique
    private static Boolean BUMP = null;
    @Unique
    private static Boolean BURN = null;

    @Unique
    private static int getRadius() {
        if (RADIUS == null) {
            RADIUS = ModConfig.get().tranquilLanternRadius;
        }
        return RADIUS;
    }

    @Unique
    private static boolean getBump() {
        if (BUMP == null) {
            BUMP = ModConfig.get().tranquilLanternBump;
        }
        return BUMP;
    }

    @Unique
    private static boolean getBurn() {
        if (BURN == null) {
            BURN = ModConfig.get().tranquilLanternBurn;
        }
        return BURN;
    }

    public TranquilLanternBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRANQUIL_LANTERN, pos, state);
    }
    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, TranquilLanternBlockEntity _be) {
        if (world.isClient) return;

        int radius = getRadius();

        double cx = pos.getX() + 0.5;
        double cy = pos.getY() + 0.5;
        double cz = pos.getZ() + 0.5;
        double r2 = radius * radius;

        for (PlayerEntity player : world.getPlayers()) {
            if (player.squaredDistanceTo(cx, cy, cz) <= r2) {
                if (!player.hasStatusEffect(StatusEffects.REGENERATION) ||
                        player.getStatusEffect(StatusEffects.REGENERATION).getDuration() < 40) {
                    player.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.REGENERATION,
                            100,
                            0,
                            true,
                            false
                    ));
                }
            }
        }

        boolean suppressModeBump = getBump();
        boolean suppressModeBurn = getBurn();

        // We execute action only if config allows it
        if (suppressModeBump || suppressModeBurn) {
            Box box = new Box(cx - RADIUS, cy - RADIUS, cz - RADIUS,
                cx + RADIUS, cy + RADIUS, cz + RADIUS);

            for (HostileEntity mob : world.getEntitiesByClass(HostileEntity.class, box, Entity::isAlive)) {
                if (suppressModeBump) {
                    // Bump mob out of box
                    double dx = mob.getX() - cx;
                    double dy = mob.getY() - cy;
                    double dz = mob.getZ() - cz;
                    double d2 = dx * dx + dy * dy + dz * dz;

                    if (d2 <= r2 && d2 > 0.25) {
                        double d = Math.sqrt(d2);
                        double normalized = Math.max(0.0, 1.0 - (d / RADIUS));
                        double strength = 0.25 * Math.pow(normalized, 2.0);

                        mob.addVelocity((dx / d) * strength, 0, (dz / d) * strength);
                        mob.velocityModified = true;
                    }
                }
                if (suppressModeBurn) {
                    // If mob in range, set it on fire for 5s. Anyways it will apply again next tick if mob does not go out
                    mob.setOnFireFor(5);
                }
            }
        }
    }

}
