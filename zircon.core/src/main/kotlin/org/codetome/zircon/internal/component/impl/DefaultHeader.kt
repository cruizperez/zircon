package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Header
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.util.Maybe

class DefaultHeader(private val text: String,
                    initialSize: Size,
                    initialTileset: Tileset,
                    position: Position,
                    componentStyleSet: ComponentStyleSet) : Header, DefaultComponent(
        initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = listOf(),
        initialTileset = initialTileset) {


    init {
        getDrawSurface().putText(text, Position.defaultPosition())
    }

    override fun getText() = text

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getBrightForegroundColor())
                        .backgroundColor(TextColor.transparent())
                        .build())
                .build())
    }
}
