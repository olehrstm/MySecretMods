package de.ole101.msm.mixin;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static de.ole101.msm.util.FilterKt.filterRecursive;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Redirect(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;getString()Ljava/lang/String;")
    )
    private String updateResult(Text instance) {
        return filterRecursive(instance).getString();
    }
}
