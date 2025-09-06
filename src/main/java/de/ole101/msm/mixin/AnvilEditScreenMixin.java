package de.ole101.msm.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static de.ole101.msm.util.FilterKt.filterRecursive;

@Mixin(AnvilScreen.class)
public class AnvilEditScreenMixin {

    @Redirect(
            method = "onSlotUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getString()Ljava/lang/String;")
    )
    private String onSlotUpdate(Text instance) {
        return filterRecursive(instance).getString();
    }

    @Redirect(
            method = "onRenamed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getString()Ljava/lang/String;")
    )
    private String onRenamed(Text instance) {
        return filterRecursive(instance).getString();
    }
}
