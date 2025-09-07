package de.ole101.msm.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.InputStream;
import java.util.function.BiConsumer;

import static de.ole101.msm.util.FilterKt.getVanillaTranslationKeys;

@Mixin(TranslationStorage.class)
class TranslationStorageMixin {

    @WrapOperation(
            method = "load(Ljava/lang/String;Ljava/util/List;Ljava/util/Map;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Language;load(Ljava/io/InputStream;Ljava/util/function/BiConsumer;)V"
            )
    )
    private static void load(InputStream inputStream, BiConsumer<String, String> entryConsumer,
            Operation<Void> original, @Local Resource resource) {
        ResourcePack pack = resource.getPack();
        boolean shouldBypass = pack instanceof DefaultResourcePack;

        if (pack.getInfo().source() == ResourcePackSource.SERVER
            || pack.getInfo().source() == ResourcePackSource.WORLD) {
            shouldBypass = true;
        }

        if (shouldBypass) {
            original.call(inputStream, (BiConsumer<String, String>) (translationKey, localisedValue) -> {
                getVanillaTranslationKeys().add(translationKey);
                entryConsumer.accept(translationKey, localisedValue);
            });
        } else {
            original.call(inputStream, entryConsumer);
        }
    }
}
