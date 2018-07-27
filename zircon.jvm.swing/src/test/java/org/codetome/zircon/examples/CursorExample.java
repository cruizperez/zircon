package org.codetome.zircon.examples;

import org.codetome.zircon.TerminalUtils;
import org.codetome.zircon.api.data.Size;
import org.codetome.zircon.api.interop.DeviceConfigurations;
import org.codetome.zircon.api.interop.Sizes;
import org.codetome.zircon.api.interop.TextColors;
import org.codetome.zircon.api.resource.CP437TilesetResource;
import org.codetome.zircon.api.terminal.CursorStyle;
import org.codetome.zircon.api.terminal.Terminal;

public class CursorExample {

    private static final int TERMINAL_WIDTH = 30;
    private static final int TERMINAL_HEIGHT = 10;
    private static final Size SIZE = Sizes.create(TERMINAL_WIDTH, TERMINAL_HEIGHT);

    public static void main(String[] args) {
        // we create a new terminal using TerminalBuilder
        final Terminal terminal = TerminalUtils.fetchTerminalBuilder(args)
                .initialTerminalSize(SIZE)
                .font(CP437TilesetResource.TAFFER_20X20.toFont())
                // we only override the device config
                .deviceConfiguration(DeviceConfigurations.newBuilder()
                        .cursorColor(TextColors.fromString("#ff8844"))
                        .cursorStyle(CursorStyle.UNDER_BAR)
                        .cursorBlinking(true)
                        .build())
                .build(); // then we build the terminal

        // for this example we need the cursor to be visible
        terminal.setCursorVisibility(true);

        String text = "Cursor example...";
        for (int i = 0; i < text.length(); i++) {
            terminal.putCharacter(text.charAt(i));
        }

        terminal.flush();

    }
}
