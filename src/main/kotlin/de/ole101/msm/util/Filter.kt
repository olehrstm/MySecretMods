package de.ole101.msm.util

import de.ole101.msm.MySecretMods
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import net.minecraft.text.*

private val vanillaKeybindings = mutableListOf<String>()
private val vanillaTranslationKeys = mutableListOf<String>()
const val vanillaKeybindingsFile = "vanilla_keybindings.json"
const val vanillaTranslationKeysFile = "vanilla_translation_keys.json"

fun loadVanillaKeybindings() {
    val keybindingsStream = MySecretMods::class.java.getResourceAsStream("/data/$vanillaKeybindingsFile")
    val translationKeysStream = MySecretMods::class.java.getResourceAsStream("/data/$vanillaTranslationKeysFile")

    if (keybindingsStream != null) {
        val text = keybindingsStream.bufferedReader().readText()
        val jsonArray = Json.parseToJsonElement(text).jsonArray
        vanillaKeybindings.clear()
        vanillaKeybindings.addAll(jsonArray.map { it.jsonPrimitive.content })
    }
    if (translationKeysStream != null) {
        val text = translationKeysStream.bufferedReader().readText()
        val jsonArray = Json.parseToJsonElement(text).jsonArray
        vanillaTranslationKeys.clear()
        vanillaTranslationKeys.addAll(jsonArray.map { it.jsonPrimitive.content })
    }
}

fun filterRecursive(message: Text): Text {
    val filtered: MutableText = when (val content = message.content) {
        is KeybindTextContent -> {
            if (!isVanillaKeybinding(content.key)) {
                MutableText.of(PlainTextContent.Literal(content.key))
            } else {
                MutableText.of(content)
            }
        }

        is TranslatableTextContent -> {
            if (!isVanillaTranslationKey(content.key)) {
                MutableText.of(PlainTextContent.Literal(content.key))
            } else {
                MutableText.of(content)
            }
        }

        else -> MutableText.of(content)
    }

    filtered.style = message.style
    for (sibling in message.siblings) {
        filtered.append(filterRecursive(sibling))
    }
    return filtered
}

fun isVanillaKeybinding(keybinding: String): Boolean {
    return vanillaKeybindings.contains(keybinding)
}

fun isVanillaTranslationKey(translationKey: String): Boolean {
    return vanillaTranslationKeys.contains(translationKey)
}
