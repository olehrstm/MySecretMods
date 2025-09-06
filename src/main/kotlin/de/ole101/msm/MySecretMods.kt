package de.ole101.msm

import de.ole101.msm.util.loadVanillaKeybindings
import net.fabricmc.api.ModInitializer

class MySecretMods : ModInitializer {

    override fun onInitialize() {
        loadVanillaKeybindings()
    }
}
