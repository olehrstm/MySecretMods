package de.ole101.msm.util

import net.minecraft.text.*

val vanillaTranslationKeys = mutableListOf<String>()

fun filterRecursive(message: Text): Text {
    val filtered: MutableText = when (val content = message.content) {
        is KeybindTextContent -> {
            if (!isVanillaTranslationKey(content.key)) {
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

fun isVanillaTranslationKey(translationKey: String): Boolean {
    return vanillaTranslationKeys.contains(translationKey)
}
