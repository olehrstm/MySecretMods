package de.ole101.msm.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static de.ole101.msm.util.FilterKt.filterRecursive;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(
            method = "getName",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyGetName(CallbackInfoReturnable<Text> cir) {
        Text original = cir.getReturnValue();

        cir.setReturnValue(filterRecursive(original));
    }
}
