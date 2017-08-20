package org.codetome.zircon.examples.interactive;

import org.codetome.zircon.Position;
import org.codetome.zircon.Size;
import org.codetome.zircon.Symbols;
import org.codetome.zircon.api.CP437TilesetResource;
import org.codetome.zircon.api.TerminalBuilder;
import org.codetome.zircon.api.TextCharacterBuilder;
import org.codetome.zircon.api.TextColorFactory;
import org.codetome.zircon.api.shape.FilledRectangleFactory;
import org.codetome.zircon.color.TextColor;
import org.codetome.zircon.color.impl.ANSITextColor;
import org.codetome.zircon.graphics.layer.DefaultLayer;
import org.codetome.zircon.graphics.layer.Layer;
import org.codetome.zircon.input.InputType;
import org.codetome.zircon.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class HideNSeek {

    private static final List<InputType> EXIT_CONDITIONS = new ArrayList<>();

    static {
        EXIT_CONDITIONS.add(InputType.Escape);
        EXIT_CONDITIONS.add(InputType.EOF);
    }

    public static void main(String[] args) {
        // for this example we only need a default terminal (no extra config)
        final Screen screen = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(40, 20))
                .font(CP437TilesetResource.TAFFER_20X20.asJava2DFont())
                .buildScreen();
        Size size = screen.getBoundableSize();
        screen.setCursorVisible(false); // we don't want the cursor right now
        FilledRectangleFactory
                .buildFilledRectangle(Position.TOP_LEFT_CORNER, screen.getBoundableSize())
                .toTextImage(TextCharacterBuilder.newBuilder()
                        .backgroundColor(TextColorFactory.fromString("#665233"))
                        .character(' ')
                        .build())
                .drawOnto(screen, Position.TOP_LEFT_CORNER);

        final Layer player = new DefaultLayer(Size.ONE,
                TextCharacterBuilder.newBuilder()
                        .character('@')
                        .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 0))
                        .foregroundColor(ANSITextColor.WHITE)
                        .build(),
                Position.of(size.getColumns() / 2, size.getRows() / 2));

        screen.addOverlay(player);
        screen.display();
        drawBuilding(screen, Position.of(5, 10));
        enableMovement(screen, player);
    }

    private static void drawBuilding(Screen screen, Position position) {
        Layer building = new DefaultLayer(Size.of(4, 4), TextCharacterBuilder.DEFAULT_CHARACTER, position);
        TextColor windowColor = TextColorFactory.fromString("#808080");
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++) {
                building.setCharacterAt(Position.of(x, y).plus(position), TextCharacterBuilder.newBuilder()
                        .backgroundColor(windowColor)
                        .character(' ')
                        .build());
            }
        }

        drawCharAt(building, Position.of(0, 2).plus(position), Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
        drawCharAt(building, Position.of(1, 2).plus(position), Symbols.SINGLE_LINE_T_DOWN);
        drawCharAt(building, Position.of(2, 2).plus(position), Symbols.SINGLE_LINE_T_DOWN);
        drawCharAt(building, Position.of(3, 2).plus(position), Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
        drawCharAt(building, Position.of(0, 3).plus(position), Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
        drawCharAt(building, Position.of(1, 3).plus(position), Symbols.SINGLE_LINE_T_UP);
        drawCharAt(building, Position.of(2, 3).plus(position), Symbols.SINGLE_LINE_T_UP);
        drawCharAt(building, Position.of(3, 3).plus(position), Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
        screen.display();
        screen.addOverlay(building);
    }

    private static void drawCharAt(Layer building, Position position, char c) {
        TextColor wallColor = TextColorFactory.fromString("#333333");
        TextColor windowColor = TextColorFactory.fromString("#808080");
        building.setCharacterAt(position, TextCharacterBuilder.newBuilder()
                .backgroundColor(wallColor)
                .foregroundColor(windowColor)
                .character(c)
                .build());
    }

    private static void enableMovement(Screen screen, Layer player) {
        screen.subscribe((input) -> {
            if (EXIT_CONDITIONS.contains(input.getInputType())) {
                System.exit(0);
            } else {
                if (InputType.ArrowUp == input.getInputType()) {
                    player.moveTo(player.getPosition().withRelativeRow(-1));
                }
                if (InputType.ArrowDown == input.getInputType()) {
                    player.moveTo(player.getPosition().withRelativeRow(1));
                }
                if (InputType.ArrowLeft == input.getInputType()) {
                    player.moveTo(player.getPosition().withRelativeColumn(-1));
                }
                if (InputType.ArrowRight == input.getInputType()) {
                    player.moveTo(player.getPosition().withRelativeColumn(1));
                }
                screen.display();
            }
        });
    }
}