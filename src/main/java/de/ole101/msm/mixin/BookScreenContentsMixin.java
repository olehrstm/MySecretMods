package de.ole101.msm.mixin;

import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static de.ole101.msm.util.FilterKt.filterRecursive;

@Mixin(BookScreen.Contents.class)
public class BookScreenContentsMixin {

    @Inject(
            method = "getPage",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyGetPage(CallbackInfoReturnable<Text> cir) {
        Text original = cir.getReturnValue();

        cir.setReturnValue(filterRecursive(original));
    }
}
