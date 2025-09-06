package de.ole101.msm.util

import net.minecraft.text.*

fun filterKeybindsRecursive(message: Text): Text {
    val content = message.content
    var filtered = MutableText.of(content)
    if (content is KeybindTextContent) {
        val keybind: String? = content.key
        filtered = MutableText.of(PlainTextContent.Literal(keybind))
    }
    if (content is TranslatableTextContent) {
        val translationKey: String? = content.key
        filtered = MutableText.of(PlainTextContent.Literal(translationKey))
    }
    filtered.style = message.style
    for (sibling in message.siblings) {
        filtered.append(filterKeybindsRecursive(sibling))
    }
    return filtered
}
